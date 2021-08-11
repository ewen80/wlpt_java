package pw.ewen.WLPT.domains.dtoconvertors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Component;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.DTOs.permissions.PermissionDTO;

import org.springframework.security.acls.model.Permission;
import pw.ewen.WLPT.security.acl.ChangdiPermission;

/**
 * created by wenliang on 2021-04-06
 */
@Component
public class PermissionDTOConvertor {

    @Autowired
    private BizConfig bizConfig;
    // 无法转换返回 null
    public Permission toPermission(PermissionDTO dto) {
        //系统支持的权限类型
//        Permission[] allPermissions = {BasePermission.READ, BasePermission.WRITE, ChangdiPermission.NEW, ChangdiPermission.FINISH};
        for(org.springframework.security.acls.model.Permission p : bizConfig.getPermission().getArray()) {
            if(dto.getMask() == p.getMask()) {
                return p;
            }
        }
        return null;
    }

    public PermissionDTO toDTO(Permission permission) {
        PermissionDTO dto = new PermissionDTO(permission.getMask());
        return  dto;
    }
}
