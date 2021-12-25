package pw.ewen.WLPT.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.autoconfigure.security.servlet.StaticResourceRequest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.repositories.UserRepository;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by wen on 17-2-8.
 * Spring Security 配置类
 * Web级别
 */
@EnableWebSecurity  //启用web安全性
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BizConfig bizConfig;
//    @Value("${frontendUrls}")
//    private String frontendUrls;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web)  {

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();//Disable X-Frame-Options in Spring Security,用于H2 Console

        http
            .authorizeRequests()
                .antMatchers("/h2console/**", "/doc/**").permitAll()   //对嵌入式数据库,对文档静态资源不做用户认证console不做用户认证
                .antMatchers("/adminmenuinit").hasAuthority(bizConfig.getUser().getAdminRoleId()) //对admin角色进行菜单授权，只有admin角色才能操作
                .anyRequest().authenticated()                            //其他访问都需要经过认证
                .and()
                    .httpBasic()   //Basic Authentication 认证方式
                .and()
                    .logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());  // 退出登录后返回200


        http.csrf().disable(); //关闭CSRF检查
//        http.cors().configurationSource(this.getCorsConfigurationSource()); //允许CORS跨域请求
    }

//    CorsConfigurationSource getCorsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "x-requested-with", "token"));
//        corsConfiguration.setAllowedOrigins(Arrays.asList(this.frontendUrls.split(",")));
//        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.setExposedHeaders(Collections.singletonList("Authorization"));
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        return source;
//    }

}
