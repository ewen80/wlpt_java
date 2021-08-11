package pw.ewen.WLPT.domains.DTOs;

import org.springframework.util.Assert;
import pw.ewen.WLPT.domains.dtoconvertors.ResourceRangeDTOConvertor;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.services.ResourceTypeService;
import pw.ewen.WLPT.services.RoleService;

import java.io.Serializable;

/**
 * Created by wen on 17-4-9.
 */
public class ResourceRangeDTO implements Serializable {

    private static final long serialVersionUID = -6541329556847931902L;

    /**
     * 资源范围id
     */
    private long id;
    /**
     * 范围过滤器
     */
    private String filter;
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 资源类型类名称
     */
    private String resourceTypeClassName;

    /**
     * 转化DTO为ResourceRange对象
     */
    public ResourceRange convertToResourceRange(RoleService roleService, ResourceTypeService resourceTypeService){
        ResourceRangeDTOConvertor converter = new ResourceRangeDTOConvertor();
        return converter.toResourceRange(this, roleService, resourceTypeService);
    }

    /**
     * 转化ResourceRange对象为DTO对象
     */
    public static ResourceRangeDTO convertFromResourceRange(ResourceRange range){
        ResourceRangeDTOConvertor converter = new ResourceRangeDTOConvertor();
        return converter.toDTO(range);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourceTypeClassName() {
        return resourceTypeClassName;
    }

    public void setResourceTypeClassName(String resourceTypeClassName) {
        this.resourceTypeClassName = resourceTypeClassName;
    }

    public boolean isMatchAll() {
        return this.filter.isEmpty();
    }

    public void setMatchAll() {
        this.filter = "";
    }
}
