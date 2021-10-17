package pw.ewen.WLPT.controllers.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
        return this.menuService.findTree().stream().map(MenuDTO::convertFromMenu).collect(Collectors.toList());
    }

    /**
     * 返回有权限的菜单树
     * @apiNote   返回树形json格式
     */
    @GetMapping(value = "/{userId}")
    public List<MenuDTO> getAuthorizedMenuTree(@PathVariable("userId") String userId){
        return this.menuService.findPermissionMenuTree(userId).stream().map(MenuDTO::convertFromMenu).collect(Collectors.toList());
    }

    /**
     * 保存菜单
     * @param dto 菜单DTO
     */
    @PutMapping()
    public MenuDTO save(@RequestBody MenuDTO dto){
        Menu menu = dto.convertToMenu(this.menuService);
        return MenuDTO.convertFromMenu(this.menuService.save(menu));
    }

    /**
     * 保存全部菜单
     * @param dtos  全部菜单
     */
    @PutMapping(value = "/all")
    public List<MenuDTO> saveAll(@RequestBody List<MenuDTO> dtos) {
        List<Menu> menus = new ArrayList<>();
        dtos.forEach(dto->{
            Menu menu = dto.convertToMenu(this.menuService);
            menus.add(menu);
        });
        List<Menu> saveMenus = this.menuService.batchSave(menus);
        List<MenuDTO> saveMenuDTOs = new ArrayList<>();
        saveMenus.forEach(menu -> {
            MenuDTO dto = MenuDTO.convertFromMenu(menu);
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
