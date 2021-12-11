package pw.ewen.WLPT.domains.entities.utils.userunreaded;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 记录用户未读资源数量
 */
@Entity
@Cacheable
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@IdClass(UserReadedId.class)
public class UserReaded implements Serializable {
    private static final long serialVersionUID = -4912194525070944350L;

    public UserReaded() {
    }

    public UserReaded(String userId, String resourceTypeClassName, int readedCount, LocalDateTime updatedAt) {
        this.userId = userId;
        this.resourceTypeClassName = resourceTypeClassName;
        this.readedCount = readedCount;
        this.updatedAt = updatedAt;
    }

    /**
     * 用户id
     */
    @Id
    private String userId;
    /**
     * 资源类型
     */
    @Id
    private String resourceTypeClassName;
    /**
     * 已读资源的数量
     */
    private int readedCount;
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResourceTypeClassName() {
        return resourceTypeClassName;
    }

    public void setResourceTypeClassName(String resourceTypeClassName) {
        this.resourceTypeClassName = resourceTypeClassName;
    }

    public int getReadedCount() {
        return readedCount;
    }

    public void setReadedCount(int readedCount) {
        this.readedCount = readedCount;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
