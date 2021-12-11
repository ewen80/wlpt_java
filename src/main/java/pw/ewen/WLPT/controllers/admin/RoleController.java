package pw.ewen.WLPT.controllers.admin;

import org.bouncycastle.util.Iterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.controllers.utils.PageInfo;
import pw.ewen.WLPT.domains.DTOs.RoleDTO;
import pw.ewen.WLPT.domains.dtoconvertors.RoleDTOConvertor;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.services.RoleService;
import pw.ewen.WLPT.services.UserService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色
 */
@RestController
@RequestMapping(value = "/admin/roles")
public class RoleController {
    private final RoleService roleService;
    private final UserService userService;
    private final BizConfig bizConfig;
    private final RoleDTOConvertor roleDTOConvertor;

    @Autowired
    public RoleController(RoleService roleService, UserService userService, BizConfig bizConfig, RoleDTOConvertor roleDTOConvertor) {
        this.roleService = roleService;
        this.userService = userService;
        this.bizConfig = bizConfig;
        this.roleDTOConvertor = roleDTOConvertor;
    }

    /**
     * 获取全部角色
     * @apiNote  返回结果不分页
     */
    @RequestMapping(value="/all", method=RequestMethod.GET, produces="application/json")
    public List<RoleDTO> getAllRoles(){
        return this.roleService.findAll().stream().map(roleDTOConvertor::toDTO).collect(Collectors.toList());
    }

    /**
     * 获取角色
     * @apiNote  返回结果分页
     */
    @RequestMapping(method = RequestMethod.GET, produces="application/json")
    public Page<RoleDTO> getRolesWithPage(PageInfo pageInfo){
        Page<Role> roles;
        PageRequest pr = pageInfo.getPageRequest();

        if(pageInfo.getFilter().isEmpty()){
            roles =  this.roleService.findAll(pr);
        }else{
            roles =  this.roleService.findAll(pageInfo.getFilter(), pr);
        }
        return roles.map(roleDTOConvertor::toDTO);
    }

    /**
     * 获取一个角色
     * @param roleId 角色Id
     * @apiNote  如果找不到返回404
     */
    @RequestMapping(value="/{roleId}", method=RequestMethod.GET, produces="application/json")
    public ResponseEntity<RoleDTO> getOneRole(@PathVariable("roleId") String roleId){
        return roleService.findOne(roleId)
                .map((role) -> new ResponseEntity<>(roleDTOConvertor.toDTO(role), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 检查角色id是否存在
     * @param roleId    角色id
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/check/{roleId}")
    public boolean checkRoleExist(@PathVariable("roleId") String roleId) {
        return roleService.findOne(roleId).isPresent();
    }

    /**
     * 保存角色信息
     * @param roleDTO  角色DTO
     */
    @RequestMapping(method=RequestMethod.POST, produces = "application/json")
    public RoleDTO save(@RequestBody RoleDTO roleDTO) {
        Role role = roleDTOConvertor.toRole(roleDTO);
        return roleDTOConvertor.toDTO(this.roleService.save(role));
    }

//    /**
//     * 删除角色
//     * @param roleIds   角色Id数字
//     */
//    @RequestMapping(method=RequestMethod.DELETE, value = "/{roleIds}")
//    public void delete(@PathVariable String roleIds){
//        String[] arrRoldIds = roleIds.split(",");
//        this.roleService.delete(arrRoldIds);
//    }

    /**
     * 删除角色
     * @param roleIds 角色id(多个id用,分隔)
     * @apiNote 软删除,如果角色下面没有实际用户，硬删除角色
     */
    @DeleteMapping(value = "/{roleIds}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("roleIds") String roleIds){
        List<String> ids = Arrays.asList(roleIds.split(","));
        this.roleService.delete(ids);
    }

    /**
     * 设置角色的用户
     * @param roleId 角色id
     * @param userIds 用户id（多个id用,分隔）
     */
    @RequestMapping(value = "/setusers/{roleId}", method = RequestMethod.PUT)
    public void setUsers(@PathVariable("roleId") String roleId, @RequestBody String[] userIds) {
        Optional<Role> anonymousRole = roleService.findOne(bizConfig.getUser().getAnonymousRoleId());
        Optional<Role> role = roleService.findOne(roleId);
        if( role.isPresent() && anonymousRole.isPresent() ) {
            // 清空该角色原本用户，如被清空用户无其他角色，则归入anonymous组
            Set<User> users = role.get().getUsers();
            users.forEach( user -> {
                Set<Role> roles = user.getRoles();
                if(roles.size() > 1){
                    roles.remove((anonymousRole.get()));
                    user.setRoles(roles);
                    userService.save(user);
                } else if(!roles.contains(anonymousRole.get())){
                    user.setRoles(Collections.singleton(anonymousRole.get()));
                    userService.save(user);
                }
            });
            // 将指定用户加入该角色
            for (String userId : userIds ) {
                Optional<User> user = this.userService.findOne(userId);
                if(user.isPresent()){
                    Set<Role> roles = user.get().getRoles();
                    roles.add(role.get());
                    this.userService.save(user.get());
                }
            }
//            roleService.save(role.get());
        }
    }
}
