package pw.ewen.WLPT.domains.DTOs;

import pw.ewen.WLPT.domains.dtoconvertors.UserDTOConvertor;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.services.RoleService;

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
     * 角色id
     */
    private String roleId;
    /**
     * 头像
     */
    private String avatar = "";
    /**
     * 所属区id
     */
    private String qxId = "0";

    public UserDTO(String id, String name, String roleId, String avatar, String qxId) {
        this.id = id;
        this.name = name;
        this.roleId = roleId;
        this.avatar = avatar;
        this.qxId = qxId;
    }

    public UserDTO() {  }




    /**
     * 转化User对象为UserDTO对象
     * @param user 用户对象
     * @return 用户DTO对象
     */
    public static UserDTO convertFromUser(User user){
        return new UserDTOConvertor().toDTO(user);
    }

    /**
     * 转化UserDTO对象为User对象
     */
    public User convertToUser(RoleService roleService){
        UserDTOConvertor converter = new UserDTOConvertor();
        return converter.toUser(this, roleService);
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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
}
