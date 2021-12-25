package pw.ewen.WLPT.services.resources;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import pw.ewen.WLPT.domains.entities.resources.BaseResource;
import pw.ewen.WLPT.domains.entities.resources.ResourceCheckIn;
import pw.ewen.WLPT.domains.entities.resources.ResourceReadInfo;
import pw.ewen.WLPT.repositories.UserRepository;
import pw.ewen.WLPT.repositories.resources.ResourceRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.SerialNumberService;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * created by wenliang on 2021/12/9
 */
public abstract class ResourceServiceBase<T extends BaseResource> {
    private final UserRepository userRepository;
    private final ResourceRepository<T, Long> repository;
    private final SerialNumberService serialNumberService;
    private final UserContext userContext;

    protected ResourceServiceBase(UserRepository userRepository, ResourceRepository<T, Long> repository, SerialNumberService serialNumberService, UserContext userContext) {
        this.userRepository = userRepository;
        this.repository = repository;
        this.serialNumberService = serialNumberService;
        this.userContext = userContext;
    }

    @PostFilter("hasPermission(filterObject, 'read')")
    public  List<T> findAll(){
        return repository.findAll();
    }
    @PostFilter("hasPermission(filterObject, 'read')")
    public List<T> findAll(String filter){
        SearchSpecificationsBuilder<T> builder = new SearchSpecificationsBuilder<>();
        return this.repository.findAll(builder.build(filter));
    }

    @PostAuthorize("hasPermission(returnObject.orElse(null), 'read')")
    public Optional<T> findOne(long id){
        return repository.findById(id);
    }

    protected T add(T t, String serialName, String serialBasis){
        // 新增生成一条新的ResourceCheckIn记录
        if(t.getId() == 0) {
            // 生成编号
            String serialNumber = serialNumberService.generate(serialName, serialBasis);
            t.setBh(serialNumber);

            ResourceCheckIn resourceCheckIn = new ResourceCheckIn(LocalDateTime.now(), userContext.getCurrentUser());
            t.setResourceCheckIn(resourceCheckIn);

            this.repository.save(t);
            return t;
        } else {
            return null;
        }
    }

    abstract  public T add(T t);


    @PreAuthorize("hasPermission(#t, 'write')")
    public void update(T t){
        if(t.getId() > 0) {
            this.repository.save(t);
        }
    }


    @PreAuthorize("hasPermission(#t, 'delete')")
    public void delete(T t){
        this.repository.deleteById(t.getId());
    }

    public void tagReaded(long resourceId, String userId){
        this.repository.findById(resourceId).ifPresent(resource -> {
            ResourceReadInfo readInfo = new ResourceReadInfo();
            readInfo.setReadAt(LocalDateTime.now());
            userRepository.findById(userId).ifPresent(readInfo::setUser);
            resource.getReadInfoList().add(readInfo);
            this.repository.save(resource);
        });
    }

    abstract public void getFieldAuditWord(long resourceId, long fieldAuditId, OutputStream output);

}
