package pw.ewen.WLPT.services.resources.weixing;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xmlbeans.XmlException;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.entities.resources.weixing.WeixingResource;
import pw.ewen.WLPT.repositories.UserRepository;
import pw.ewen.WLPT.repositories.resources.ResourceRepository;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.FileService;
import pw.ewen.WLPT.services.SerialNumberService;
import pw.ewen.WLPT.services.resources.ResourceServiceBase;

import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * created by wenliang on 2021-07-21
 */
@Service
public class WeixingResourceService extends ResourceServiceBase<WeixingResource> {

    private final BizConfig bizConfig;
    private final FileService fileService;

    protected WeixingResourceService(UserRepository userRepository, ResourceRepository<WeixingResource, Long> repository, SerialNumberService serialNumberService, UserContext userContext, BizConfig bizConfig, FileService fileService) {
        super(userRepository, repository, serialNumberService, userContext);
        this.bizConfig = bizConfig;
        this.fileService = fileService;
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
            textFieldMap.put("xhtzfs", weixing.getXhtzfs());
            textFieldMap.put("wxmc", weixing.getWxmc());
            textFieldMap.put("ssnr", weixing.getSsnr().replaceAll(",{2,}",",").replaceAll(",$",""));
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
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(bizConfig.getDateFormat().getPrintDateFormat());
                        textFieldMap.put("auditDate", audit.getAuditDate().format(formatter));
                        if (audit.getFzrSignature() != null && audit.getFzrSignature().getBytes() != null) {
                            imageFieldMap.put("signature", audit.getFzrSignature().getBytes());
                        }
                        // 核查人签名
                        byte[][] auditorSignatures = new byte[audit.getAuditorSignatures().size()][1];
                        for(int i=0; i<audit.getAuditorSignatures().size(); i++) {
                            auditorSignatures[i] = audit.getAuditorSignatures().get(i).getBytes();
                        }
                        rowImageMap.put("auditorSignatures", auditorSignatures);
                    });
            String template = bizConfig.getFile().getWeixingFieldAuditTemplate();

            try {
                this.fileService.getWord(template, textFieldMap, imageFieldMap, new HashMap<>(), rowImageMap, output);
            } catch (IOException | XmlException | InvalidFormatException ignored) {

            }

        });
    }

    @Override
    public WeixingResource add(WeixingResource weixingResource) {
        return this.add(weixingResource, bizConfig.getSerialNumber().getWeixingName(), bizConfig.getSerialNumber().getWeixingBasis());
    }
}
