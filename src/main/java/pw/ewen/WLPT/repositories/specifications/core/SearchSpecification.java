package pw.ewen.WLPT.repositories.specifications.core;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by wenliang on 17-3-22.
 * 用于动态查询
 */
public class SearchSpecification<T> implements Specification<T> {

    private SearchCriteria criteria;

    public SearchSpecification(SearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        //解决类嵌套属性问题,例如 ResourceRange.resourceType.className等于"className1"
        String key = criteria.getKey();
        String[] nestPropery = key.split("\\.");
        javax.persistence.criteria.Path<String> property = null;
        if(nestPropery.length > 1){
            for(int i = 0; i < nestPropery.length; i++){
                property = property == null ? root.get(nestPropery[i]) : property.get(nestPropery[i]);
            }
        }else{
            property = root.get(nestPropery[0]);
        }
        switch (criteria.getOperation()) {
            case EQUALITY:
                return builder.equal(property, criteria.getValue());
            case NEGATION:
                return builder.notEqual(property, criteria.getValue());
            case GREATER_THAN:
                return builder.greaterThan(property, criteria.getValue().toString());
            case GREATER_THAN_EQUALITY:
                return builder.greaterThanOrEqualTo(property, criteria.getValue().toString());
            case LESS_THAN:
                return builder.lessThan(property, criteria.getValue().toString());
            case LESS_THAN_EQUALITY:
                return builder.lessThanOrEqualTo(property, criteria.getValue().toString());
            case LIKE:
                return builder.like(property, criteria.getValue().toString());
            case STARTS_WITH:
                return builder.like(property, criteria.getValue() + "%");
            case ENDS_WITH:
                return builder.like(property, "%" + criteria.getValue());
            case CONTAINS:
                return builder.like(property, "%" + criteria.getValue() + "%");
            case IN:
                // criteria.getValue() 内保存in()内部的值，以HashSet存在,字段类型是字符串
                CriteriaBuilder.In<String> inClause = builder.in(property);
                if ( criteria.getValue() instanceof HashSet) {
                    HashSet inValues = (HashSet) criteria.getValue();
                    for (Object v : inValues) {
                        inClause.value(v.toString());
                    }
                    return inClause;
                }
                return null;
            default:
                return null;
        }
    }
}
