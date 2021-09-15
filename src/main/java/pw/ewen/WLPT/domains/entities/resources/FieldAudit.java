package pw.ewen.WLPT.domains.entities.resources;

import pw.ewen.WLPT.domains.entities.Attachment;
import pw.ewen.WLPT.domains.entities.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * created by wenliang on 2021/9/12
 * 场地审核意见
 */
@Entity
public class FieldAudit implements Serializable {

    private static final long serialVersionUID = -8237988891764566295L;
    @Id
    @GeneratedValue
    private long id;
    /**
     * 审核意见
     */
    private String content;
    /**
     * 场地审核日期
     */
    private LocalDate auditDate;
    /**
     * 创建人
     */
    @OneToOne
    @JoinColumn
    private User user;
    /**
     * 附件列表
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments = new ArrayList<>();
    /**
     * 签名信息
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private Signature signature;
    /**
     * 审核单位名称
     */
    private String auditDepartment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(LocalDate auditDate) {
        this.auditDate = auditDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Signature getSignature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    public String getAuditDepartment() {
        return auditDepartment;
    }

    public void setAuditDepartment(String auditDepartment) {
        this.auditDepartment = auditDepartment;
    }
}