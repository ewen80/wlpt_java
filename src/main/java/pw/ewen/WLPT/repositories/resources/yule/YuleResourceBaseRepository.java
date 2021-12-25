package pw.ewen.WLPT.repositories.resources.yule;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceBase;
import pw.ewen.WLPT.repositories.resources.ResourceRepository;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * created by wenliang on 2021/10/6
 */
@Repository
public interface YuleResourceBaseRepository extends ResourceRepository<YuleResourceBase, Long> {
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    List<YuleResourceBase> findAll();

    /**
     * 获取已读列表数目
     * @param userId 用户id
     * @return 已读数量
     */
    @Query("SELECT COUNT(1)  FROM YuleResourceBase w JOIN w.readInfoList r WHERE r.user.id = :userId")
    int getReadedCount(String userId);
}
