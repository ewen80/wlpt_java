package pw.ewen.WLPT.controllers;

import org.springframework.web.bind.annotation.*;

/**
 * 用户认证
 */
@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationApi {
    /**
     * 客户端刷新服务器认证接口
     */
    @RequestMapping(method = RequestMethod.PUT, value = "refresh", produces = "application/json")
    public boolean refresh() {
        return true;
    }

}
