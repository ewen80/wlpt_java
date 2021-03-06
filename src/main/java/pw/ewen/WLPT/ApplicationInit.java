package pw.ewen.WLPT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.repositories.UserRepository;

import java.util.Collections;
import java.util.Optional;

/**
 * created by wenliang on 2021/3/6
 * 系统初始化工作。对用户、角色和权限配置进行初始化。
 */
@Component
public class ApplicationInit implements ApplicationRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BizConfig bizConfig;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationInit(RoleRepository roleRepository,
                           UserRepository userRepository,
                          BizConfig bizConfig, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bizConfig = bizConfig;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        initUserAndRole();
    }

    // 初始化用户和角色，生成角色："admin"、"anonymous".生成用户："admin"
    private void initUserAndRole() {
        // 检查角色表中是否有anonymous角色,没有就新建
        Optional<Role> anonymousRoleOpt = roleRepository.findById(bizConfig.getUser().getAnonymousRoleId());
        Role anonymousRole = anonymousRoleOpt.orElseGet(() -> roleRepository.save(new Role(bizConfig.getUser().getAnonymousRoleId(), bizConfig.getUser().getAnonymousRoleName())));

        Optional<Role> adminRoleOpt = roleRepository.findById(bizConfig.getUser().getAdminRoleId());
        // 新建admin角色
        Role adminRole = adminRoleOpt.orElseGet(() -> roleRepository.save(new Role(bizConfig.getUser().getAdminRoleId(), bizConfig.getUser().getAdminRoleName())));

        // 检查是否存在admin用户，没有就新建，默认加入admin角色
        Optional<User> adminUserOpt = userRepository.findById(bizConfig.getUser().getAdminUserId());
        User adminUser = adminUserOpt.orElseGet(() -> {
            //新建用户admin
            User user = new User(bizConfig.getUser().getAdminUserId(), bizConfig.getUser().getAdminUserName());
            String passwordMD5 = DigestUtils.md5DigestAsHex("admin".getBytes()).toUpperCase();
            user.setPassword(passwordEncoder.encode(passwordMD5));
            user.setRoles(Collections.singleton(adminRole));
            user.setDefaultRole(adminRole);
            return userRepository.save(user);
        });

        // 检查是否存在guest用户，没有就新建，默认加入anonymous角色
        Optional<User> guestUserOpt = userRepository.findById(bizConfig.getUser().getGuestUserId());
        User guestUser = guestUserOpt.orElseGet(() -> {
            // 新建用户guest
            User user = new User(bizConfig.getUser().getGuestUserId(), bizConfig.getUser().getGuestUserName());
            String passwordMD5 = DigestUtils.md5DigestAsHex("guest".getBytes()).toUpperCase();
            user.setPassword(passwordEncoder.encode(passwordMD5));
            user.setRoles(Collections.singleton(anonymousRole));
            user.setDefaultRole(anonymousRole);
            return userRepository.save(user);
        });
    }
}
