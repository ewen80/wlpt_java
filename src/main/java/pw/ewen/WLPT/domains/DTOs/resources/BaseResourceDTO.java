package pw.ewen.WLPT.domains.DTOs.resources;

import pw.ewen.WLPT.domains.DTOs.permissions.PermissionDTO;

import java.util.ArrayList;
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
     * 编号
     */
    private String bh;
    /**
     * 资源对应的权限列表
     */
    private List<PermissionDTO> permissions;
    /**
     * 资源的登记信息
     */
    private ResourceCheckInDTO resourceCheckIn;
    /**
     * 场地核查信息
     */
    private List<FieldAuditDTO> fieldAudits = new ArrayList<>();
    /**
     * 是否已读
     */
    private boolean readed = false;

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

    public List<FieldAuditDTO> getFieldAudits() {
        return fieldAudits;
    }

    public void setFieldAudits(List<FieldAuditDTO> fieldAudits) {
        this.fieldAudits = fieldAudits;
    }

    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
    }

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
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
