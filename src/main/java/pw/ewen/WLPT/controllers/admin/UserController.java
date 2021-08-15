package pw.ewen.WLPT.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.controllers.utils.PageInfo;
import pw.ewen.WLPT.domains.DTOs.UserDTO;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.exceptions.domain.FindUserException;
import pw.ewen.WLPT.services.RoleService;
import pw.ewen.WLPT.services.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 用户
 */
@RestController
@RequestMapping("/admin/users")
public class UserController {

	private final UserService userService;
	private final RoleService roleService;

	@Autowired
	public UserController(UserService userService, RoleService roleService){
		this.roleService = roleService;
		this.userService = userService;
	}

//	//将user对象转为DTO对象的内部辅助类
//	static class UserDTOConverter implements Converter<User, UserDTO>{
//		@Override
//		public UserDTO convert(User source) {
//			return  UserDTO.convertFromUser(source);
//		}
//	}
	/**
	 * 获取用户
	 * @param pageInfo 分页信息
	 * @apiNote 返回结果分页
	 */
	@RequestMapping(method = RequestMethod.GET, produces="application/json")
	public Page<UserDTO> getUsersWithPage(PageInfo pageInfo){
		Page<User> userResults;
		PageRequest pr = pageInfo.getPageRequest();

		if(pageInfo.getFilter().isEmpty()){
			userResults =  this.userService.findAll(pr);
		}else{
			userResults =  this.userService.findAll(pageInfo.getFilter(), pr);
		}
		return userResults.map(UserDTO::convertFromUser);
	}

	/**
	 * 获取用户信息
	 * @param userId 用户id
	 * @apiNote 如果用户找不到返回404
	 */
	@RequestMapping(value="/{userId}", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<UserDTO> getOne(@PathVariable("userId") String userId){
		return this.userService.findOne(userId)
				.map((user) -> new ResponseEntity<>(UserDTO.convertFromUser(user), HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * 检查用户是否存在
	 * @param userId 用户id
	 */
	@RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/check/{userId}")
	public boolean checkUserExist(@PathVariable("userId") String userId) {
		return userService.findOne(userId).isPresent();
	}

	/**
	 * 获取指定角色的用户
	 * @param roleId 角色id
	 * @param pageInfo 分页信息
	 * @apiNote 返回分页结果
	 */
	@RequestMapping(value="/role/{roleId}", method=RequestMethod.GET, produces="application/json")
	public Page<UserDTO> getByRoleIdWithPage(@PathVariable("roleId") String roleId, PageInfo pageInfo) {
		Page<User> users;
		PageRequest pr = pageInfo.getPageRequest();

		String filter = "role.id:" + roleId + "," + pageInfo.getFilter();
		users = this.userService.findAll(filter, pr);
		return users.map(UserDTO::convertFromUser);
	}

	/**
	 * 获取指定角色的用户
	 * @param roleId 角色id
	 * @param filter 过滤器
	 * @apiNote 不返回分页结果
	 */
	@RequestMapping(value = "/role/nopage/{roleId}", method = RequestMethod.GET, produces = "application/json")
	public List<UserDTO> getByRoleId(@PathVariable("roleId") String roleId, @RequestParam(value = "filter", defaultValue = "") String filter) {
		String filterStr = "role.id:" + roleId + "," + filter;
		List<User> users = this.userService.findAll(filterStr);
		List<UserDTO> userDTOS = new ArrayList<>();
		users.forEach( (user -> userDTOS.add(UserDTO.convertFromUser(user))));
		return userDTOS;
	}

	/**
	 * 获取所有有效用户（未删除的用户）
	 */
	@RequestMapping(value = "/nopage", method = RequestMethod.GET, produces = "application/json")
	public List<UserDTO> getAllValidUsers() {
		String filter = "deleted:false";
		List<User> users = this.userService.findAll(filter);
		List<UserDTO> userDTOS = new ArrayList<>();
		users.forEach( (user -> userDTOS.add(UserDTO.convertFromUser(user))));
		return userDTOS;
	}

	/**
	 * 保存
	 * @param dto 用户信息
	 */
	@RequestMapping(method=RequestMethod.POST, produces="application/json")
	public UserDTO save(@RequestBody UserDTO dto){
		User user = dto.convertToUser(this.roleService);
		return UserDTO.convertFromUser(this.userService.save(user));
    }

	/**
	 * 设置用户密码
	 * @param userId 用户id
	 * @param passwordMD5 MD5加密后的用户密码
	 */
    @RequestMapping(method = RequestMethod.PUT, value = "/{userId}")
    public void setPassword(@PathVariable("userId") String userId, @RequestBody String passwordMD5) {
		this.userService.setpassword(userId, passwordMD5);
	}

	/**
	 * 检查用户密码
	 * @param userId 用户id
	 * @param passwordMD5 MD5加密后的用户密码
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/checkPassword")
	public boolean checkPassword(@RequestParam(value = "userId") String userId, @RequestParam(value = "passwordMD5") String passwordMD5) {
		return this.userService.checkPassword(userId, passwordMD5);
	}

	/**
	 * 删除用户
	 * @param userIds 用户id(多个id用,分隔)
	 * @apiNote 软删除
	 */
    @RequestMapping(value = "/{userIds}", method=RequestMethod.DELETE, produces = "application/json")
    public void delete(@PathVariable("userIds") String userIds){
		List<String> ids = Arrays.asList(userIds.split(","));
		this.userService.delete(ids);
	}
}
