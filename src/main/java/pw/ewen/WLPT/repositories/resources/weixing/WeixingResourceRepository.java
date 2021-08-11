package pw.ewen.WLPT.repositories.resources.weixing;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import pw.ewen.WLPT.domains.entities.resources.weixing.WeixingResource;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * created by wenliang on 2021-07-21
 */
@Repository
public interface WeixingResourceRepository extends JpaRepository<WeixingResource, Long>, JpaSpecificationExecutor<WeixingResource> {

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    List<WeixingResource> findAll();
//    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
//    List<WeixingResource> findAll(Specification<WeixingResource> spec );
}
