package pw.ewen.WLPT.domains.dtoconvertors;

import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.DTOs.RoleDTO;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.services.UserService;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * created by wenliang on 2021-03-24
 * RoleDTO 和 Role 之间转换类
 */
@Component
public class RoleDTOConvertor {

    private final UserService userService;

    public RoleDTOConvertor(UserService userService) {
        this.userService = userService;
    }

    public Role toRole(RoleDTO roleDTO) {
        Role role = new Role(roleDTO.getId(), roleDTO.getName(), roleDTO.getDescription());
        Set<String> userIds = roleDTO.getUserIds();
        Set<User> users = userIds.stream()
                .map( userId-> userService.findOne(userId).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        role.setUsers(users);
        return role;
    }

    public RoleDTO toDTO(Role role) {
        RoleDTO dto = new RoleDTO(role.getId(), role.getName(), role.getDescription());
        Set<String> userIds = role.getUsers().stream()
                .map(User::getId)
                .collect(Collectors.toSet());
        dto.setUserIds(userIds);
        return dto;
    }
}
