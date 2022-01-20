package pw.ewen.WLPT.services.resources.artifactshop;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xmlbeans.XmlException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.entities.resources.Signature;
import pw.ewen.WLPT.domains.entities.resources.artifactshop.ArtifactShopResource;
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
 * created by wenliang on 2021-12-24
 */
@Service
public class ArtifactShopResourceService extends ResourceServiceBase<ArtifactShopResource> {

    private final BizConfig bizConfig;
    private final FileService fileService;

    protected ArtifactShopResourceService(UserRepository userRepository, ResourceRepository<ArtifactShopResource, Long> repository, SerialNumberService serialNumberService, UserContext userContext, BizConfig bizConfig, FileService fileService) {
        super(userRepository, repository, serialNumberService, userContext);
        this.bizConfig = bizConfig;
        this.fileService = fileService;
    }

    @Override
    @PreAuthorize("hasPermission(#resource, 'create')")
    public ArtifactShopResource add(ArtifactShopResource resource) {
        return this.add(resource, bizConfig.getSerialNumber().getArtifactshopName(), bizConfig.getSerialNumber().getArtifactshopBasis());
    }


    public void getFieldAuditWord(long resourceId, long fieldAuditId, OutputStream output) {
        this.findOne(resourceId).ifPresent(resource -> {
            Map<String, String> textFieldMap = new HashMap<>();
            Map<String, List<byte[]>> imageFieldMap = new HashMap<>();
            Map<String, byte[][]> rowImageMap = new HashMap<>();

            textFieldMap.put("sqdw", resource.getSqdw());
            textFieldMap.put("faren", resource.getFaren());
            textFieldMap.put("csdz", resource.getCsdz());
            textFieldMap.put("lxr", resource.getLxr());
            textFieldMap.put("sbxm", resource.getSbxm());
            textFieldMap.put("lxdh", resource.getLxdh());

            resource.getFieldAudits()
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
            String template = bizConfig.getFile().getArtifactshopFieldAuditTemplate();

            try {
                this.fileService.getWord(template, textFieldMap, imageFieldMap, new HashMap<>(), rowImageMap, output);
            } catch (IOException | XmlException | InvalidFormatException ignored) {

            }

        });
    }

}
