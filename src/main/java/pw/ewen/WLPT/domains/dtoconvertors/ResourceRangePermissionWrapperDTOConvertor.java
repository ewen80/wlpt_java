package pw.ewen.WLPT.domains.dtoconvertors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;
import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.DTOs.ResourceRangeDTO;
import pw.ewen.WLPT.domains.DTOs.permissions.PermissionDTO;
import pw.ewen.WLPT.domains.DTOs.permissions.ResourceRangePermissionWrapperDTO;
import pw.ewen.WLPT.domains.ResourceRangePermissionWrapper;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.security.acl.ChangdiPermission;
import pw.ewen.WLPT.services.ResourceRangeService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * created by wenliang on 2021-04-06
 */
@Component
public class ResourceRangePermissionWrapperDTOConvertor {

    private final PermissionDTOConvertor permissionDTOConvertor;

    @Autowired
    public ResourceRangePermissionWrapperDTOConvertor(PermissionDTOConvertor permissionDTOConvertor) {
        this.permissionDTOConvertor = permissionDTOConvertor;
    }

    // 无法转换返回null
    public ResourceRangePermissionWrapper toResourceRangePermissionWrapper(ResourceRangePermissionWrapperDTO dto, ResourceRangeService resourceRangeService) {
        ResourceRange range = resourceRangeService.findOne(dto.getResourceRangeDTO().getId());
        if(range != null) {
            Set<Permission> permissions = new HashSet<>();
            for(PermissionDTO pDTO : dto.getPermissions()) {
                permissions.add(permissionDTOConvertor.toPermission(pDTO));
            }
            return  new ResourceRangePermissionWrapper(range, permissions);
        } else {
            return null;
        }
    }

    public ResourceRangePermissionWrapperDTO toResourceRangePermissionWrapperDTO(ResourceRangePermissionWrapper wrapper) {
        ResourceRangePermissionWrapperDTO dto = new ResourceRangePermissionWrapperDTO();
        ResourceRangeDTOConvertor rangeDTOConvertor = new ResourceRangeDTOConvertor();
        ResourceRangeDTO rangeDTO = rangeDTOConvertor.toDTO(wrapper.getResourceRange());
        dto.setResourceRangeDTO(rangeDTO);
        Set<PermissionDTO> pDTO = wrapper.getPermissions().stream()
                .map(permissionDTOConvertor::toDTO)
                .collect(Collectors.toSet());
        dto.setPermissions(pDTO);
        return dto;
    }
}
