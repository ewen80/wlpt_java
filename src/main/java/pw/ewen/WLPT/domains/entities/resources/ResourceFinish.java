package pw.ewen.WLPT.domains.entities.resources;

import pw.ewen.WLPT.domains.entities.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * created by wenliang on 2021-05-25
 * 资源办结情况
 */
@Entity
public class ResourceFinish implements Serializable {
    private static final long serialVersionUID = 6010912594564317222L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime finishDateTime;

    @OneToOne
    @JoinColumn
    private User finishUser;
    private boolean finished;

    @OneToOne
    @JoinColumn
    private ResourceCheckIn resourceCheckIn;

    public ResourceFinish(LocalDateTime finishDateTime, User finishUser, boolean finished) {
        this.finishDateTime = finishDateTime;
        this.finishUser = finishUser;
        this.finished = finished;
    }

    public ResourceFinish() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getFinishDateTime() {
        return finishDateTime;
    }

    public void setFinishDateTime(LocalDateTime finishDateTime) {
        this.finishDateTime = finishDateTime;
    }

    public User getFinishUser() {
        return finishUser;
    }

    public void setFinishUser(User finishUser) {
        this.finishUser = finishUser;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public ResourceCheckIn getResourceCheckIn() {
        return resourceCheckIn;
    }

    public void setResourceCheckIn(ResourceCheckIn resourceCheckIn) {
        this.resourceCheckIn = resourceCheckIn;
    }
}
