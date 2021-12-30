package pw.ewen.WLPT.domains.dtoconvertors.resources.artifactshop;

import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.DTOs.resources.artifactshop.ArtifactShopResourceDTO;
import pw.ewen.WLPT.domains.dtoconvertors.PermissionDTOConvertor;
import pw.ewen.WLPT.domains.dtoconvertors.resources.DTOBaseConvertor;
import pw.ewen.WLPT.domains.dtoconvertors.resources.FieldAuditDTOConvertor;
import pw.ewen.WLPT.domains.dtoconvertors.resources.ResourceCheckInDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.artifactshop.ArtifactShopResource;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.PermissionService;

/**
 * created by wenliang on 2021-12-24
 */
@Component
public class ArtifactShopResourceDTOConvertor extends DTOBaseConvertor<ArtifactShopResource, ArtifactShopResourceDTO> {
    protected ArtifactShopResourceDTOConvertor(FieldAuditDTOConvertor fieldAuditDTOConvertor, ResourceCheckInDTOConvertor resourceCheckInDTOConvertor, PermissionService permissionService, PermissionDTOConvertor permissionDTOConvertor, UserContext userContext) {
        super(fieldAuditDTOConvertor, resourceCheckInDTOConvertor, permissionService, permissionDTOConvertor, userContext);
    }

    public ArtifactShopResourceDTO toDTO(ArtifactShopResource artifactShopResource) {
        return toDTO(artifactShopResource, true);
    }

    public ArtifactShopResourceDTO toDTO(ArtifactShopResource artifactShopResource, boolean fetchLazy) {
        ArtifactShopResourceDTO dto = new ArtifactShopResourceDTO();

        dto.setBh(artifactShopResource.getBh());
        dto.setId(artifactShopResource.getId());
        dto.setFaren(artifactShopResource.getFaren());
        dto.setCsdz(artifactShopResource.getCsdz());
        dto.setBh(artifactShopResource.getBh());
        dto.setLxdh(artifactShopResource.getLxdh());
        dto.setSbxm(artifactShopResource.getSbxm());
        dto.setSqdw(artifactShopResource.getSqdw());
        dto.setLxr(artifactShopResource.getLxr());

        this.setReadedInfoToDTO(artifactShopResource, dto);
        if(!fetchLazy) this.setExtraInfoToDTO(artifactShopResource, dto);

        return dto;
    }

    public ArtifactShopResource toResource(ArtifactShopResourceDTO dto) {
        ArtifactShopResource as = new ArtifactShopResource();
        as.setBh(dto.getBh());
        as.setId(dto.getId());
        as.setLxdh(dto.getLxdh());
        as.setLxr(dto.getLxr());
        as.setSbxm(dto.getSbxm());
        as.setFaren(dto.getFaren());
        as.setCsdz(dto.getCsdz());
        as.setSqdw(dto.getSqdw());
        this.setExtraInfoToResource(dto, as);
        return as;
    }
}
