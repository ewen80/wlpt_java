package pw.ewen.WLPT.controllers.resources.yule;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.controllers.resources.ResourceFieldAuditControllerBase;
import pw.ewen.WLPT.domains.dtoconvertors.resources.FieldAuditDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceBase;
import pw.ewen.WLPT.services.resources.FieldAuditService;
import pw.ewen.WLPT.services.resources.ResourceServiceBase;

/**
 * 娱乐场地-现场审核信息
 */
@RestController
@RequestMapping(value = "/resources/yules/fieldaudits")
public class YuleResourceFieldAuditController extends ResourceFieldAuditControllerBase<YuleResourceBase> {

    protected YuleResourceFieldAuditController(ResourceServiceBase<YuleResourceBase> resourceService, FieldAuditDTOConvertor fieldAuditDTOConvertor, FieldAuditService fieldAuditService) {
        super(resourceService, fieldAuditDTOConvertor, fieldAuditService);
    }
}
