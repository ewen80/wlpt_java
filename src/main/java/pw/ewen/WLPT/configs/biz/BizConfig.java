package pw.ewen.WLPT.configs.biz;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.acls.model.Permission;
import org.springframework.stereotype.Component;
import pw.ewen.WLPT.security.acl.ChangdiPermission;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * created by wenliang on 2021-03-22
 * 业务类的配置文件
 */
@Component
@ConfigurationProperties(prefix = "bizconfig")
public class BizConfig {

    // 用户信息配置
    public static class User {
        // 管理员角色id
        private   String adminRoleId;
        private   String adminRoleName;

        // 匿名角色id
        private   String anonymousRoleId;
        private   String anonymousRoleName;

        // 管理员用户id
        private   String adminUserId;
        private   String adminUserName;

        // 游客用户id
        private   String guestUserId;
        private   String guestUserName;

        public String getAdminRoleId() {
            return adminRoleId;
        }

        public void setAdminRoleId(String adminRoleId) {
            this.adminRoleId = adminRoleId;
        }

        public String getAdminRoleName() {
            return adminRoleName;
        }

        public void setAdminRoleName(String adminRoleName) {
            this.adminRoleName = adminRoleName;
        }

        public String getAnonymousRoleId() {
            return anonymousRoleId;
        }

        public void setAnonymousRoleId(String anonymousRoleId) {
            this.anonymousRoleId = anonymousRoleId;
        }

        public String getAnonymousRoleName() {
            return anonymousRoleName;
        }

        public void setAnonymousRoleName(String anonymousRoleName) {
            this.anonymousRoleName = anonymousRoleName;
        }

        public String getAdminUserId() {
            return adminUserId;
        }

        public void setAdminUserId(String adminUserId) {
            this.adminUserId = adminUserId;
        }

        public String getAdminUserName() {
            return adminUserName;
        }

        public void setAdminUserName(String adminUserName) {
            this.adminUserName = adminUserName;
        }

        public String getGuestUserId() {
            return guestUserId;
        }

        public void setGuestUserId(String guestUserId) {
            this.guestUserId = guestUserId;
        }

        public String getGuestUserName() {
            return guestUserName;
        }

        public void setGuestUserName(String guestUserName) {
            this.guestUserName = guestUserName;
        }
    }
    private User user;

    public static class File {
        // 文件上传路径
        private   String fileUploadRootPath;

        public String getFileUploadRootPath() {
            return fileUploadRootPath;
        }

        public void setFileUploadRootPath(String fileUploadRootPath) {
            this.fileUploadRootPath = fileUploadRootPath;
        }
    }
    private File file;

    public static class Permission {
        // 系统支持的权限
        private String allPermissions;

        public String getAllPermissions() {
            return allPermissions;
        }

        public void setAllPermissions(String allPermissions) {
            this.allPermissions = allPermissions;
        }

        public org.springframework.security.acls.model.Permission[] getArray() {
            if (allPermissions != null) {
                String[] permissionsStringArray = allPermissions.split(",");
                ArrayList<org.springframework.security.acls.model.Permission> permissions = new ArrayList<>();

                for (String permissionString : permissionsStringArray) {
                    switch (permissionString) {
                        case "ChangdiPermission.READ":
                            permissions.add(ChangdiPermission.READ);
                            break;
                        case "ChangdiPermission.WRITE":
                            permissions.add(ChangdiPermission.WRITE);
                            break;
                        case "ChangdiPermission.FINISH":
                            permissions.add(ChangdiPermission.FINISH);
                            break;
                    }
                }
                org.springframework.security.acls.model.Permission[] changdiPermissions = new org.springframework.security.acls.model.Permission[permissions.size()];
                return permissions.toArray(changdiPermissions);
            }
            return null;
        }
    }
    private Permission permission;

    public static class SerialNumber {
        //序列号相关
        //  卫星场地核查序列号名
        private   String weixingName;
        //  卫星场地核查序列号依据 [date：YYYY] 按照日期的四位年份
        private   String weixingBasis;

        public String getWeixingName() {
            return weixingName;
        }

        public void setWeixingName(String weixingName) {
            this.weixingName = weixingName;
        }

        public String getWeixingBasis() {
            return weixingBasis;
        }

        public void setWeixingBasis(String weixingBasis) {
            this.weixingBasis = weixingBasis;
        }
    }
    private SerialNumber serialNumber;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public SerialNumber getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(SerialNumber serialNumber) {
        this.serialNumber = serialNumber;
    }
}
