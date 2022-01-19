package pw.ewen.WLPT.domains.dtoconvertors.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.DTOs.resources.ResourceCheckInDTO;
import pw.ewen.WLPT.domains.DTOs.resources.ResourceFinishDTO;
import pw.ewen.WLPT.domains.entities.resources.ResourceCheckIn;
import pw.ewen.WLPT.domains.entities.resources.ResourceFinish;
import pw.ewen.WLPT.services.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * created by wenliang on 2021-05-25
 */
@Component
public class ResourceCheckInDTOConvertor {
    private final UserService userService;
    private final ResourceFinishDTOConvertor resourceFinishDTOConvertor;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public ResourceCheckInDTOConvertor(UserService userService, ResourceFinishDTOConvertor resourceFinishDTOConvertor) {
        this.userService = userService;
        this.resourceFinishDTOConvertor = resourceFinishDTOConvertor;
    }

    public ResourceCheckIn toResourceCheckIn(ResourceCheckInDTO dto) {
        ResourceFinishDTO resourceFinishDTO = dto.getResourceFinish();
        ResourceFinish resourceFinish = null;
        if(resourceFinishDTO != null) {
            resourceFinish = resourceFinishDTOConvertor.toResourceFinish(dto.getResourceFinish());
        }

        ResourceCheckIn resourceCheckIn = new ResourceCheckIn(dto.isFinished(),
                                                                resourceFinish,
                                                                LocalDateTime.parse(dto.getCreatedDateTime(), formatter),
                                                                userService.findOne(dto.getCreatedUserId()).orElse(null));
        resourceCheckIn.setId(dto.getId());
        return resourceCheckIn;
    }

    public ResourceCheckInDTO toDTO(ResourceCheckIn resourceCheckIn) {
        ResourceFinish resourceFinish = resourceCheckIn.getResourceFinish();
        ResourceFinishDTO resourceFinishDTO = null;
        if(resourceFinish != null) {
            resourceFinishDTO = resourceFinishDTOConvertor.toDTO(resourceFinish);
        }


        return new ResourceCheckInDTO(resourceCheckIn.getId(),
                                    resourceCheckIn.getCreatedDateTime().format(formatter),
                                    resourceCheckIn.getCreatedUser().getId(),
                                    resourceCheckIn.isFinished(),
                                    resourceFinishDTO);
    }
}
