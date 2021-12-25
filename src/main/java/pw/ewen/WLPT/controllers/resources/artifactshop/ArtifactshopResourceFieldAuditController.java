package pw.ewen.WLPT.controllers.resources.artifactshop;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.controllers.resources.ResourceFieldAuditControllerBase;
import pw.ewen.WLPT.domains.dtoconvertors.resources.FieldAuditDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.artifactshop.ArtifactShopResource;
import pw.ewen.WLPT.services.resources.FieldAuditService;
import pw.ewen.WLPT.services.resources.ResourceServiceBase;

/**
 * 文物商店-现场审核信息
 */
@RestController
@RequestMapping(value = "/resources/artifactshops/fieldaudits")
public class ArtifactshopResourceFieldAuditController extends ResourceFieldAuditControllerBase<ArtifactShopResource> {
    protected ArtifactshopResourceFieldAuditController(ResourceServiceBase<ArtifactShopResource> resourceService, FieldAuditDTOConvertor fieldAuditDTOConvertor, FieldAuditService fieldAuditService) {
        super(resourceService, fieldAuditDTOConvertor, fieldAuditService);
    }
}
