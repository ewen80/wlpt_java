package pw.ewen.WLPT.domains.DTOs.resources.permissionwrappers;

import pw.ewen.WLPT.domains.DTOs.permissions.PermissionDTO;
import pw.ewen.WLPT.domains.DTOs.resources.myresource.MyResourceDTO;

import java.util.List;

/**
 * created by wenliang on 2021/5/23
 */
public class MyResourceDTOPermissionWrapper {

    private MyResourceDTO myResourceDTO;
    private List<PermissionDTO> permissions;

    public MyResourceDTO getMyResourceDTO() {
        return myResourceDTO;
    }

    public void setMyResourceDTO(MyResourceDTO myResourceDTO) {
        this.myResourceDTO = myResourceDTO;
    }

    public List<PermissionDTO> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionDTO> permissions) {
        this.permissions = permissions;
    }
}
