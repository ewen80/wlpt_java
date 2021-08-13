package pw.ewen.WLPT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.domains.entities.resources.Menu;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.repositories.UserRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;
import pw.ewen.WLPT.services.resources.MenuService;

import java.util.List;

/**
 * created by wenliang on 2021/3/6
 * 系统初始化工作。对用户、角色和权限配置进行初始化。
 */
@Component
public class ApplicationInit implements ApplicationRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final MenuService menuService;
    private final BizConfig bizConfig;


    @Autowired
    public ApplicationInit(RoleRepository roleRepository,
                           UserRepository userRepository,
                           MenuService menuService, BizConfig bizConfig) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.menuService = menuService;
        this.bizConfig = bizConfig;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initUserAndRole();
        initialMenu();
    }

    // 初始化用户和角色，生成角色："admin"、"anonymous".生成用户："admin"
    private void initUserAndRole() {
        // 检查角色表中是否有anonymous角色,没有就新建
        Role anonymousRole = roleRepository.findOne(bizConfig.getUser().getAnonymousRoleId());
        if(anonymousRole == null) {
            anonymousRole = roleRepository.save(new Role(bizConfig.getUser().getAnonymousRoleId(), bizConfig.getUser().getAnonymousRoleName()));
        }

        Role adminRole = roleRepository.findOne(bizConfig.getUser().getAdminRoleId());
        if(adminRole == null) {
            // 新建admin角色
            adminRole = roleRepository.save(new Role(bizConfig.getUser().getAdminRoleId(), bizConfig.getUser().getAdminRoleName()));
        }

        // 检查是否存在admin用户，没有就新建，默认加入admin角色
        User adminUser = userRepository.findOne(bizConfig.getUser().getAdminUserId());
        if(adminUser == null) {
            //新建用户admin
            User user = new User(bizConfig.getUser().getAdminUserId(), bizConfig.getUser().getAdminUserName(), adminRole);
            user.setPassword(DigestUtils.md5DigestAsHex("admin".getBytes()).toUpperCase());  // 默认密码admin
            adminRole.getUsers().add(user);
            userRepository.save(user);
        }

        // 检查是否存在guest用户，没有就新建，默认加入anonymous角色
        User guestUser = userRepository.findOne(bizConfig.getUser().getGuestUserId());
        if(guestUser == null) {
            // 新建用户guest
            User user = new User(bizConfig.getUser().getGuestUserId(), bizConfig.getUser().getGuestUserName(), anonymousRole);
            user.setPassword(DigestUtils.md5DigestAsHex("guest".getBytes()).toUpperCase());  // 默认密码guest
            anonymousRole.getUsers().add(user);
            userRepository.save(user);
        }
    }

    //初始化菜单数据
    private void initialMenu() {
        SearchSpecificationsBuilder<Menu> builder = new SearchSpecificationsBuilder<>();

        List<Menu> bizMenus = menuService.findAll(builder.build("name:业务区"));
        Menu bizMenu = null;
        if(bizMenus.size() == 0) {
            bizMenu = new Menu();
            bizMenu.setName("业务区");
            this.menuService.save(bizMenu);
        }

        builder.reset();
        List<Menu> homeMenus = menuService.findAll(builder.build("name:首页"));
        Menu homeMenu = null;
        if(homeMenus.size() == 0) {
            homeMenu = new Menu();
            homeMenu.setName("首页");
            homeMenu.setPath("/home");
            homeMenu.setIconClass("dashboard");
            homeMenu.setParent(bizMenu);
            this.menuService.save(homeMenu);
        }

        builder.reset();
        List<Menu> adminMenus = menuService.findAll(builder.build("name:后台管理"));
        Menu adminMenu = null;
        if(adminMenus.size() == 0) {
            adminMenu = new Menu();
            adminMenu.setName("后台管理");
            this.menuService.save(adminMenu);
        }

        builder.reset();
        List<Menu> usersAdminMenus = menuService.findAll(builder.build("name:用户管理"));
        Menu usersAdminMenu = null;
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
        Menu rolesAdminMenu = null;
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
        Menu resourcesAdminMenu = null;
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
        Menu menusAdminMenu = null;
        if(menusAdminMenus.size() == 0) {
            menusAdminMenu = new Menu();
            menusAdminMenu.setName("菜单管理");
            menusAdminMenu.setPath("/admin/resources/menus");
            menusAdminMenu.setIconClass("menu");
            menusAdminMenu.setParent(adminMenu);
            this.menuService.save(menusAdminMenu);
        }

    }

}
