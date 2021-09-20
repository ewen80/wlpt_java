package pw.ewen.WLPT.services.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.resources.FieldAudit;
import pw.ewen.WLPT.repositories.FieldAuditRepository;

import java.util.Optional;

/**
 * created by wenliang on 2021/9/12
 */
@Service
public class FieldAuditService {
    private final FieldAuditRepository fieldAuditRepository;

    @Autowired
    public FieldAuditService(FieldAuditRepository fieldAuditRepository) {
        this.fieldAuditRepository = fieldAuditRepository;
    }

    public FieldAudit save(FieldAudit fieldAudit) {
        return this.fieldAuditRepository.save(fieldAudit);
    }
    public Optional<FieldAudit> findOne(long id) {
        return this.fieldAuditRepository.findById(id);
    }
    public void delete(long id) {
        this.fieldAuditRepository.deleteById(id);
    }

    /**
     * 删除该场地审核意见下的附件包
     * @param auditId 审核意见Id
     * @param attachmentBagId 附件包id
     */
    public void deleteAttachmentBag(long auditId, long attachmentBagId) {
        this.fieldAuditRepository.findById(auditId)
                .ifPresent(fieldAudit -> {
                    fieldAudit.getAttachmentBags().removeIf(attachmentBag -> attachmentBag.getId() == attachmentBagId);
                    this.fieldAuditRepository.save(fieldAudit);
                });
    }
}
