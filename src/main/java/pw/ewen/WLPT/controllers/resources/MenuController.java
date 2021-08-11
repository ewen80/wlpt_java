package pw.ewen.WLPT.controllers.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.DTOs.resources.MenuDTO;
import pw.ewen.WLPT.domains.dtoconvertors.resources.MenuDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.Menu;
import pw.ewen.WLPT.services.resources.MenuService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单
 * @apiNote  菜单是特殊的资源，他的权限控制不由acl控制，所以不能在方法上使用hasPermission
 */
@RestController
@RequestMapping(value = "/resources/menus")
public class MenuController {

    private final MenuService menuService;
    private final MenuDTOConvertor menuDTOConvertor;

    @Autowired
    MenuController(MenuService menuService, MenuDTOConvertor menuDTOConvertor) {
        this.menuDTOConvertor = menuDTOConvertor;
        this.menuService = menuService;
    }

    /**
     * 返回顶级菜单
     * @apiNote   返回树形json格式
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<MenuDTO> getTree() {
        return this.menuService.findTree().stream().map(MenuDTO::convertFromMenu).collect(Collectors.toList());
    }

    /**
     * 返回有权限的菜单树
     * @apiNote   返回树形json格式
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/{userId}")
    public List<MenuDTO> getAuthorizedMenuTree(@PathVariable("userId") String userId){
        return this.menuService.findPermissionMenuTree(userId).stream().map(MenuDTO::convertFromMenu).collect(Collectors.toList());
    }

    /**
     * 保存菜单
     * @param dto 菜单DTO
     */
    @RequestMapping(method=RequestMethod.POST, produces="application/json")
    public MenuDTO save(@RequestBody MenuDTO dto){
        Menu menu = dto.convertToMenu(this.menuService);
        return MenuDTO.convertFromMenu(this.menuService.save(menu));
    }

    /**
     * 删除菜单
     * @param menuId 菜单id
     * @throws NumberFormatException
     */
    // TODO 不向接口外返回异常
    @RequestMapping(method=RequestMethod.DELETE, value="/{menuId}")
    public void delete(@PathVariable("menuId") String menuId) throws NumberFormatException{
        long longMenuId = Long.parseLong(menuId);
        Menu menu = this.menuService.findOne(longMenuId);
        MenuDTO menuDTO = menuDTOConvertor.toDTO(menu);
        this.delete(menuDTO);
    }

    @PreAuthorize("hasAuthority(@bizConfig.user.adminRoleId)")  // 只有管理员能删除菜单
    private void delete(MenuDTO menuDTO) {
        this.menuService.delete(menuDTO.getId());
    }
}
