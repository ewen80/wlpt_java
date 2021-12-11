package pw.ewen.WLPT.controllers;

import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.utils.UserReadedService;

/**
 * 用户认证
 */
@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationApi {

    /**
     * 客户端刷新服务器认证接口
     * 获取未读信息
     */
    @PutMapping(value = "/refresh")
    public boolean refresh() {

        return true;
    }

}
