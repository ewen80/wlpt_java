package pw.ewen.WLPT.domains.DTOs.resources;

import pw.ewen.WLPT.domains.DTOs.SignatureDTO;
import pw.ewen.WLPT.domains.entities.AttachmentBag;
import pw.ewen.WLPT.domains.entities.resources.GPS;

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
    private List<AttachmentBag> attachmentBags = new ArrayList<>();
    /**
     * 场地负责人签名信息
     */
    private SignatureDTO fzrSignature;
    /**
     * 场地审核人签名
     */
    private List<SignatureDTO> auditorSignatures = new ArrayList<>();
    /**
     * GPS打卡信息
     */
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

    public List<AttachmentBag> getAttachmentBags() {
        return attachmentBags;
    }

    public void setAttachmentBags(List<AttachmentBag> attachmentBags) {
        this.attachmentBags = attachmentBags;
    }

    public SignatureDTO getFzrSignature() {
        return fzrSignature;
    }

    public void setFzrSignature(SignatureDTO fzrSignature) {
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

    public List<SignatureDTO> getAuditorSignatures() {
        return auditorSignatures;
    }

    public void setAuditorSignatures(List<SignatureDTO> auditorSignatures) {
        this.auditorSignatures = auditorSignatures;
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
