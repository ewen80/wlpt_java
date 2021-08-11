package pw.ewen.WLPT.repositories.resources;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.ewen.WLPT.domains.entities.resources.ResourceCheckIn;

import java.util.UUID;

/**
 * created by wenliang on 2021/5/24
 */
public interface ResourceCheckInRepository extends JpaRepository<ResourceCheckIn, UUID> {
}
