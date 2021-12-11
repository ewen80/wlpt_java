package pw.ewen.WLPT.domains.entities.resources;

import pw.ewen.WLPT.domains.entities.AttachmentBag;
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
    private List<AttachmentBag> attachmentBags = new ArrayList<>();
    /**
     * 签名信息
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private Signature fzrSignature;
    /**
     * 场地负责人签名信息
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Signature> auditorSignatures = new ArrayList<>();
    /**
     * 审核单位名称
     */
    private String auditDepartment;
    /**
     * GPS打卡信息
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private GPS gps;

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

    public List<AttachmentBag> getAttachmentBags() {
        return attachmentBags;
    }

    public void setAttachmentBags(List<AttachmentBag> attachmentBags) {
        this.attachmentBags = attachmentBags;
    }

    public Signature getFzrSignature() {
        return fzrSignature;
    }

    public void setFzrSignature(Signature fzrSignature) {
        this.fzrSignature = fzrSignature;
    }

    public String getAuditDepartment() {
        return auditDepartment;
    }

    public void setAuditDepartment(String auditDepartment) {
        this.auditDepartment = auditDepartment;
    }

    public GPS getGps() {
        return gps;
    }

    public void setGps(GPS gps) {
        this.gps = gps;
    }

    public List<Signature> getAuditorSignatures() {
        return auditorSignatures;
    }

    public void setAuditorSignatures(List<Signature> auditorSignatures) {
        this.auditorSignatures = auditorSignatures;
    }
}
