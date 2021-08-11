package pw.ewen.WLPT.domains.entities;

import javax.persistence.*;

/**
 * Created by wen on 17-2-26.
 * 资源范围类
 * 代表某个范围的资源集合
 * Role和ResourceType不能为空,如果filter为空，表示角色对该对象有全部权限
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames={"role_Id", "resourceType_Id"}))
public class ResourceRange {

    @Id
    @GeneratedValue
    private long id;

    //资源范围筛选依据（Spel）
    @Column(nullable = false)
    private String filter;

    @ManyToOne
    @JoinColumn(name = "role_Id", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "resourceType_Id", nullable = false)
    private ResourceType resourceType;


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public ResourceRange(){   }
    public ResourceRange(String filter, Role role, ResourceType resourceType) {
        this.filter = filter;
        this.role = role;
        this.resourceType = resourceType;
    }


    public String getFilter() {
        return filter;
    }
    public void setFilter(String filter) {
        this.filter = filter;
    }


    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }


    public ResourceType getResourceType() {
        return resourceType;
    }
    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * 本ResourceRange是否匹配所有Resource
     */
    @Transient
    public boolean isMatchAll() {
        return this.filter.isEmpty();
    }

    public void setMatchAll() {
        this.filter = "";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        // 如果role,filter和type都一样则认为两个range相等
        ResourceRange rangeObj = (ResourceRange)obj;
        return this.role.equals(rangeObj.getRole()) && this.filter.equals(rangeObj.getFilter()) && this.resourceType.equals(rangeObj.resourceType);
    }
}
