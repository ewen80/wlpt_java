package pw.ewen.WLPT.repositories;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * created by wenliang on 2021/8/7
 * 用于实现角色软删除功能
 */
public class RoleRepositoryImpl implements SoftDelete<String> {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public int softdelete(List<String> ids) {
        StringBuilder sb = new StringBuilder();
        ids.forEach((id) -> {
            sb.append("'").append(id).append("',");
        });

        String strIds = sb.deleteCharAt(sb.length() - 1).toString();
        String softDeleteSql =
                "UPDATE Role SET deleted=true WHERE id in (" + strIds + ")";
        return em.createQuery(softDeleteSql).executeUpdate();
    }
}
