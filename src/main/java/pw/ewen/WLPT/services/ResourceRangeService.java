package pw.ewen.WLPT.services;

import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.exceptions.security.MatchedMultipleResourceRangesException;
import pw.ewen.WLPT.repositories.ResourceRangeRepository;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;

import java.util.List;
import java.util.Optional;

/**
 * Created by wenliang on 17-4-12.
 */
@Service
public class ResourceRangeService {

    private final ResourceRangeRepository resourceRangeRepository;


    @Autowired
    public ResourceRangeService(ResourceRangeRepository resourceRangeRepository, RoleRepository roleRepository, ResourceTypeRepository resourceTypeRepository) {
        this.resourceRangeRepository = resourceRangeRepository;
    }

    public Optional<ResourceRange> findOne(long id) {
        return resourceRangeRepository.findById(id);
    }

    public List<ResourceRange> findByResourceType(String resourceTypeClassName){
        Assert.hasText(resourceTypeClassName, "resourceTypeClassName不能为空");

        SearchSpecificationsBuilder<ResourceRange> builder = new SearchSpecificationsBuilder<>();
        return this.resourceRangeRepository.findAll(builder.build("resourceType.className:" + resourceTypeClassName));
    }

    public List<ResourceRange> findAll(Specification<ResourceRange> spec) {
        return this.resourceRangeRepository.findAll(spec);
    }

    public List<ResourceRange> findAll(String filter) {
        SearchSpecificationsBuilder<ResourceRange> builder = new SearchSpecificationsBuilder<>();
        return this.resourceRangeRepository.findAll(builder.build(filter));
    }

    /**
     * 根据角色和资源类型返回资源范围，一个角色和一个资源最多只能匹配到一个资源范围
     * @param resourceTypeClassName 资源类名
     * @param roleId 角色id
     * @return 返回资源范围，如果没有匹配到则返回null
     */
    public ResourceRange findByResourceTypeAndRole(String resourceTypeClassName, String roleId) {
        SearchSpecificationsBuilder<ResourceRange> builder = new SearchSpecificationsBuilder<>();
        List<ResourceRange> resultList = this.resourceRangeRepository.findAll(builder.build("resourceType.className:" + resourceTypeClassName + ",role.id:" + roleId));
        if(resultList.size() > 0) {
            return resultList.get(0);
        }
        return null;
    }

    /**
     * 根据domain object 和 roleId 筛选符合条件的唯一 resourceRange对象
     * @param roleId 角色id
     * @return 符合filter筛选条件的ResourceRange,如果没有匹配项返回 null
     */
    public ResourceRange findByResourceAndRole(Object domainObject, String roleId){
        //从ResourceRange仓储中获取所有和当前角色以及指定资源对应的filter
        List<ResourceRange> ranges = this.resourceRangeRepository.findByRole_idAndResourceType_className(roleId, domainObject.getClass().getTypeName());
        //遍历所有filter进行判断表达式是否为true
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext(domainObject);
        Expression exp;
        int matchedRangesNumber = 0;
        ResourceRange matchedResourceRange = null;
        for(Object range : ranges){
            if(range instanceof  ResourceRange){
                //范围是否是全匹配范围
                if(!((ResourceRange) range).isMatchAll()) {
                    exp = parser.parseExpression(((ResourceRange) range).getFilter());
                    Boolean result = exp.getValue(context, Boolean.class);
                    if (result != null && result) {
                        matchedRangesNumber++;
                        //只匹配第一条记录
                        if (matchedResourceRange == null) {
                            matchedResourceRange = (ResourceRange) range;
                        }
                    }
                }else{
                    //如果是全匹配范围，则直接返回该范围
                    return (ResourceRange)range;
                }
            }

        }
        if(matchedRangesNumber > 1){
            //匹配到多条记录
            throw new MatchedMultipleResourceRangesException("matched multipile resource ranges object to domains object: " + domainObject.toString());
        } //没有匹配到记录，matchedResourceRange=null，函数返回null

        return matchedResourceRange;
    }

    public ResourceRange save(ResourceRange range){
        return this.resourceRangeRepository.save(range);
    }

    public void delete(String[] resourceRangeIds){
        for(String id : resourceRangeIds){
            this.resourceRangeRepository.deleteById(Long.parseLong(id));
        }
    }
}
