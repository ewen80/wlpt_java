package pw.ewen.WLPT.controllers.resources.artifactshop;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.controllers.resources.ResourceControllerBase;
import pw.ewen.WLPT.domains.DTOs.resources.artifactshop.ArtifactShopResourceDTO;
import pw.ewen.WLPT.domains.dtoconvertors.resources.DTOBaseConvertor;
import pw.ewen.WLPT.domains.entities.resources.artifactshop.ArtifactShopResource;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.resources.ResourceServiceBase;

/**
 * 文物商店
 */
@RestController
@RequestMapping(value = "/resources/artifactshops")
public class ArtifactshopResourceController extends ResourceControllerBase<ArtifactShopResource, ArtifactShopResourceDTO> {
    protected ArtifactshopResourceController(UserContext userContext, ResourceServiceBase<ArtifactShopResource> resourceService, DTOBaseConvertor<ArtifactShopResource, ArtifactShopResourceDTO> dtoConvertor) {
        super(userContext, resourceService, dtoConvertor);
    }
}
