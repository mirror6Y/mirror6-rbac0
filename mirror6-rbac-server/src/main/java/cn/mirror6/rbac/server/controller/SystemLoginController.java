package cn.mirror6.rbac.server.controller;

import cn.mirror6.rbac.center.pojo.login.LoginRequire;
import cn.mirror6.rbac.response.ResponseFactory;
import cn.mirror6.rbac.response.Result;
import cn.mirror6.rbac.server.component.SystemLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: mirror6
 * @create: 2021-04-24 14:50
 **/
@RestController
@RequestMapping("/api/login")
public class SystemLoginController {

    @Autowired
    private SystemLoginService loginServer;

    /**
     * 登录方法
     *
     * @param loginRequire 登录信息
     * @return 结果
     */
    @PostMapping
    public Result login(@RequestBody LoginRequire loginRequire) {
        String login = loginServer.login(loginRequire);
        return ResponseFactory.build(login);
    }
}
