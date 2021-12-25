package pw.ewen.WLPT.repositories.resources;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import pw.ewen.WLPT.repositories.utils.GetReadedResourceCount;

/**
 * created by wenliang on 2021-12-24
 */
@NoRepositoryBean
public interface ResourceRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>, GetReadedResourceCount {
}
