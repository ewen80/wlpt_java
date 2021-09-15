package pw.ewen.WLPT.domains.DTOs.resources;

import pw.ewen.WLPT.domains.DTOs.SignatureDTO;
import pw.ewen.WLPT.domains.entities.Attachment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * created by wenliang on 2021/9/12
 * 场地审核信息DTO
 */
public class FieldAuditDTO {

    /**
     * id
     */
    private long id;
    /**
     * 审核意见
     */
    private String content;
    /**
     * 场地审核日期
     */
    private String auditDate;
    /**
     * 创建用户id
     */
    private String auditUserId;
    /**
     * 审核单位
     */
    private String auditDepartment;
    /**
     * 附件列表
     */
    private List<Attachment> attachments = new ArrayList<>();
    /**
     * 签名信息
     */
    private SignatureDTO signature;

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

    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }

    public String getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public SignatureDTO getSignature() {
        return signature;
    }

    public void setSignature(SignatureDTO signature) {
        this.signature = signature;
    }

    public String getAuditDepartment() {
        return auditDepartment;
    }

    public void setAuditDepartment(String auditDepartment) {
        this.auditDepartment = auditDepartment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldAuditDTO that = (FieldAuditDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
