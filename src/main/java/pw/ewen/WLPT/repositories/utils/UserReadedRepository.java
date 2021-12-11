package pw.ewen.WLPT.repositories.utils;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import pw.ewen.WLPT.domains.entities.utils.userunreaded.UserReaded;
import pw.ewen.WLPT.domains.entities.utils.userunreaded.UserReadedId;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * created by wenliang on 2021-11-26
 */
@Repository
public interface UserReadedRepository extends JpaRepository<UserReaded, UserReadedId> {
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    List<UserReaded> findByUserId(String userId);
}
