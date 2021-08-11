package pw.ewen.WLPT.repositories.resources.myresource;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import pw.ewen.WLPT.domains.entities.resources.myresource.MyResource;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * Created by wen on 17-2-24.
 */
@Repository
public interface MyResourceRepository extends JpaRepository<MyResource, Long>, JpaSpecificationExecutor<MyResource> {
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    List<MyResource> findAll();
//    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
//    List<MyResource> findAll(Specification<MyResource> spec);
}
