package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.exceptions.domain.FindUserException;
import pw.ewen.WLPT.repositories.UserRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;

import java.util.List;

/**
 * Created by wenliang on 17-4-14.
 */
@Service
@PreAuthorize("hasAuthority(@bizConfig.user.adminRoleId)")
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<User> findAll(String filter, PageRequest pr) {
        // 默认过滤已删除用户
        SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();
        String filterStr = "deleted:false," + filter;
        return  this.userRepository.findAll(builder.build(filterStr), pr);
    }

    /**
     * 返回翻页格式用户列表
     * @param pr    分页对象
     */
    public Page<User> findAll(PageRequest pr){
        // 默认过滤已删除用户
        Specification<User> spec = (root, query, cb) -> cb.isFalse(root.get("deleted"));
        return this.userRepository.findAll(spec, pr);
    }

    /**
     * 返回所有用户，没有过滤条件
     */
    public List<User> findAll() {
        // 默认过滤已删除用户
        Specification<User> spec = (root, query, cb) -> cb.isFalse(root.get("deleted"));
        return this.userRepository.findAll(spec);
    }

    public List<User> findAll(String filter) {
        SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();
        String filterStr = "deleted:false," + filter;
        // 默认过滤已删除用户
        return this.userRepository.findAll(builder.build(filterStr));
    }

    /**
     * 返回一个用户
     * @param id 用户id
     * @return 如果没有找到返回null
     */
    @PreAuthorize("#id == @userContext.getCurrentUser().getId() or hasAuthority(@bizConfig.user.adminRoleId)")
    public User findOne(String id){
        return this.userRepository.findOne(id);
    }

    public User save(User user){
        return this.userRepository.save(user);
    }

    /**
     * 修改用户密码
     * @param userId 用户id
     * @param passwordMD5 用户原始密码MD5
     * @throws FindUserException
     */
    public void setpassword(String userId, String passwordMD5) throws  FindUserException {
        User user = this.userRepository.findOne(userId);
        if (user != null) {
            String encodedPassword = passwordEncoder.encode(passwordMD5);
            user.setPassword(encodedPassword);
            this.userRepository.save(user);
        } else {
            throw new FindUserException("找不到指定的用户");
        }
    }

    /**
     * 检查密码是否正确
     * @param userId 用户id
     * @param passwordMD5 经过MD5后的待验证密码
     * @return 密码是否正确
     */
    public boolean checkPassword(String userId, String passwordMD5) {
        String correctEncodedPassword = this.findOne(userId).getPassword();
        return passwordEncoder.matches(passwordMD5, correctEncodedPassword);
    }

    /**
     * 删除用户
     * @param userIds 用户id数组
     * @return 删除的行数
     */
    public int delete(List<String> userIds){
        return this.userRepository.softdelete(userIds);
    }

}
