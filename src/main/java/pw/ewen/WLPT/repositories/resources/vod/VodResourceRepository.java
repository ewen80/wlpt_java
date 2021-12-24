package pw.ewen.WLPT.repositories.resources.vod;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import pw.ewen.WLPT.domains.entities.resources.vod.VodResource;
import pw.ewen.WLPT.repositories.utils.GetReadedResourceCount;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * created by wenliang on 2021-12-21
 */
@Repository
public interface VodResourceRepository extends JpaRepository<VodResource, Long>, JpaSpecificationExecutor<VodResource>, GetReadedResourceCount {
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    List<VodResource> findAll();

    /**
     * 获取已读列表数目
     * @param userId 用户id
     * @return 已读数量
     */
    @Query("SELECT COUNT(1)  FROM VodResource v JOIN v.readInfoList r WHERE r.user.id = :userId")
    int getReadedCount(String userId);
}
