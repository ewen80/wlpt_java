package pw.ewen.WLPT.controllers.resources.weixing;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.controllers.resources.ResourceFieldAuditControllerBase;
import pw.ewen.WLPT.domains.dtoconvertors.resources.FieldAuditDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.weixing.WeixingResource;
import pw.ewen.WLPT.services.resources.FieldAuditService;
import pw.ewen.WLPT.services.resources.ResourceServiceBase;

/**
 * 卫星场地-现场审核信息
 */
@RestController
@RequestMapping(value = "/resources/weixings/fieldaudits")
public class WeixingResourceFieldAuditController extends ResourceFieldAuditControllerBase<WeixingResource> {


    protected WeixingResourceFieldAuditController(ResourceServiceBase<WeixingResource> resourceService, FieldAuditDTOConvertor fieldAuditDTOConvertor, FieldAuditService fieldAuditService) {
        super(resourceService, fieldAuditDTOConvertor, fieldAuditService);
    }
}
