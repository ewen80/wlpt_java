package pw.ewen.WLPT.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.DTOs.resources.MenuDTO;
import pw.ewen.WLPT.domains.ResourceRangePermissionWrapper;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.services.*;
import pw.ewen.WLPT.services.resources.MenuService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 系统初始化
 */
@RestController
public class OnceInitController {

    private final ResourceTypeService resourceTypeService;
    private final PermissionService permissionService;
    private final RoleService roleService;
    private final ResourceRangeService resourceRangeService;
    private final MenuService menuService;
    private final BizConfig bizConfig;

    @Autowired
    public OnceInitController(ResourceTypeService resourceTypeService,
                              ResourceRangeService resourceRangeService,
                              PermissionService permissionService,
                              MenuService menuService,
                              RoleService roleService, BizConfig bizConfig) {
        this.resourceTypeService = resourceTypeService;
        this.permissionService = permissionService;
        this.roleService = roleService;
        this.resourceRangeService = resourceRangeService;
        this.menuService = menuService;
        this.bizConfig = bizConfig;
    }


    //初始化菜单权限
    //admin角色对所有菜单都有权限
    private void authorizeMenu(Role role){
        ResourceType menuResourceType = new ResourceType("pw.ewen.WLPT.domains.entities.resources.Menu", "menu", "系统菜单");
        this.resourceTypeService.save(menuResourceType);

        ResourceRange range = this.resourceRangeService.findByResourceTypeAndRole(menuResourceType.getClassName(), role.getId());
        if(range == null) {
            range = new ResourceRange("", role, menuResourceType);
            this.resourceRangeService.save(range);
        }

        //添加ACL权限,对所有菜单有权限(READ WHITE)
        ResourceRangePermissionWrapper wrapper = permissionService.getByResourceRange(range.getId());
        HashSet<Permission> permissions = new HashSet<>();
        permissions.add(BasePermission.READ);
        permissions.add(BasePermission.WRITE);

        permissionService.insertPermissions(range.getId(), permissions);
    }

//    // 对MyResource配置权限
//    private void authorizeMyResource(Role role) {
//        ResourceType menuResourceType = new ResourceType("pw.ewen.WLPT.domains.entities.resources.myresource.MyResource", "MyResource", "我的资源");
//        this.resourceTypeService.save(menuResourceType);
//
//        ResourceRange range = this.resourceRangeService.findByResourceTypeAndRole(menuResourceType.getClassName(), role.getId());
//        if(range == null) {
//            range = new ResourceRange("", role, menuResourceType);
//            this.resourceRangeService.save(range);
//        }
//
//        //添加ACL权限,对所有菜单有权限(READ WHITE)
//        ResourceRangePermissionWrapper wrapper = permissionService.getByResourceRange(range.getId());
//        HashSet<Permission> permissions = new HashSet<>();
//        permissions.add(BasePermission.READ);
//        permissions.add(BasePermission.WRITE);
//
//        permissionService.insertPermissions(range.getId(), permissions);
//    }

    private void authorizeResources(Role role) {
        List<BizConfig.Resource> resources = bizConfig.getResources();
        for(BizConfig.Resource resource: resources) {
            ResourceType resourceType = new ResourceType(resource.getType(), resource.getTypeName(), resource.getDescription());
            this.resourceTypeService.save(resourceType);

            ResourceRange range = this.resourceRangeService.findByResourceTypeAndRole(resourceType.getClassName(), role.getId());
            if(range == null) {
                range = new ResourceRange("", role, resourceType);
                this.resourceRangeService.save(range);
            }

            //添加ACL权限,对所有菜单有权限(READ WHITE)
            ResourceRangePermissionWrapper wrapper = permissionService.getByResourceRange(range.getId());
            HashSet<Permission> permissions = new HashSet<>();
            permissions.add(BasePermission.READ);
            permissions.add(BasePermission.WRITE);

            permissionService.insertPermissions(range.getId(), permissions);
        }
    }


    /**
     * 对admin菜单进行授权
     * @apiNote 系统首次启动时需要运行，只有admin组可以操作
     */
    @PutMapping(value = "/admin/menuinit")
    @Transactional
    public List<MenuDTO> adminMenuInit() {
        Optional<Role> adminRole = this.roleService.findOne(bizConfig.getUser().getAdminRoleId());
        if(adminRole.isPresent()) {
            this.authorizeMenu(adminRole.get());
            this.authorizeResources(adminRole.get());
            return this.menuService.findPermissionMenuTree(adminRole.get()).stream().map(MenuDTO::convertFromMenu).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

}
