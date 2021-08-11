package pw.ewen.WLPT.services.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.resources.BaseResource;
import pw.ewen.WLPT.domains.entities.resources.ResourceCheckIn;
import pw.ewen.WLPT.domains.entities.resources.ResourceFinish;
import pw.ewen.WLPT.repositories.resources.ResourceCheckInRepository;
import pw.ewen.WLPT.security.UserContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * created by wenliang on 2021-05-25
 */
@Service
public class ResourceCheckInService {

    private final ResourceCheckInRepository resourceCheckInRepository;
    private final UserContext userContext;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public ResourceCheckInService(ResourceCheckInRepository resourceCheckInRepository,
                                  UserContext userContext) {
        this.resourceCheckInRepository = resourceCheckInRepository;
        this.userContext = userContext;
    }

    public  ResourceCheckIn findOne(UUID id) {
        return this.resourceCheckInRepository.findOne(id);
    }

//    public ResourceCheckIn save() {
//        ResourceCheckIn resourceCheckIn = new ResourceCheckIn(LocalDateTime.now(), userContext.getCurrentUser());
//        return this.resourceCheckInRepository.save(resourceCheckIn);
//    }


}
