package pw.ewen.WLPT.domains.entities.resources;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    protected BaseResource() {}

    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn
    private ResourceCheckIn resourceCheckIn;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ResourceReadInfo> readInfoList = new ArrayList<>();

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

    public List<ResourceReadInfo> getReadInfoList() {
        return readInfoList;
    }

    public void setReadInfoList(List<ResourceReadInfo> readInfoList) {
        this.readInfoList = readInfoList;
    }

    /**
     * 用户对该资源是否已读
     * @param userId    用户id
     */
    public boolean isReaded(String userId) {
        return this.readInfoList.stream().anyMatch(readInfo->readInfo.getUser().getId().equals(userId));
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
