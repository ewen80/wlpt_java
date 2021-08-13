package pw.ewen.WLPT.services.resources.weixing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.controllers.utils.MyPage;
import pw.ewen.WLPT.domains.entities.resources.ResourceCheckIn;
import pw.ewen.WLPT.domains.entities.resources.weixing.WeixingResource;
import pw.ewen.WLPT.repositories.resources.weixing.WeixingResourceRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.SerialNumberService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * created by wenliang on 2021-07-21
 */
@Service
public class WeixingResourceService {

    private final WeixingResourceRepository weixingResourceRepository;
    private final SerialNumberService serialNumberService;
    private final UserContext userContext;
    private final BizConfig bizConfig;

    @Autowired
    public WeixingResourceService(WeixingResourceRepository weixingResourceRepository, SerialNumberService serialNumberService, UserContext userContext, BizConfig bizConfig) {
        this.weixingResourceRepository = weixingResourceRepository;
        this.serialNumberService = serialNumberService;
        this.userContext = userContext;
        this.bizConfig = bizConfig;
    }

    @PostFilter("hasPermission(filterObject, 'read')")
    public List<WeixingResource> findAll() {
        return this.weixingResourceRepository.findAll();
    }

    @PostFilter("hasPermission(filterObject, 'read')")
    public List<WeixingResource> findAll(String filter) {
        SearchSpecificationsBuilder<WeixingResource> builder = new SearchSpecificationsBuilder<>();
        return this.weixingResourceRepository.findAll(builder.build(filter));
    }

    @PostAuthorize("hasPermission(returnObject.get(), 'read')")
    public Optional<WeixingResource> findOne(long id) {
        return this.weixingResourceRepository.findById(id);
    }

    @PreAuthorize("hasPermission(#weixingResource, 'write')")
    public WeixingResource save(WeixingResource weixingResource) {
        boolean isAdd = weixingResource.getId() == 0;
        // 判断是否是新增，如果是新增生成一条新的ResourceCheckIn记录
        if(isAdd) {
            // 生成编号
            String serialNumber = serialNumberService.generate(bizConfig.getSerialNumber().getWeixingName(), bizConfig.getSerialNumber().getWeixingBasis());
            weixingResource.setBh(serialNumber);

            ResourceCheckIn resourceCheckIn = new ResourceCheckIn(LocalDateTime.now(), userContext.getCurrentUser());
            weixingResource.setResourceCheckIn(resourceCheckIn);
        }
        this.weixingResourceRepository.save(weixingResource);
        return weixingResource;
    }

//    public void delete(long id) {
//        WeixingResource weixingResource = this.findOne(id);
//        this.delete(weixingResource);
//    }

    @PreAuthorize("hasPermission(#weixingResource, 'write')")
    public void delete(WeixingResource weixingResource) {
        this.weixingResourceRepository.deleteById(weixingResource.getId());
    }
}
