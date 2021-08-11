package pw.ewen.WLPT.repositories.resources;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.ewen.WLPT.domains.entities.resources.ResourceCheckIn;
import pw.ewen.WLPT.domains.entities.resources.ResourceFinish;

/**
 * created by wenliang on 2021-05-25
 */
public interface ResourceFinishRepository extends JpaRepository<ResourceFinish, Long> {
}
