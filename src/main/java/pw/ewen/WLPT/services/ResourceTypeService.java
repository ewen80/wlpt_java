package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.exceptions.domain.DeleteResourceTypeException;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;

import java.util.Optional;

/**
 * created by wenliang on 20210226
 */
@Service
@PreAuthorize("hasAuthority(@bizConfig.user.adminRoleId)")
public class ResourceTypeService {

    private final ResourceTypeRepository resourceTypeRepository;

    @Autowired
    public ResourceTypeService(ResourceTypeRepository resourceTypeRepository) {
        this.resourceTypeRepository = resourceTypeRepository;
    }

    public ResourceType save(ResourceType resourceType) {
        return this.resourceTypeRepository.save(resourceType);
    }

    public Page<ResourceType> findAll(String filter, PageRequest pr) {
        SearchSpecificationsBuilder<ResourceType> builder = new SearchSpecificationsBuilder<>();
        return this.resourceTypeRepository.findAll(builder.build(filter), pr);
    }

    public Page<ResourceType> findAll(PageRequest pr) {
        return this.resourceTypeRepository.findAll(pr);
    }

    public Optional<ResourceType> findOne(String className) {
        return this.resourceTypeRepository.findById(className);
    }

    public void delete(String className) throws DeleteResourceTypeException {
        if(checkCanDelete(className)) {
            this.resourceTypeRepository.deleteById(className);
        } else {
            throw new DeleteResourceTypeException("删除ResourceType错误：ResourceType存在关联的ResourceRange");
        }
    }

    public void delete(String[] classNames) throws DeleteResourceTypeException {
        for(String className : classNames) {
            this.delete(className);
        }
    }

    private boolean checkCanDelete(String className) {
        Optional<ResourceType> resourceType = this.resourceTypeRepository.findById(className);
        return resourceType.map(type->type.getResourceRanges().size()==0).orElse(true);
    }
}
