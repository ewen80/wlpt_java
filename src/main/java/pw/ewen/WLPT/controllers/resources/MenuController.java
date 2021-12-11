package pw.ewen.WLPT.controllers.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.DTOs.resources.MenuDTO;
import pw.ewen.WLPT.domains.dtoconvertors.resources.MenuDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.Menu;
import pw.ewen.WLPT.services.MenuService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    @GetMapping
    public List<MenuDTO> getTree() {
        return this.menuService.findTree().stream().map(menuDTOConvertor::toDTO).collect(Collectors.toList());
    }

    /**
     * 返回有权限的菜单树
     * @param roleId 角色id
     * @apiNote   返回树形json格式
     */
    @GetMapping(value = "/{roleId}")
    public List<MenuDTO> getAuthorizedMenuTree(@PathVariable("roleId") String roleId){
        return this.menuService.findPermissionMenuTree(roleId).stream().map(menuDTOConvertor::toDTO).collect(Collectors.toList());
    }

    /**
     * 保存菜单
     * @param dto 菜单DTO
     */
    @PutMapping()
    public MenuDTO save(@RequestBody MenuDTO dto){
        Menu menu = menuDTOConvertor.toMenu(dto, menuService);
        return menuDTOConvertor.toDTO(this.menuService.save(menu));
    }

    /**
     * 保存全部菜单
     * @param dtos  全部菜单
     */
    @PutMapping(value = "/all")
    public List<MenuDTO> saveAll(@RequestBody List<MenuDTO> dtos) {
        List<Menu> menus = new ArrayList<>();
        dtos.forEach(dto->{
            Menu menu = menuDTOConvertor.toMenu(dto, menuService);
            menus.add(menu);
        });
        List<Menu> saveMenus = this.menuService.batchSave(menus);
        List<MenuDTO> saveMenuDTOs = new ArrayList<>();
        saveMenus.forEach(menu -> {
            MenuDTO dto = menuDTOConvertor.toDTO(menu);
            saveMenuDTOs.add(dto);
        });
        return saveMenuDTOs;
    }

    /**
     * 删除菜单
     * @param menuId 菜单id
     */
    @DeleteMapping(value = "/{menuId}")
    public void delete(@PathVariable("menuId") String menuId){
        long longMenuId = Long.parseLong(menuId);
        Optional<Menu> menu = this.menuService.findOne(longMenuId);
        if(menu.isPresent()) {
            MenuDTO menuDTO = menuDTOConvertor.toDTO(menu.get());
            this.menuService.delete(menuDTO.getId());
        }

    }

//    @PreAuthorize("hasAuthority(@bizConfig.user.adminRoleId)")  // 只有管理员能删除菜单
//    private void delete(MenuDTO menuDTO) {
//        this.menuService.delete(menuDTO.getId());
//    }
}
