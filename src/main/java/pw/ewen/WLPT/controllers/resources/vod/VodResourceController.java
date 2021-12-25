package pw.ewen.WLPT.controllers.resources.vod;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.controllers.resources.ResourceControllerBase;
import pw.ewen.WLPT.domains.DTOs.resources.vod.VodResourceDTO;
import pw.ewen.WLPT.domains.dtoconvertors.resources.DTOBaseConvertor;
import pw.ewen.WLPT.domains.entities.resources.vod.VodResource;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.resources.ResourceServiceBase;

/**
 * 视频点播
 */
@RestController
@RequestMapping(value = "/resources/vods")
public class VodResourceController extends ResourceControllerBase<VodResource, VodResourceDTO> {

    protected VodResourceController(UserContext userContext, ResourceServiceBase<VodResource> resourceService, DTOBaseConvertor<VodResource, VodResourceDTO> dtoConvertor) {
        super(userContext, resourceService, dtoConvertor);
    }
}
