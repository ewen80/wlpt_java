package pw.ewen.WLPT.domains.entities.resources;

import javax.persistence.*;

/**
 * Created by wen on 17-2-28.
 * 资源抽象类
 */
@MappedSuperclass
public abstract class BaseResource {

    /**
     * 资源唯一标志号
     */
    @Id
    @GeneratedValue
    private long id;

    protected BaseResource() {}

    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn
    private ResourceCheckIn resourceCheckIn;

    public long getId(){
        return this.id;
    }
    public void setId(long id){ this.id = id;}

    public ResourceCheckIn getResourceCheckIn() {
        return resourceCheckIn;
    }

    public void setResourceCheckIn(ResourceCheckIn resourceCheckIn) {
        this.resourceCheckIn = resourceCheckIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseResource resource = (BaseResource) o;

        return id == resource.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
