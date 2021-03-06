package pw.ewen.WLPT.domains.DTOs.resources;

import pw.ewen.WLPT.domains.dtoconvertors.resources.MenuDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.Menu;
import pw.ewen.WLPT.services.MenuService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 17-5-7.
 */
public class MenuDTO {

    private long id;

    /**
     * 菜单名
     */
    private String name;
    /**
     * 菜单路径
     */
    private String path;
    /**
     * 菜单顺序
     */
    private int orderId;
    /**
     * 菜单图标类
     */
    private String iconClass;
    /**
     * 资源类型名
     */
    private String resourceType;
    /**
     * 子菜单
     */
    private List<MenuDTO> children = new ArrayList<>();
    /**
     * 上级菜单id
     */
    private long parentId; //默认0,表示无上级节点
    /**
     * 未读数
     */
    private int unReadCount;

//    /**
//     * 转化为Menu对象
//     */
//    public Menu convertToMenu(MenuService menuService) {
//        MenuDTOConvertor converter = new MenuDTOConvertor(userContext);
//        return converter.toMenu(this, menuService);
//    }
//
//    /**
//     * 转换为MenuDTO对象
//     */
//    public static MenuDTO convertFromMenu(Menu menu) {
//        MenuDTOConvertor converter = new MenuDTOConvertor(userContext);
//        return converter.toDTO(menu);
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public List<MenuDTO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuDTO> children) {
        this.children = children;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
}
