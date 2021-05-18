package cn.mirror6.rbac.server.component;

import cn.mirror6.rbac.center.pojo.login.LoginRequire;
import cn.mirror6.rbac.server.security.LoginUser;
import cn.mirror6.rbac.server.util.JwtTokenUtil;
import cn.mirror6.rbac.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description:
 * @author: mirror6
 * @create: 2021-04-24 14:53
 **/
@Slf4j
@Component
public class SystemLoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisUtil redisUtil;


    public String login(LoginRequire loginRequire) {
        //验证码

        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequire.getUsername(), loginRequire.getPassword()));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                log.info("user.password.not.match");
            } else {
                log.info("authentication");
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        log.info("user.login.success");
        assert authentication != null;
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String token = JwtTokenUtil.createToken(loginUser, loginRequire.getRemember());
        boolean set = redisUtil.set("user_token_" + loginUser.getUser().getId(), token);
        System.out.println(set);
        return token;
    }
}
