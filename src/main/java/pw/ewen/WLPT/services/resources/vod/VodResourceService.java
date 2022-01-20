package pw.ewen.WLPT.services.resources.vod;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xmlbeans.XmlException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.entities.resources.Signature;
import pw.ewen.WLPT.domains.entities.resources.vod.VodResource;
import pw.ewen.WLPT.repositories.UserRepository;
import pw.ewen.WLPT.repositories.resources.ResourceRepository;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.FileService;
import pw.ewen.WLPT.services.SerialNumberService;
import pw.ewen.WLPT.services.resources.ResourceServiceBase;

import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * created by wenliang on 2021-12-21
 */
@Service
public class VodResourceService extends ResourceServiceBase<VodResource> {

    private final BizConfig bizConfig;
    private final FileService fileService;

    protected VodResourceService(UserRepository userRepository, ResourceRepository<VodResource, Long> repository, SerialNumberService serialNumberService, UserContext userContext, BizConfig bizConfig, FileService fileService) {
        super(userRepository, repository, serialNumberService, userContext);
        this.bizConfig = bizConfig;
        this.fileService = fileService;
    }


    public void getFieldAuditWord(long resourceId, long fieldAuditId, OutputStream output) {
        this.findOne(resourceId).ifPresent(vod -> {
            Map<String, String> textFieldMap = new HashMap<>();
            Map<String, List<byte[]>> imageFieldMap = new HashMap<>();
            Map<String, byte[][]> rowImageMap = new HashMap<>();

            textFieldMap.put("sysName", vod.getSysName());
            textFieldMap.put("deviceName", vod.getDeviceName());
            textFieldMap.put("manufacturer", vod.getManufacturer());
            textFieldMap.put("deviceModel", vod.getDeviceModel());
            textFieldMap.put("samplingMethod", vod.getSamplingMethod());
            textFieldMap.put("detectLocation", vod.getDetectLocation());
            textFieldMap.put("detectUnit", vod.getDetectUnit());
            textFieldMap.put("sysExplanation", vod.getSysExplanation());
            textFieldMap.put("detectBasis", vod.getDetectBasis());
            textFieldMap.put("detectOverview", vod.getDetectOverview());

            vod.getFieldAudits()
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
                            imageFieldMap.put("fzrSignature", Collections.singletonList(audit.getFzrSignature().getBytes()));
                        }
                        // 核查人签名
                        imageFieldMap.put("auditorSignatures",audit.getAuditorSignatures().stream().map(Signature::getBytes).collect(Collectors.toList()));

                    });
            String template = bizConfig.getFile().getVodFieldAuditTemplate();

            try {
                this.fileService.getWord(template, textFieldMap, imageFieldMap, new HashMap<>(), rowImageMap, output);
            } catch (IOException | XmlException | InvalidFormatException ignored) {

            }

        });
    }

    @Override
    @PreAuthorize("hasPermission(#resource, 'create')")
    public VodResource add(VodResource resource) {
        return this.add(resource, bizConfig.getSerialNumber().getVodName(), bizConfig.getSerialNumber().getVodBasis());
    }

}
