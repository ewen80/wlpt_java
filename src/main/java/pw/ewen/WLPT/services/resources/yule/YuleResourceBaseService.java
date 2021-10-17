package pw.ewen.WLPT.services.resources.yule;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xmlbeans.XmlException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.entities.resources.ResourceCheckIn;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceBase;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceGwRoom;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceGwWc;
import pw.ewen.WLPT.repositories.resources.yule.YuleResourceBaseRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.FileService;
import pw.ewen.WLPT.services.SerialNumberService;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * created by wenliang on 2021/10/6
 */
@Service
public class YuleResourceBaseService {
    private final YuleResourceBaseRepository yuleResourceBaseRepository;
    private final SerialNumberService serialNumberService;
    private final BizConfig bizConfig;
    private final UserContext userContext;
    private final FileService fileService;

    public YuleResourceBaseService(YuleResourceBaseRepository yuleResourceBaseRepository, SerialNumberService serialNumberService, BizConfig bizConfig, UserContext userContext, FileService fileService) {
        this.yuleResourceBaseRepository = yuleResourceBaseRepository;
        this.serialNumberService = serialNumberService;
        this.bizConfig = bizConfig;
        this.userContext = userContext;
        this.fileService = fileService;
    }

    @PostAuthorize("hasPermission(returnObject.get(), 'read')")
    public Optional<YuleResourceBase> findOne(long id) {
        return this.yuleResourceBaseRepository.findById(id);
    }

    @PostFilter("hasPermission(filterObject, 'read')")
    public List<YuleResourceBase> findAll() {
        return this.yuleResourceBaseRepository.findAll();
    }

    @PostFilter("hasPermission(filterObject, 'read')")
    public List<YuleResourceBase> findAll(String filter) {
        SearchSpecificationsBuilder<YuleResourceBase> builder = new SearchSpecificationsBuilder<>();
        return this.yuleResourceBaseRepository.findAll(builder.build(filter));
    }

    // 通过娱乐id找到对应房间
    public List<YuleResourceGwRoom> findRoomsByResourceId(long resourceId) {
        return this.findOne(resourceId).map(YuleResourceBase::getRooms).orElse(new ArrayList<>());
    }

    // 通过娱乐id找到对应舞池
    public List<YuleResourceGwWc> findWcsByResourceId(long resourceId) {
        return this.findOne(resourceId).map(YuleResourceBase::getWcs).orElse(new ArrayList<>());
    }

    @PreAuthorize("hasPermission(#yuleResourceBase, 'create')")
    public YuleResourceBase add(YuleResourceBase yuleResourceBase) {
        // 新增生成一条新的ResourceCheckIn记录
        if(yuleResourceBase.getId() == 0) {
            // 生成编号
            String serialNumber = serialNumberService.generate(bizConfig.getSerialNumber().getYuleName(), bizConfig.getSerialNumber().getYuleBasis());
            yuleResourceBase.setBh(serialNumber);

            ResourceCheckIn resourceCheckIn = new ResourceCheckIn(LocalDateTime.now(), userContext.getCurrentUser());
            yuleResourceBase.setResourceCheckIn(resourceCheckIn);

            this.yuleResourceBaseRepository.save(yuleResourceBase);
            return yuleResourceBase;
        } else {
            return null;
        }
    }

    @PreAuthorize("hasPermission(#yuleResourceBase, 'write')")
    public void update(YuleResourceBase yuleResourceBase) {
        if(yuleResourceBase.getId() > 0) {
            this.yuleResourceBaseRepository.save(yuleResourceBase);
        }
    }

    @PreAuthorize("hasPermission(#yuleResourceBase, 'delete')")
    public void delete(YuleResourceBase yuleResourceBase) {
        this.yuleResourceBaseRepository.deleteById(yuleResourceBase.getId());
    }

    public void getFieldAuditWord(long resourceId, long fieldAuditId, OutputStream output) {
        this.findOne(resourceId).ifPresent(yule -> {
            Map<String, String> textFieldMap = new HashMap<>();
            Map<String, byte[]> imageFieldMap = new HashMap<>();
            Map<String, String[][]> rowMap = new HashMap<>();

            textFieldMap.put("bh", yule.getBh());
            textFieldMap.put("dwmc", yule.getDwmc());
            String qxName = bizConfig.getRegionMap().get(yule.getQxId());
            textFieldMap.put("qxname", qxName);
            textFieldMap.put("sbxm", yule.getSbxm());
            textFieldMap.put("csdz", yule.getCsdz());
            textFieldMap.put("symj", String.valueOf(yule.getSymj()));
            textFieldMap.put("aqtd", yule.getAqtd());
            textFieldMap.put("lxr", yule.getLxr());
            textFieldMap.put("lxdh", yule.getLxdh());
            textFieldMap.put("jyfw", yule.getJyfw());
            textFieldMap.put("fenqu", yule.getYyBase() == null ? "" : yule.getYyBase().isFenqu() ? "是" : "否");
            textFieldMap.put("tuibi", yule.getYyBase() == null ? "" : yule.getYyBase().isTuibi() ? "是" : "否");
            textFieldMap.put("jiangpinCatalogSame", yule.getYyBase() == null ? "" : yule.getYyBase().isJiangpinCatalogSame() ? "是" : "否");
            textFieldMap.put("jiangpinValue", yule.getYyBase() == null ? "" : yule.getYyBase().isJiangpinValue() ? "是" : "否");

            yule.getFieldAudits().stream().filter(audit -> audit.getId() == fieldAuditId)
                            .findFirst()
                                    .ifPresent(audit -> {
                                        textFieldMap.put("auditContent", audit.getContent());
                                        textFieldMap.put("auditer", audit.getUser().getName());
                                        textFieldMap.put("auditDepartment", audit.getAuditDepartment());

                                        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern(bizConfig.getPrintDateFormat());
                                        textFieldMap.put("auditDate", audit.getAuditDate().format(formatter));

                                        if(audit.getSignature() != null && audit.getSignature().getBytes() != null) {
                                            imageFieldMap.put("signature", audit.getSignature().getBytes());
                                        }
                                    });
            // 填充包房Map
            String[][] rooms = new String[yule.getRooms().size()][7];
            for(int i=0; i<yule.getRooms().size(); i++) {
                YuleResourceGwRoom room = yule.getRooms().get(i);
                rooms[i][0] = String.valueOf(i+1);
                rooms[i][1] = room.getName();
                rooms[i][2] = String.valueOf(room.getArea());
                rooms[i][3] = String.valueOf(room.getHdrs());
                rooms[i][4] = room.isToilet() ? "是" : "否";
                rooms[i][5] = room.isInnerLock() ? "是" : "否";
                rooms[i][6] = room.isWindow() ? "是" : "否";
            }
            rowMap.put("row1", rooms);

            // 填充舞池Map
            String[][] wcs = new String[yule.getWcs().size()][5];
            for(int i=0; i<yule.getWcs().size(); i++) {
                YuleResourceGwWc wc = yule.getWcs().get(i);
                wcs[i][0] = String.valueOf(i+1);
                wcs[i][1] = wc.getName();
                wcs[i][2] = String.valueOf(wc.getArea());
                wcs[i][3] = wc.isDlwc() ? "是" : "否";
                wcs[i][4] = wc.isYwjf() ? "是" : "否";
            }
            rowMap.put("row2", wcs);

            String template = bizConfig.getFile().getYuleFieldAuditTemplate();

            try {
                this.fileService.getWord(template, textFieldMap, imageFieldMap, rowMap, output);
            } catch (IOException | XmlException | InvalidFormatException ignored) {

            }

        });
    }
}
