package pw.ewen.WLPT.domains.DTOs;

import java.util.*;

/**
 * Created by wenliang on 17-4-14.
 */
public class UserDTO {

    /**
     * 用户id
     */
    private String id;
    /**
     * 用户名
     */
    private String name;
    /**
     * 角色ids
     */
    private Set<String> roleIds = new HashSet<>();
    /**
     * 当前使用角色id
     */
    private String currentRoleId;
    /**
     * 默认使用的角色id
     */
    private String defaultRoleId;
    /**
     * 头像
     */
    private String avatar = "";
    /**
     * 所属区id
     */
    private String qxId = "0";

    public UserDTO(String id, String name, String avatar, String qxId) {
        this.id = id;
        this.name = name;
//        this.roles = roles;
        this.avatar = avatar;
        this.qxId = qxId;
    }

    public UserDTO() {  }

//    /**
//     * 转化User对象为UserDTO对象
//     * @param user 用户对象
//     * @return 用户DTO对象
//     */
//    public static UserDTO convertFromUser(User user){
//        return new UserDTOConvertor(roleDTOConvertor).toDTO(user);
//    }
//
//    /**
//     * 转化UserDTO对象为User对象
//     */
//    public User convertToUser(RoleService roleService){
//        UserDTOConvertor converter = new UserDTOConvertor(roleDTOConvertor);
//        return converter.toUser(this, roleService);
//    }

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

    public Set<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<String> roleIds) {
        this.roleIds = roleIds;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getQxId() {
        return qxId;
    }

    public void setQxId(String qxId) {
        this.qxId = qxId;
    }

    public String getCurrentRoleId() {
        return currentRoleId;
    }

    public void setCurrentRoleId(String currentRoleId) {
        this.currentRoleId = currentRoleId;
    }

    public String getDefaultRoleId() {
        return defaultRoleId;
    }

    public void setDefaultRoleId(String defaultRoleId) {
        this.defaultRoleId = defaultRoleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id.equals(userDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
