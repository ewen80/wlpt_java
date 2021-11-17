package pw.ewen.WLPT.services.resources.weixing;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.entities.resources.ResourceCheckIn;
import pw.ewen.WLPT.domains.entities.resources.ResourceReadInfo;
import pw.ewen.WLPT.domains.entities.resources.weixing.WeixingResource;
import pw.ewen.WLPT.repositories.resources.weixing.WeixingResourceRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.FileService;
import pw.ewen.WLPT.services.SerialNumberService;
import pw.ewen.WLPT.services.UserService;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * created by wenliang on 2021-07-21
 */
@Service
public class WeixingResourceService {

    private final WeixingResourceRepository weixingResourceRepository;
    private final SerialNumberService serialNumberService;
    private final UserContext userContext;
    private final BizConfig bizConfig;
    private final FileService fileService;
    private final UserService userService;

    @Autowired
    public WeixingResourceService(WeixingResourceRepository weixingResourceRepository, SerialNumberService serialNumberService, UserContext userContext, BizConfig bizConfig, FileService fileService, UserService userService) {
        this.weixingResourceRepository = weixingResourceRepository;
        this.serialNumberService = serialNumberService;
        this.userContext = userContext;
        this.bizConfig = bizConfig;
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostFilter("hasPermission(filterObject, 'read')")
    public List<WeixingResource> findAll() {
        return this.weixingResourceRepository.findAll();
    }

    @PostFilter("hasPermission(filterObject, 'read')")
    public List<WeixingResource> findAll(String filter) {
        SearchSpecificationsBuilder<WeixingResource> builder = new SearchSpecificationsBuilder<>();
        return this.weixingResourceRepository.findAll(builder.build(filter));
    }

    @PostAuthorize("hasPermission(returnObject.get(), 'read')")
    public Optional<WeixingResource> findOne(long id) {
        return this.weixingResourceRepository.findById(id);
    }

    @PreAuthorize("hasPermission(#weixingResource, 'create')")
    public WeixingResource add(WeixingResource weixingResource) {
        // 新增生成一条新的ResourceCheckIn记录
        if(weixingResource.getId() == 0) {
            // 生成编号
            String serialNumber = serialNumberService.generate(bizConfig.getSerialNumber().getWeixingName(), bizConfig.getSerialNumber().getWeixingBasis());
            weixingResource.setBh(serialNumber);

            ResourceCheckIn resourceCheckIn = new ResourceCheckIn(LocalDateTime.now(), userContext.getCurrentUser());
            weixingResource.setResourceCheckIn(resourceCheckIn);

            this.weixingResourceRepository.save(weixingResource);
            return weixingResource;
        } else {
            return null;
        }
    }

    @PreAuthorize("hasPermission(#weixingResource, 'write')")
    public void update(WeixingResource weixingResource) {
        if(weixingResource.getId() > 0) {
            this.weixingResourceRepository.save(weixingResource);
        }
    }

    @PreAuthorize("hasPermission(#weixingResource, 'delete')")
    public void delete(WeixingResource weixingResource) {
        this.weixingResourceRepository.deleteById(weixingResource.getId());
    }

    /**
     * 标记资源为已读
     * @param resourceId    资源id
     * @param userId    读取用户id
     */
    public void tagReaded(long resourceId, String userId) {
        this.weixingResourceRepository.findById(resourceId).ifPresent(weixing -> {
            ResourceReadInfo readInfo = new ResourceReadInfo();
            readInfo.setReadAt(LocalDateTime.now());
            userService.findOne(userId).ifPresent(readInfo::setUser);
            weixing.getReadInfoList().add(readInfo);
            this.weixingResourceRepository.save(weixing);
        });
    }

//    /**
//     * 获取现场审核意见表-pdf格式
//     * @param weixingResourceId 卫星id
//     * @param fieldAuditId 场地审核id
//     * @param output 输出流
//     */
//    public void getFieldAuditPdf(long weixingResourceId, long fieldAuditId, OutputStream output) {
//        this.findOne(weixingResourceId).ifPresent(weixing -> {
//            Map<String, String> textFieldMap = new HashMap<>();
//            Map<String, byte[]> imageFieldMap = new HashMap<>();
//
//            textFieldMap.put("bh", weixing.getBh());
//            textFieldMap.put("sqdw", weixing.getSqdw());
//            String qxName = bizConfig.getRegionMap().get(weixing.getQxId());
//            textFieldMap.put("qxname", qxName);
//            textFieldMap.put("sqlx", weixing.getSqlx());
//            textFieldMap.put("azdz", weixing.getAzdz());
//            textFieldMap.put("bgdh", weixing.getBgdh());
//            textFieldMap.put("yzbm", weixing.getYb());
//            textFieldMap.put("fzr", weixing.getFzr());
//            textFieldMap.put("fzrsj", weixing.getFzrsj());
//            textFieldMap.put("jfwz", weixing.getJfwz());
//            textFieldMap.put("txwz", weixing.getTxwz());
//            textFieldMap.put("txsl", String.valueOf(weixing.getTxsl()));
//            textFieldMap.put("txlx", weixing.getTxlx());
//            textFieldMap.put("jnssjmy", weixing.getJnssjmy());
//            textFieldMap.put("wxcsfs", weixing.getWxcsfs());
//            textFieldMap.put("wxmc", weixing.getWxmc());
//            textFieldMap.put("ssnr", weixing.getSsnr());
//            textFieldMap.put("sjazdwmc", weixing.getSjazdwmc());
//            textFieldMap.put("wxssazxkzh", weixing.getWxssazxkzh());
//            textFieldMap.put("ssdwlx", weixing.getSsdwlx());
//            textFieldMap.put("lpm", weixing.getLpm());
//            textFieldMap.put("lc", weixing.getLc());
//            textFieldMap.put("zds", String.valueOf(weixing.getZds()));
//
//            weixing.getFieldAudits()
//                    .stream()
//                    .filter(audit-> audit.getId() == fieldAuditId)
//                    .findFirst()
//                    .ifPresent(audit->{
//                        textFieldMap.put("auditContent", audit.getContent());
//                        textFieldMap.put("auditer", audit.getUser().getName());
//                        textFieldMap.put("auditDepartment", audit.getAuditDepartment());
//                        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern(bizConfig.getLocalDateFormat());
//                        textFieldMap.put("auditDate", audit.getAuditDate().format(formatter));
//                        if(audit.getSignature() != null && audit.getSignature().getBytes() != null) {
//                            imageFieldMap.put("signature", audit.getSignature().getBytes());
//                        }
//
//                    });
//            String template = bizConfig.getFile().getWeixingFieldAuditTemplate();
//
//            try {
//                this.fileService.getPdf(template, textFieldMap, imageFieldMap, output);
//            } catch (IOException ignored) {
//
//            }
//
//        });
//    }

    // 生成word
    public void getFieldAuditWord(long resourceId, long fieldAuditId, OutputStream output) {
        this.findOne(resourceId).ifPresent(weixing -> {
            Map<String, String> textFieldMap = new HashMap<>();
            Map<String, byte[]> imageFieldMap = new HashMap<>();
            Map<String, byte[][]> rowImageMap = new HashMap<>();

            textFieldMap.put("bh", weixing.getBh());
            textFieldMap.put("sqdw", weixing.getSqdw());
            String qxName = bizConfig.getRegionMap().get(weixing.getQxId());
            textFieldMap.put("qxname", qxName);
            textFieldMap.put("sqlx", weixing.getSqlx());
            textFieldMap.put("azdz", weixing.getAzdz());
            textFieldMap.put("bgdh", weixing.getBgdh());
            textFieldMap.put("yzbm", weixing.getYb());
            textFieldMap.put("fzr", weixing.getFzr());
            textFieldMap.put("fzrsj", weixing.getFzrsj());
            textFieldMap.put("jfwz", weixing.getJfwz());
            textFieldMap.put("txwz", weixing.getTxwz());
            textFieldMap.put("txsl", String.valueOf(weixing.getTxsl()));
            textFieldMap.put("txlx", weixing.getTxlx());
            textFieldMap.put("jnssjmy", weixing.getJnssjmy());
            textFieldMap.put("wxcsfs", weixing.getWxcsfs());
            textFieldMap.put("wxmc", weixing.getWxmc());
            textFieldMap.put("ssnr", weixing.getSsnr());
            textFieldMap.put("sjazdwmc", weixing.getSjazdwmc());
            textFieldMap.put("wxssazxkzh", weixing.getWxssazxkzh());
            textFieldMap.put("ssdwlx", weixing.getSsdwlx());
            textFieldMap.put("lpm", weixing.getLpm());
            textFieldMap.put("lc", weixing.getLc());
            textFieldMap.put("zds", String.valueOf(weixing.getZds()));

            weixing.getFieldAudits()
                    .stream()
                    .filter(audit -> audit.getId() == fieldAuditId)
                    .findFirst()
                    .ifPresent(audit -> {
                        textFieldMap.put("auditContent", audit.getContent());
                        textFieldMap.put("auditer", audit.getUser().getName());
                        textFieldMap.put("auditDepartment", audit.getAuditDepartment());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(bizConfig.getPrintDateFormat());
                        textFieldMap.put("auditDate", audit.getAuditDate().format(formatter));
                        if (audit.getFzrSignature() != null && audit.getFzrSignature().getBytes() != null) {
                            imageFieldMap.put("signature", audit.getFzrSignature().getBytes());
                        }

                    });
            String template = bizConfig.getFile().getWeixingFieldAuditTemplate();

            try {
                this.fileService.getWord(template, textFieldMap, imageFieldMap, new HashMap<>(), rowImageMap, output);
            } catch (IOException | XmlException | InvalidFormatException ignored) {

            }

        });
    }
}
