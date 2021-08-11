package pw.ewen.WLPT.domains.entities.resources;

import org.hibernate.annotations.Type;
import pw.ewen.WLPT.domains.entities.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * created by wenliang on 2021-05-24
 * 资源登记情况,新增资源一定要有一条对应的登记记录
 */
@Entity
public class ResourceCheckIn implements Serializable {

    private static final long serialVersionUID = -4471764985878738470L;

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    // 是否办结
    private boolean finished = false;

    @OneToOne(mappedBy = "resourceCheckIn", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private ResourceFinish resourceFinish;


    // 创建日期
    @Column(nullable = false)
    private LocalDateTime createdDateTime;

    // 创建人
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User createdUser;

    public ResourceCheckIn(LocalDateTime createdDateTime, User createdUser) {
        this.createdDateTime = createdDateTime;
        this.createdUser = createdUser;
    }

    public ResourceCheckIn(boolean finished, ResourceFinish resourceFinish, LocalDateTime createdDateTime, User createdUser) {
        this.finished = finished;
        this.resourceFinish = resourceFinish;
        this.createdDateTime = createdDateTime;
        this.createdUser = createdUser;
    }

    public ResourceCheckIn() {
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public User getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(User createdUser) {
        this.createdUser = createdUser;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ResourceFinish getResourceFinish() {
        return resourceFinish;
    }

    public void setResourceFinish(ResourceFinish resourceFinish) {
        resourceFinish.setResourceCheckIn(this);
        this.resourceFinish = resourceFinish;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
