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
import java.util.Optional;

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
    public Optional<User> findOne(String id){
        return this.userRepository.findById(id);
    }

    public User save(User user){
        return this.userRepository.save(user);
    }

    /**
     * 修改密码
     * @param userId 用户id
     * @param passwordMD5 md5编码后的用户密码
     * @throws FindUserException
     */
    public void setpassword(String userId, String passwordMD5) throws  FindUserException {
        Optional<User> user = this.userRepository.findById(userId);
        if (user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(passwordMD5));
            this.userRepository.save(user.get());
        } else {
            throw new FindUserException("找不到指定的用户");
        }
    }

    /**
     * 检查密码
     * @param userId 用户id
     * @param passwordMd5 md5编码后的用户密码
     * @return 密码是否正确
     */
    public boolean checkPassword(String userId, String passwordMd5) {
        return findOne(userId)
                .map(user -> this.passwordEncoder.matches(passwordMd5, user.getPassword()))
                .orElse(false);
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
