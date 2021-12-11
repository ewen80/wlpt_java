package pw.ewen.WLPT.domains.dtoconvertors;

import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.DTOs.UserDTO;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.services.RoleService;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * created by wenliang on 2021-03-24
 * UserDTO 和 User 转换类
 */
@Component
public class UserDTOConvertor {

    private final RoleService roleService;

    public UserDTOConvertor(RoleService roleService) {
        this.roleService = roleService;
    }

    public User toUser(UserDTO dto) {
        User user = new User(dto.getId(), dto.getName(), dto.getQxId());
        Set<Role> roles = dto.getRoleIds().stream()
                .map(roleId -> roleService.findOne(roleId).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        user.setRoles(roles);
        if(dto.getDefaultRoleId() != null) {
            roleService.findOne(dto.getDefaultRoleId()).ifPresent(user::setDefaultRole);
        }
        if(dto.getCurrentRoleId() != null) {
            roleService.findOne(dto.getCurrentRoleId()).ifPresent(user::setDefaultRole);
        }

        return user;
    }

    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setAvatar(user.getAvatar());
        dto.setQxId(user.getQxId());

        Set<String> roleIds = user.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
        dto.setRoleIds(roleIds);
        if(user.getCurrentRole() != null) {
            dto.setCurrentRoleId(user.getCurrentRole().getId());
        }
        if(user.getDefaultRole() != null) {
            dto.setDefaultRoleId(user.getDefaultRole().getId());
        }

        return dto;
    }
}
