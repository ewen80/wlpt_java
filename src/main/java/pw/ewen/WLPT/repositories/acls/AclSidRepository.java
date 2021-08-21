package pw.ewen.WLPT.repositories.acls;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.ewen.WLPT.domains.entities.Attachment;
import pw.ewen.WLPT.domains.entities.acls.acl_sid;

/**
 * created by wenliang on 2021/8/21
 */
public interface AclSidRepository extends JpaRepository<acl_sid, Long> {
}
