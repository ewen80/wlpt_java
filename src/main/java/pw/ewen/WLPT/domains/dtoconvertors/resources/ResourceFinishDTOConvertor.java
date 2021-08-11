package pw.ewen.WLPT.domains.dtoconvertors.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.DTOs.resources.ResourceFinishDTO;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.domains.entities.resources.ResourceFinish;
import pw.ewen.WLPT.services.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * created by wenliang on 2021-05-25
 */
@Component
public class ResourceFinishDTOConvertor {

    private final UserService userService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public ResourceFinishDTOConvertor(UserService userService) {
        this.userService = userService;
    }

    public ResourceFinish toResourceFinish(ResourceFinishDTO dto) {
        String userId = dto.getFinishUserId();
        User user = userService.findOne(userId);
        LocalDateTime finishedDateTime = LocalDateTime.parse(dto.getFinishDateTime(), formatter);
        ResourceFinish resourceFinish = new ResourceFinish(finishedDateTime, user, dto.isFinished());
        resourceFinish.setId(dto.getId());
        return resourceFinish;
    }

    public ResourceFinishDTO toDTO(ResourceFinish resourceFinish) {
        if( resourceFinish == null) return null;
        return new ResourceFinishDTO(resourceFinish.getId(), resourceFinish.getFinishDateTime().format(formatter), resourceFinish.getFinishUser().getId(), resourceFinish.isFinished() );

    }
}
