package pw.ewen.WLPT.domains.DTOs;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * created by wenliang on 2021-03-17
 */
public class RoleDTO {

    private String id;
    private String name;
    private String description;
    private Set<String> userIds = new HashSet<>();

    public RoleDTO() {  }

    public RoleDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public RoleDTO(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<String> userIds) {
        this.userIds = userIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDTO roleDTO = (RoleDTO) o;
        return id.equals(roleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //    public Set<UserDTO> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<UserDTO> users) {
//        this.users = users;
//    }

//    public static RoleDTO convertFromRole(Role role) {
//        RoleDTOConvertor converter = new RoleDTOConvertor(roleService, userDTOConvertor);
//        return converter.toDTO(role);
//    }
//
//    public static Role convertToRole(RoleDTO roleDTO, RoleService roleService) {
//        RoleDTOConvertor converter = new RoleDTOConvertor(roleService, userDTOConvertor);
//        return converter.toRole(roleDTO, roleService);
//    }
}
