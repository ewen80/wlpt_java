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
}
