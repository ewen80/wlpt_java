package pw.ewen.WLPT.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.domains.DTOs.resources.MenuDTO;
import pw.ewen.WLPT.domains.entities.utils.userunreaded.UserReaded;
import pw.ewen.WLPT.services.utils.UserReadedService;

import java.util.List;

/**
 * 相关统计信息
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final UserReadedService userReadedService;

    public DashboardController(UserReadedService userReadedService) {
        this.userReadedService = userReadedService;
    }

    @GetMapping(value = "/userreaded/{userId}")
    public List<UserReaded> getUserReaded(@PathVariable("userId") String userId){
        return userReadedService.findByUserId(userId);
    }

}
