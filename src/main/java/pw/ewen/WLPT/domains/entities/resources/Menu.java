package pw.ewen.WLPT.domains.entities.resources;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 17-5-7.
 * 菜单资源类
 */
@Entity
public class Menu extends BaseResource implements Serializable {

    private static final long serialVersionUID = 7179865199657578507L;

    /**
     * 菜单名
     */
    @Column(nullable = false)
    private String name;

    /**
     * 菜单路径
     */
    @Column
    private String path;

    /**
     * 菜单图标类
     */
    @Column
    private String iconClass = "";

    /**
     * 排序号
     */
    @Column
    private int orderId = 0;

    /**
     * 子节点
     */
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @OrderBy("orderId")
    private List<Menu> children;

    /**
     * 父节点
     */
    @ManyToOne
    @JoinColumn(name = "parent_Id")
    private Menu parent;

    public  Menu() {
        super();
        children = new ArrayList<>();
    }

    public Menu(String name){
        this();
        this.name = name;
    }

    public Menu(String name, String path){
        this(name);
        this.path = path;
    }

    public Menu(String name, String path, int orderId){
        this(name,path);
        this.orderId = orderId;
    }



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


    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }


    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public List<Menu> getChildren() {
        return children;
    }
    public void setChildren(List<Menu> children) {
        this.children = children;
    }


    public Menu getParent() {
        return parent;
    }
    public void setParent(Menu parent) {
        this.parent = parent;
    }

}
