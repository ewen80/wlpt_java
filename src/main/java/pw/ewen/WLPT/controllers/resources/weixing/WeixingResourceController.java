package pw.ewen.WLPT.controllers.resources.weixing;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.controllers.resources.ResourceControllerBase;
import pw.ewen.WLPT.domains.DTOs.resources.weixing.WeixingResourceDTO;
import pw.ewen.WLPT.domains.dtoconvertors.resources.DTOBaseConvertor;
import pw.ewen.WLPT.domains.entities.resources.weixing.WeixingResource;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.resources.ResourceServiceBase;

/**
 * 卫星场地
 */
@RestController
@RequestMapping(value = "/resources/weixings")
public class WeixingResourceController extends ResourceControllerBase<WeixingResource, WeixingResourceDTO> {


    protected WeixingResourceController(UserContext userContext, ResourceServiceBase<WeixingResource> resourceService, DTOBaseConvertor<WeixingResource, WeixingResourceDTO> dtoConvertor) {
        super(userContext, resourceService, dtoConvertor);
    }
}
