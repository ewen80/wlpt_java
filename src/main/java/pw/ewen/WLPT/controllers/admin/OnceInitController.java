package pw.ewen.WLPT.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.DTOs.resources.MenuDTO;
import pw.ewen.WLPT.domains.ResourceRangePermissionWrapper;
import pw.ewen.WLPT.domains.dtoconvertors.resources.MenuDTOConvertor;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.resources.Menu;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;
import pw.ewen.WLPT.security.acl.ChangdiPermission;
import pw.ewen.WLPT.services.*;

import java.util.*;
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
    private final MenuDTOConvertor menuDTOConvertor;

    @Autowired
    public OnceInitController(ResourceTypeService resourceTypeService,
                              ResourceRangeService resourceRangeService,
                              PermissionService permissionService,
                              MenuService menuService,
                              RoleService roleService, BizConfig bizConfig, MenuDTOConvertor menuDTOConvertor) {
        this.resourceTypeService = resourceTypeService;
        this.permissionService = permissionService;
        this.roleService = roleService;
        this.resourceRangeService = resourceRangeService;
        this.menuService = menuService;
        this.bizConfig = bizConfig;
        this.menuDTOConvertor = menuDTOConvertor;
    }


    //初始化菜单权限
    //admin角色对所有菜单都有权限
    private void authorizeMenu(Role role){
        ResourceType menuResourceType = new ResourceType("pw.ewen.WLPT.domains.entities.resources.Menu", "menu", "系统菜单", "", "");
        this.resourceTypeService.save(menuResourceType);

        ResourceRange range = this.resourceRangeService.findByResourceTypeAndRole(menuResourceType.getClassName(), role.getId());
        if(range == null) {
            range = new ResourceRange("", role, menuResourceType);
            this.resourceRangeService.save(range);
        }

        //添加ACL权限,对所有菜单有权限(READ WHITE NEW DELETE等)
        ResourceRangePermissionWrapper wrapper = permissionService.getByResourceRange(range.getId());
        HashSet<Permission> permissions = new HashSet<>();
        permissions.add(BasePermission.READ);
        permissions.add(BasePermission.WRITE);
        permissions.add(BasePermission.CREATE);
        permissions.add(BasePermission.DELETE);
        permissions.add(ChangdiPermission.FINISH);

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
            ResourceType resourceType = new ResourceType(resource.getType(), resource.getTypeName(), resource.getDescription(), resource.getRepositoryBeanName(), resource.getServiceBeanName());
            this.resourceTypeService.save(resourceType);

            ResourceRange range = this.resourceRangeService.findByResourceTypeAndRole(resourceType.getClassName(), role.getId());
            if(range == null) {
                range = new ResourceRange("", role, resourceType);
                this.resourceRangeService.save(range);
            }

            //添加ACL权限,对所有菜单有权限
            ResourceRangePermissionWrapper wrapper = permissionService.getByResourceRange(range.getId());
            HashSet<Permission> permissions = new HashSet<>();
            permissions.add(BasePermission.READ);
            permissions.add(BasePermission.WRITE);
            permissions.add(BasePermission.CREATE);
            permissions.add(BasePermission.DELETE);
            permissions.add(ChangdiPermission.FINISH);

            permissionService.insertPermissions(range.getId(), permissions);
        }
    }

    //初始化菜单数据
    private void initialMenu() {
        SearchSpecificationsBuilder<Menu> builder = new SearchSpecificationsBuilder<>();

        // 添加业务功能菜单
        List<Menu> bizMenus = menuService.findAll(builder.build("name:业务区"));
        Menu bizMenu;
        if(bizMenus.size() == 0) {
            bizMenu = new Menu();
            bizMenu.setName("业务区");
            bizMenu = this.menuService.save(bizMenu);
        } else {
            bizMenu = bizMenus.get(0);
        }

        builder.reset();
        List<Menu> homeMenus = menuService.findAll(builder.build("name:首页"));
        Menu homeMenu;
        if(homeMenus.size() == 0) {
            homeMenu = new Menu();
            homeMenu.setName("首页");
            homeMenu.setPath("/home");
            homeMenu.setIconClass("dashboard");
            homeMenu.setParent(bizMenu);
            this.menuService.save(homeMenu);
        }


//        builder.reset();
//        List<Menu> myResourceMenus = menuService.findAll(builder.build("name:我的资源"));
//        Menu myResourceMenu;
//        if(myResourceMenus.size() == 0) {
//            myResourceMenu = new Menu();
//            myResourceMenu.setName("我的资源");
//            myResourceMenu.setPath("/resources/myresources");
//            myResourceMenu.setParent(bizMenu);
//            this.menuService.save(myResourceMenu);
//        }

        List<BizConfig.Resource> resources = bizConfig.getResources();
        for(BizConfig.Resource resource: resources) {
            builder.reset();
            List<Menu> menus = menuService.findAll(builder.build("name:" + resource.getName()));
            Menu menu;
            if(menus.size() == 0) {
                menu = new Menu();
                menu.setName(resource.getName());
                menu.setPath(resource.getPath());
                resourceTypeService.findOne(resource.getType())
                                .ifPresent(menu::setResourceType);
                menu.setParent(bizMenu);
                this.menuService.save(menu);
            }
        }

        // 添加后台管理菜单
        builder.reset();
        List<Menu> adminMenus = menuService.findAll(builder.build("name:后台管理"));
        Menu adminMenu;
        if(adminMenus.size() == 0) {
            adminMenu = new Menu();
            adminMenu.setName("后台管理");
            this.menuService.save(adminMenu);
        } else {
            adminMenu = adminMenus.get(0);
        }

        builder.reset();
        List<Menu> usersAdminMenus = menuService.findAll(builder.build("name:用户管理"));
        Menu usersAdminMenu;
        if(usersAdminMenus.size() == 0) {
            usersAdminMenu = new Menu();
            usersAdminMenu.setName("用户管理");
            usersAdminMenu.setPath("/admin/users");
            usersAdminMenu.setIconClass("user");
            usersAdminMenu.setParent(adminMenu);
            this.menuService.save(usersAdminMenu);
        }

        builder.reset();
        List<Menu> rolesAdminMenus = menuService.findAll(builder.build("name:角色管理"));
        Menu rolesAdminMenu;
        if(rolesAdminMenus.size() == 0) {
            rolesAdminMenu = new Menu();
            rolesAdminMenu.setName("角色管理");
            rolesAdminMenu.setPath("/admin/roles");
            rolesAdminMenu.setIconClass("team");
            rolesAdminMenu.setParent(adminMenu);
            this.menuService.save(rolesAdminMenu);
        }

        builder.reset();
        List<Menu> resourcesAdminMenus = menuService.findAll(builder.build("name:资源管理"));
        Menu resourcesAdminMenu;
        if(resourcesAdminMenus.size() == 0) {
            resourcesAdminMenu = new Menu();
            resourcesAdminMenu.setName("资源管理");
            resourcesAdminMenu.setPath("/admin/resources");
            resourcesAdminMenu.setIconClass("appstore");
            resourcesAdminMenu.setParent(adminMenu);
            this.menuService.save(resourcesAdminMenu);
        }

        builder.reset();
        List<Menu> menusAdminMenus = menuService.findAll(builder.build("name:菜单管理"));
        Menu menusAdminMenu;
        if(menusAdminMenus.size() == 0) {
            menusAdminMenu = new Menu();
            menusAdminMenu.setName("菜单管理");
            menusAdminMenu.setPath("/admin/resources/menus");
            menusAdminMenu.setIconClass("menu");
            menusAdminMenu.setParent(adminMenu);
            this.menuService.save(menusAdminMenu);
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
            this.initialMenu();
            this.authorizeMenu(adminRole.get());
            this.authorizeResources(adminRole.get());
            return this.menuService.findPermissionMenuTree(adminRole.get()).stream().map(menuDTOConvertor::toDTO).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

}
