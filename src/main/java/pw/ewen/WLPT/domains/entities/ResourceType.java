package pw.ewen.WLPT.domains.entities;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import pw.ewen.WLPT.domains.entities.resources.BaseResource;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by wen on 17-3-12.
 * 保存当前系统中的资源类别列表
 */
@Entity
@Cacheable @org.hibernate.annotations.Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ResourceType implements Serializable {

    private static final long serialVersionUID = -2617247962702444217L;

    /**
     * 类的全名称
     */
    @Id
    private String className;

    /**
     * 资源名称
     */
    private String name;
    private String description;
    /**
     * 仓库类名
     */
    private String repositoryBeanName;
    /**
     * 服务类名
     */
    private String serviceBeanName;

    /**
     * 资源范围
     */
    @OneToMany(mappedBy = "resourceType", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<ResourceRange> resourceRanges = new HashSet<>();

    protected ResourceType(){}

    public ResourceType(String className, String name, String description, String repositoryClassName, String serviceBeanName) {
        this.className = className;
        this.name = name;
        this.description = description;
        this.repositoryBeanName = repositoryClassName;
        this.serviceBeanName = serviceBeanName;
//        this.deleted = deleted;
    }

    public ResourceType(String className, String name){
        this.className = className;
        this.name = name;
    }


    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    public Set<ResourceRange> getResourceRanges() { return this.resourceRanges;}
    public void setResourceRanges(Set<ResourceRange> resourceRanges) { this.resourceRanges = resourceRanges;}

    public String getRepositoryBeanName() {
        return repositoryBeanName;
    }

    public void setRepositoryBeanName(String repositoryBeanName) {
        this.repositoryBeanName = repositoryBeanName;
    }

    public String getServiceBeanName() {
        return serviceBeanName;
    }

    public void setServiceBeanName(String serviceBeanName) {
        this.serviceBeanName = serviceBeanName;
    }

    /**
     * 根据Resource返回对应的ResourceType
     */
    @Autowired
    public static ResourceType getFromResouce(BaseResource resource, ResourceTypeRepository repository){
        String resourceTypeName = resource.getClass().getTypeName();
        return repository.getById(resourceTypeName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        ResourceType type = (ResourceType)obj;

        return Objects.equals(className, type.getClassName());
    }
}
