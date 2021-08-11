package pw.ewen.WLPT.security.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.IdentityUnavailableException;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;
import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.NeverMatchedResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.ResourceRangeService;

/**
 * Created by wen on 17-2-26.
 * 根据domain object 找到acl_object_identity中的objectidentity策略实现
 * 由于acl数据库中实际保存的是 ResourceRange，所以需要从domain_object到ResourceRange进行转换
 * 根据domain_object查找符合要求的ResourceRange
 */
@Component
public class ObjectIdentityRetrievalStrategyWLPTImpl implements ObjectIdentityRetrievalStrategy {
    @Autowired
    private UserContext userContext;
    @Autowired
    private ResourceRangeService resourceRangeService;

    @Override
    public ObjectIdentity getObjectIdentity(Object domainObject) throws IdentityUnavailableException {
        //查找ResourceRepository中当前SID对应的ResourceRange
        Role currentUserRole = userContext.getCurrentUser().getRole();
        ResourceRange resourceRange = getResourceRange(domainObject, currentUserRole);
//        if(resourceRange.getClass().equals(NeverMatchedResourceRange.class)) throw new IdentityUnavailableException("从Resource未匹配到对应的ResourceRange");
//        else{
            return new ObjectIdentityImpl(resourceRange);
//        }
//        return null;
    }

    /**
     * 从domain object获得ResourceRange范围对象
     * @return 匹配的ResourceRange，如果没有匹配对象则返回NeverMatchedResourceRange(任何用户不能对此ResourceRange有权限)
     */
    private ResourceRange getResourceRange(Object domainObject, Role role)  {
        ResourceRange matchedRange = resourceRangeService.findByResourceAndRole(domainObject, role.getId());
        //如果没有匹配到返回一个Never_Matched_ResourceRange
        //没有匹配到ResourceRange代表该Role对资源没有访问权
        matchedRange = matchedRange == null ? new NeverMatchedResourceRange() : matchedRange;
        return  matchedRange;
    }
}
