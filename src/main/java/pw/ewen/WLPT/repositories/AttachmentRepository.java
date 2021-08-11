package pw.ewen.WLPT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pw.ewen.WLPT.domains.entities.Attachment;

/**
 * created by wenliang on 2021/5/17
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, String>, JpaSpecificationExecutor<Attachment> {
}
