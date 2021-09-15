package pw.ewen.WLPT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pw.ewen.WLPT.domains.entities.resources.FieldAudit;

/**
 * created by wenliang on 2021/9/12
 */
@Repository
public interface FieldAuditRepository extends JpaRepository<FieldAudit, Long>, JpaSpecificationExecutor<FieldAudit> {
}
