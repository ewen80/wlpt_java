package pw.ewen.WLPT.repositories.resources.yule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceGwRoom;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceGwWc;

/**
 * created by wenliang on 2021/10/12
 */
public interface YuleResourceGwWcRepository extends JpaRepository<YuleResourceGwWc, Long>, JpaSpecificationExecutor<YuleResourceGwWc> {
}
