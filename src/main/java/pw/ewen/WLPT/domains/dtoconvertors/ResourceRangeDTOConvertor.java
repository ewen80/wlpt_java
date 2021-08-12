package pw.ewen.WLPT.domains.dtoconvertors;

import org.springframework.util.Assert;
import pw.ewen.WLPT.domains.DTOs.ResourceRangeDTO;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.services.ResourceTypeService;
import pw.ewen.WLPT.services.RoleService;

/**
 * created by wenliang on 2021/3/29
 */
public class ResourceRangeDTOConvertor {

    public ResourceRange toResourceRange(ResourceRangeDTO dto, RoleService roleService, ResourceTypeService resourceTypeService) {
        ResourceRange range = new ResourceRange();
        range.setId(dto.getId());
        range.setFilter(dto.getFilter());
        roleService.findOne(dto.getRoleId()).ifPresent(range::setRole);
        resourceTypeService.findOne((dto.getResourceTypeClassName())).ifPresent(range::setResourceType);
        return range;
    }

    public ResourceRangeDTO toDTO(ResourceRange range) {
        ResourceRangeDTO dto = new ResourceRangeDTO();
        dto.setId(range.getId());
        dto.setFilter(range.getFilter());
        dto.setResourceTypeClassName(range.getResourceType().getClassName());
        dto.setRoleId(range.getRole().getId());
        return dto;
    }
}
