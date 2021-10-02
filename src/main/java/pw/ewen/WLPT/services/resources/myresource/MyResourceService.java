package pw.ewen.WLPT.services.resources.myresource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.resources.ResourceCheckIn;
import pw.ewen.WLPT.domains.entities.resources.ResourceFinish;
import pw.ewen.WLPT.domains.entities.resources.myresource.MyResource;
import pw.ewen.WLPT.repositories.resources.myresource.MyResourceRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;
import pw.ewen.WLPT.security.UserContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * created by wenliang on 2021/5/1
 */
@Service
public class MyResourceService {

    private final MyResourceRepository myResourceRepository;
    private final UserContext userContext;

    @Autowired
    public MyResourceService(MyResourceRepository myResourceRepository,
                             UserContext userContext) {
        this.myResourceRepository = myResourceRepository;
        this.userContext = userContext;
    }

    @PostFilter("hasPermission(filterObject, 'read')")
    public List<MyResource> findAll() {
        return this.myResourceRepository.findAll();
    }

    @PostFilter("hasPermission(filterObject, 'read')")
    public List<MyResource> findAll(String filter) {
        SearchSpecificationsBuilder<MyResource> builder = new SearchSpecificationsBuilder<>();
        return this.myResourceRepository.findAll(builder.build(filter));
    }

    @PostAuthorize("hasPermission(returnObject.get(), 'read')")
    public Optional<MyResource> findOne(long id) {
        return this.myResourceRepository.findById(id);
    }

    @PreAuthorize("hasPermission(#myResource, 'create')")
    public MyResource add(MyResource myResource) {
        if(myResource.getId() == 0) {
            // 生成一条新的ResourceCheckIn记录
            ResourceCheckIn resourceCheckIn = new ResourceCheckIn(LocalDateTime.now(), userContext.getCurrentUser());
            myResource.setResourceCheckIn(resourceCheckIn);

            this.myResourceRepository.save(myResource);
            return myResource;
        } else {
            return null;
        }
    }

    @PreAuthorize("hasPermission(#myResource, 'write')")
    public void update(MyResource myResource) {
        if(myResource.getId() > 0) {
            this.myResourceRepository.save(myResource);
        }
    }

    // 为了做权限控制单独将单个删除做成一个方法
    @PreAuthorize("hasPermission(#myResource, 'delete')")
    public void delete(MyResource myResource) {
        this.myResourceRepository.deleteById(myResource.getId());
    }

    @PreAuthorize("hasPermission(#myResource, 'finish')")
    public void finish(MyResource myResource) {
        ResourceFinish finish = new ResourceFinish(LocalDateTime.now(), userContext.getCurrentUser(), true);
        ResourceCheckIn resourceCheckIn = myResource.getResourceCheckIn();
        resourceCheckIn.setFinished(true);
        resourceCheckIn.setResourceFinish(finish);
        myResourceRepository.save(myResource);
    }
}
