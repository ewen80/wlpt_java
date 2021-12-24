package pw.ewen.WLPT.configs.biz;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import pw.ewen.WLPT.security.acl.ChangdiPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * created by wenliang on 2021-03-22
 * 业务类的配置文件
 */
@Component
@ConfigurationProperties(prefix = "bizconfig")
public class BizConfig {

    /**
     * 用户相关配置
     */
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

        // 新用户默认密码
        private String defaultPassword;

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

        public String getDefaultPassword() {
            return defaultPassword;
        }

        public void setDefaultPassword(String defaultPassword) {
            this.defaultPassword = defaultPassword;
        }
    }
    private User user;

    /**
     * 文件相关配置
     */
    public static class File {
        // 文件上传路径
        private   String fileUploadRootPath;
        // 卫星场地审核意见表模板文件路径
        private String weixingFieldAuditTemplate;
        // 娱乐场地审核意见表模板文件路径
        private String yuleFieldAuditTemplate;
        // vod审核意见表模板文件路径
        private String vodFieldAuditTemplate;

        public String getFileUploadRootPath() {
            return fileUploadRootPath;
        }

        public void setFileUploadRootPath(String fileUploadRootPath) {
            this.fileUploadRootPath = fileUploadRootPath;
        }

        public String getWeixingFieldAuditTemplate() {
            return weixingFieldAuditTemplate;
        }

        public void setWeixingFieldAuditTemplate(String weixingFieldAuditTemplate) {
            this.weixingFieldAuditTemplate = weixingFieldAuditTemplate;
        }

        public String getYuleFieldAuditTemplate() {
            return yuleFieldAuditTemplate;
        }

        public void setYuleFieldAuditTemplate(String yuleFieldAuditTemplate) {
            this.yuleFieldAuditTemplate = yuleFieldAuditTemplate;
        }

        public String getVodFieldAuditTemplate() {
            return vodFieldAuditTemplate;
        }

        public void setVodFieldAuditTemplate(String vodFieldAuditTemplate) {
            this.vodFieldAuditTemplate = vodFieldAuditTemplate;
        }
    }
    private File file;

    /**
     * 权限相关配置
     */
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
                        case "ChangdiPermission.CREATE":
                            permissions.add(ChangdiPermission.CREATE);
                            break;
                        case "ChangdiPermission.DELETE":
                            permissions.add(ChangdiPermission.DELETE);
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

    /**
     * 序列号相关配置
     */
    public static class SerialNumber {
        //序列号相关
        //  卫星场地核查序列号名
        private String weixingName;
        //  卫星场地核查序列号依据 [date：YYYY] 按照日期的四位年份
        private String weixingBasis;
        //  娱乐场地核查序列号名
        private String yuleName;
        //  娱乐场地核查序列号根据
        private String yuleBasis;
        //  视频点播场地核查序列号名
        private String vodName;
        //  视频点播场地核查序列号根据
        private String vodBasis;

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

        public String getYuleName() {
            return yuleName;
        }

        public void setYuleName(String yuleName) {
            this.yuleName = yuleName;
        }

        public String getYuleBasis() {
            return yuleBasis;
        }

        public void setYuleBasis(String yuleBasis) {
            this.yuleBasis = yuleBasis;
        }

        public String getVodName() {
            return vodName;
        }

        public void setVodName(String vodName) {
            this.vodName = vodName;
        }

        public String getVodBasis() {
            return vodBasis;
        }

        public void setVodBasis(String vodBasis) {
            this.vodBasis = vodBasis;
        }
    }
    private SerialNumber serialNumber;

    /**
     * 资源相关配置
     */
    public static class Resource {
        // 资源
        private String name;
        private String path;
        private String type;
        private String repositoryBeanName;
        private String serviceBeanName;
        private String typeName;
        private String description;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getRepositoryBeanName() {
            return repositoryBeanName;
        }

        public void setRepositoryBeanName(String repositoryBeanName) {
            this.repositoryBeanName = repositoryBeanName;
        }

        public String getServiceBeanName() {
            return serviceBeanName;
        }

        public void setServiceBeanName(String serviceBeanName) {
            this.serviceBeanName = serviceBeanName;
        }
    }
    private List<Resource> resources;

    /**
     * 日期格式相关配置
     */
    public static class DateFormat {
        private String localDateTimeFormat;
        private String localDateFormat;
        private String printDateFormat;

        public String getLocalDateTimeFormat() {
            return localDateTimeFormat;
        }

        public void setLocalDateTimeFormat(String localDateTimeFormat) {
            this.localDateTimeFormat = localDateTimeFormat;
        }

        public String getLocalDateFormat() {
            return localDateFormat;
        }

        public void setLocalDateFormat(String localDateFormat) {
            this.localDateFormat = localDateFormat;
        }

        public String getPrintDateFormat() {
            return printDateFormat;
        }

        public void setPrintDateFormat(String printDateFormat) {
            this.printDateFormat = printDateFormat;
        }
    }
    private DateFormat dateFormat;

    private Map<String,String> regionMap;

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

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public Map<String, String> getRegionMap() {
        return regionMap;
    }

    public void setRegionMap(Map<String, String> regionMap) {
        this.regionMap = regionMap;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }
}
