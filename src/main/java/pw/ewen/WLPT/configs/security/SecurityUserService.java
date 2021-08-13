package pw.ewen.WLPT.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Created by wenliang on 17-2-9.
 * 用户服务（提供用户查找等服务）
 */
@Component
public class SecurityUserService implements UserDetailsService {

    private final UserRepository userRepository;

    public SecurityUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        Role role;
        if(user.isPresent()){
            role = user.get().getRole();
            List<GrantedAuthority> authorities = new ArrayList<>();
            if(role != null){
                authorities.add(new SimpleGrantedAuthority(role.getId()));
            }

            return new org.springframework.security.core.userdetails.User(user.get().getId(), user.get().getPassword(), authorities);
        }

        throw new UsernameNotFoundException("User id: '" + userId + "' not found");
    }

    /**
     * 密码编码器，用于将用户密码编码后存入数据库
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
