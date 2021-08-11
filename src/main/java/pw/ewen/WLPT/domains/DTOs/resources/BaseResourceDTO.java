package pw.ewen.WLPT.domains.DTOs.resources;

import pw.ewen.WLPT.domains.DTOs.permissions.PermissionDTO;
import pw.ewen.WLPT.domains.entities.resources.ResourceCheckIn;

import java.util.List;

/**
 * created by wenliang on 2021-05-20
 */
public abstract class BaseResourceDTO {

    /**
     * 资源id
     */
    private long id;
    /**
     * 资源对应的权限列表
     */
    private List<PermissionDTO> permissions;
    /**
     * 资源的登记信息
     */
    private ResourceCheckInDTO resourceCheckIn;

    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public List<PermissionDTO> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionDTO> permissions) {
        this.permissions = permissions;
    }

    public ResourceCheckInDTO getResourceCheckIn() {
        return resourceCheckIn;
    }

    public void setResourceCheckIn(ResourceCheckInDTO resourceCheckIn) {
        this.resourceCheckIn = resourceCheckIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseResourceDTO resource = (BaseResourceDTO) o;

        return id == resource.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
