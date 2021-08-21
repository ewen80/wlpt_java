package pw.ewen.WLPT.repositories.acls;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.ewen.WLPT.domains.entities.acls.acl_entry;

/**
 * created by wenliang on 2021/8/21
 */
public interface AclEntryRepository extends JpaRepository<acl_entry, Long> {
}
