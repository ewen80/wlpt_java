package pw.ewen.WLPT.repositories.acls;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.ewen.WLPT.domains.entities.acls.acl_class;

/**
 * created by wenliang on 2021/8/21
 */
public interface AclClassRepository extends JpaRepository<acl_class, Long> {
}
