package pw.ewen.WLPT.controllers.resources.vod;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.controllers.resources.ResourceFieldAuditControllerBase;
import pw.ewen.WLPT.domains.dtoconvertors.resources.FieldAuditDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.vod.VodResource;
import pw.ewen.WLPT.services.resources.FieldAuditService;
import pw.ewen.WLPT.services.resources.ResourceServiceBase;

/**
 * 视频点播-现场审核信息
 */
@RestController
@RequestMapping(value = "/resources/vods/fieldaudits")
public class VodResourceFieldAuditController extends ResourceFieldAuditControllerBase<VodResource> {

    protected VodResourceFieldAuditController(ResourceServiceBase<VodResource> resourceService, FieldAuditDTOConvertor fieldAuditDTOConvertor, FieldAuditService fieldAuditService) {
        super(resourceService, fieldAuditDTOConvertor, fieldAuditService);
    }
}
