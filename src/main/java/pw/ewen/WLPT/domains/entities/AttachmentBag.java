package pw.ewen.WLPT.domains.entities;

import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * created by wenliang on 2021-09-17
 * 附件包
 */
@Entity
public class AttachmentBag implements Serializable {
    private static final long serialVersionUID = 8142432763637398081L;
    /**
     * id
     */
    @Id
    @GeneratedValue
    private long id;
    /**
     * 包名
     */
    private String name;
    /**
     * 备注
     */
    private String memo;
    /**
     * 附件列表
     */
    @OneToMany(cascade= CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;
    /**
     * 创建时间
     */
    private String createdAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttachmentBag that = (AttachmentBag) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
