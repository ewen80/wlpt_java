package pw.ewen.WLPT.repositories.resources.yule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceGwRoom;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceYyBase;

/**
 * created by wenliang on 2021/10/6
 */
@Repository
public interface YuleResourceYyBaseRepository extends JpaRepository<YuleResourceYyBase, Long>, JpaSpecificationExecutor<YuleResourceYyBase> {
}
