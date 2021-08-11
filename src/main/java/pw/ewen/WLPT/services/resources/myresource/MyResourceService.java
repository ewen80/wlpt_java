package pw.ewen.WLPT.services.resources.myresource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.controllers.utils.MyPage;
import pw.ewen.WLPT.domains.DTOs.resources.myresource.MyResourceDTO;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.domains.entities.resources.ResourceCheckIn;
import pw.ewen.WLPT.domains.entities.resources.ResourceFinish;
import pw.ewen.WLPT.domains.entities.resources.myresource.MyResource;
import pw.ewen.WLPT.repositories.resources.ResourceCheckInRepository;
import pw.ewen.WLPT.repositories.resources.myresource.MyResourceRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.resources.ResourceCheckInService;

import java.time.LocalDateTime;
import java.util.List;

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

    @PostAuthorize("hasPermission(returnObject, 'read')")
    public MyResource findOne(long id) {
        return this.myResourceRepository.findOne(id);
    }

    @PreAuthorize("hasPermission(#myResource, 'write')")
    public MyResource save(MyResource myResource) {
        boolean isAdd = myResource.getId() == 0;

        if(isAdd) {
            // 生成一条新的ResourceCheckIn记录
            ResourceCheckIn resourceCheckIn = new ResourceCheckIn(LocalDateTime.now(), userContext.getCurrentUser());
            myResource.setResourceCheckIn(resourceCheckIn);
        }
        this.myResourceRepository.save(myResource);
        return myResource;
    }

    // 为了做权限控制单独将单个删除做成一个方法
    @PreAuthorize("hasPermission(#myResource, 'write')")
    public void delete(MyResource myResource) {
        this.myResourceRepository.delete(myResource.getId());
    }

//    public void delete(long id) {
//        MyResource myResource = this.findOne(id);
//        this.delete(myResource);
//    }


    @PreAuthorize("hasPermission(#myResource, 'finish')")
    public void finish(MyResource myResource) {
        ResourceFinish finish = new ResourceFinish(LocalDateTime.now(), userContext.getCurrentUser(), true);
        ResourceCheckIn resourceCheckIn = myResource.getResourceCheckIn();
        resourceCheckIn.setFinished(true);
        resourceCheckIn.setResourceFinish(finish);
        myResourceRepository.save(myResource);
    }
}
