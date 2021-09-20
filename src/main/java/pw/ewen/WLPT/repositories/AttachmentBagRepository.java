package pw.ewen.WLPT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pw.ewen.WLPT.domains.entities.Attachment;
import pw.ewen.WLPT.domains.entities.AttachmentBag;

import java.util.List;

/**
 * created by wenliang on 2021/9/19
 */
@Repository
public interface AttachmentBagRepository extends JpaRepository<AttachmentBag, Long>, JpaSpecificationExecutor<AttachmentBag> {
}
