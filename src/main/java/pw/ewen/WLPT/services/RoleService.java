package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.exceptions.domain.DeleteRoleException;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * created by wenliang on 20210226
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * 找到一个角色
     * @param id    角色id
     * @return  如果没有找到返回null
     */
    @PreAuthorize("#id == @userContext.getCurrentUser().getCurrentRole().getId() or hasAuthority(@bizConfig.user.adminRoleId)")
    public Optional<Role> findOne(String id) {
        return this.roleRepository.findById(id);
    }

    /**
     * 返回所有角色(不分页）
     */
    @PreAuthorize("hasAuthority(@bizConfig.user.adminRoleId)")
    public List<Role> findAll() {
        // 默认过滤软删除角色
        Specification<Role> spec = (root, query, cb) -> cb.isFalse(root.get("deleted"));
        return this.roleRepository.findAll(spec);
    }

    /**
     * 返回所有角色（分页）
     * @param pr    分页对象
     * @return  角色
     */
    @PreAuthorize("hasAuthority(@bizConfig.user.adminRoleId)")
    public Page<Role> findAll(PageRequest pr)  {
        // 默认过滤软删除角色
        Specification<Role> spec = (root, query, cb) -> cb.isFalse(root.get("deleted"));
        return this.roleRepository.findAll(spec, pr);
    }

    @PreAuthorize("hasAuthority(@bizConfig.user.adminRoleId)")
    public Page<Role> findAll(String filter, PageRequest pr) {
        SearchSpecificationsBuilder<Role> builder = new SearchSpecificationsBuilder<>();
        // 默认过滤软删除角色
        String filterStr = "deleted:false," + filter;
        return this.roleRepository.findAll(builder.build(filter), pr);
    }

    /**
     * 保存
     * @param role 角色
     */
    @PreAuthorize("#role.getId() == @userContext.getCurrentUser().getCurrentRole().getId() or hasAuthority(@bizConfig.user.adminRoleId)")
    public Role save(Role role) {
        return this.roleRepository.save(role);
    }

    /**
     * 通过角色ids删除角色，如果没有用户则硬删除，如果有软删除用户则软删除，如果角色下有用户和权限配置则抛出异常
     * @param roleIds   角色id数组
     */
    @PreAuthorize("hasAuthority(@bizConfig.user.adminRoleId)")
    public void delete(List<String> roleIds) throws DeleteRoleException {
        for(String id : roleIds) {
            this.delete(id);
        }
    }

    /**
     * 通过角色id软删除角色，
     * @param roleId    角色id
     */
    @PreAuthorize("#roleId == @userContext.getCurrentUser().getCurrentRole().getId() or hasAuthority(@bizConfig.user.adminRoleId)")
    public void delete(String roleId) {
//        Optional<Role> role = this.roleRepository.findById(roleId);
//        if(role.isPresent()) {
//            if(role.get().getAllUsers().size() == 0) {
//                // 如果角色下面没有用户，则硬删除角色
//                this.roleRepository.deleteById((roleId));
//            } else if (role.get().getUsers().size() == 0 && role.get().getResourceRanges().size() == 0) {
//                // 如果角色下没有有效用户且没有权限配置，则软删除角色
//                this.roleRepository.softdelete(Collections.singletonList(roleId));
//            } else {
//                throw new DeleteRoleException("删除角色失败，该角色可能还有用户或者权限配置。");
//            }
//        }
        this.roleRepository.softdelete(Collections.singletonList(roleId));
    }

}
