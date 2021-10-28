package pw.ewen.WLPT.repositories.resources.yule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceBase;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * created by wenliang on 2021/10/6
 */
@Repository
public interface YuleResourceBaseRepository extends JpaRepository<YuleResourceBase, Long>, JpaSpecificationExecutor<YuleResourceBase> {
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    List<YuleResourceBase> findAll();
}
