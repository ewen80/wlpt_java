package pw.ewen.WLPT.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.annotation.SessionScope;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 17-2-26.
 * 当前登录用户上下文
 */
@Component
@SessionScope
public class UserContext {
    private User currentUser;

    //没有找到用户返回null
    public User getCurrentUser() {
//        SecurityContext context = SecurityContextHolder.getContext();
//        Authentication authentication = context.getAuthentication();
//        if (authentication == null) {
//            return null;
//        }
//        String userId = authentication.getName();
//        if (userId == null) {
//            return null;
//        }

//        return userRepository.findById(userId).orElse(null);
        return this.currentUser;
    }

//    public void setCurrentUser(User user) {
//        Assert.notNull(user, "user cannot be null");
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getId());
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
//                user.getPassword(),userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }

    public void setCurrentUser(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            List<GrantedAuthority> newAuthority = new ArrayList<>();
            newAuthority.add(new SimpleGrantedAuthority(user.getCurrentRole().getId()));
            Authentication newAuthentication = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), newAuthority);
            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
        }
        this.currentUser = user;
    }
}
