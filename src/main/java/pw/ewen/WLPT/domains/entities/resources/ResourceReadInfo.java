package pw.ewen.WLPT.domains.entities.resources;

import pw.ewen.WLPT.domains.entities.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * created by wenliang on 2021-10-18
 * 资源阅读信息
 */
@Entity
public class ResourceReadInfo implements Serializable {

    private static final long serialVersionUID = 3413329595660496345L;
    @Id
    @GeneratedValue
    private long id;
    /**
     * 阅读人
     */
    @OneToOne
    @JoinColumn
    private User user;
    /**
     * 阅读日期时间
     */
    private LocalDateTime readAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getReadAt() {
        return readAt;
    }

    public void setReadAt(LocalDateTime readAt) {
        this.readAt = readAt;
    }
}
