package pw.ewen.WLPT.services.resources.yule;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xmlbeans.XmlException;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.entities.resources.Signature;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceBase;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceGwRoom;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceGwWc;
import pw.ewen.WLPT.repositories.UserRepository;
import pw.ewen.WLPT.repositories.resources.ResourceRepository;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.FileService;
import pw.ewen.WLPT.services.SerialNumberService;
import pw.ewen.WLPT.services.resources.ResourceServiceBase;

import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * created by wenliang on 2021/10/6
 */
@Service
public class YuleResourceBaseService extends ResourceServiceBase<YuleResourceBase> {

    private final BizConfig bizConfig;
    private final FileService fileService;

    protected YuleResourceBaseService(UserRepository userRepository, ResourceRepository<YuleResourceBase, Long> repository, SerialNumberService serialNumberService, UserContext userContext, BizConfig bizConfig, FileService fileService) {
        super(userRepository, repository, serialNumberService, userContext);
        this.bizConfig = bizConfig;
        this.fileService = fileService;
    }



    // 通过娱乐id找到对应房间
    public List<YuleResourceGwRoom> findRoomsByResourceId(long resourceId) {
        return this.findOne(resourceId).map(YuleResourceBase::getRooms).orElse(new ArrayList<>());
    }

    // 通过娱乐id找到对应舞池
    public List<YuleResourceGwWc> findWcsByResourceId(long resourceId) {
        return this.findOne(resourceId).map(YuleResourceBase::getWcs).orElse(new ArrayList<>());
    }


    public void getFieldAuditWord(long resourceId, long fieldAuditId, OutputStream output) {
        this.findOne(resourceId).ifPresent(yule -> {
            Map<String, String> textFieldMap = new HashMap<>();
            Map<String, List<byte[]>> imageFieldMap = new HashMap<>();
            Map<String, String[][]> rowTextMap = new HashMap<>();
            Map<String, byte[][]> rowImageMap = new HashMap<>();

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

                                        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern(bizConfig.getDateFormat().getPrintDateFormat());
                                        textFieldMap.put("auditDate", audit.getAuditDate().format(formatter));

                                        if(audit.getFzrSignature() != null && audit.getFzrSignature().getBytes() != null) {
                                            imageFieldMap.put("fzrSignature", Collections.singletonList(audit.getFzrSignature().getBytes()));
                                        }

                                        // 核查人签名
//                                        byte[][] auditorSignatures = new byte[audit.getAuditorSignatures().size()][1];
//                                        for(int i=0; i<audit.getAuditorSignatures().size(); i++) {
//                                            auditorSignatures[i] = audit.getAuditorSignatures().get(i).getBytes();
//                                        }
//                                        rowImageMap.put("auditorSignatures", auditorSignatures);
                                        imageFieldMap.put("auditorSignatures",audit.getAuditorSignatures().stream().map(Signature::getBytes).collect(Collectors.toList()));
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
            rowTextMap.put("row1", rooms);

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
            rowTextMap.put("row2", wcs);

            String template = bizConfig.getFile().getYuleFieldAuditTemplate();

            try {
                this.fileService.getWord(template, textFieldMap, imageFieldMap, rowTextMap, rowImageMap, output);
            } catch (IOException | XmlException | InvalidFormatException ignored) {

            }

        });
    }

    @Override
    public YuleResourceBase add(YuleResourceBase yuleResourceBase) {
        return this.add(yuleResourceBase, bizConfig.getSerialNumber().getYuleName(), bizConfig.getSerialNumber().getYuleBasis());
    }
}
