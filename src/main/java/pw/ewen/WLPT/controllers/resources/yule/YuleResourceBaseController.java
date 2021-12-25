package pw.ewen.WLPT.controllers.resources.yule;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.controllers.resources.ResourceControllerBase;
import pw.ewen.WLPT.domains.DTOs.resources.yule.YuleResourceBaseDTO;
import pw.ewen.WLPT.domains.dtoconvertors.resources.DTOBaseConvertor;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceBase;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.resources.ResourceServiceBase;

/**
 * 娱乐场所
 */
@RestController
@RequestMapping(value = "/resources/yules")
public class YuleResourceBaseController extends ResourceControllerBase<YuleResourceBase, YuleResourceBaseDTO> {
    protected YuleResourceBaseController(UserContext userContext, ResourceServiceBase<YuleResourceBase> resourceService, DTOBaseConvertor<YuleResourceBase, YuleResourceBaseDTO> dtoConvertor) {
        super(userContext, resourceService, dtoConvertor);
    }
}
