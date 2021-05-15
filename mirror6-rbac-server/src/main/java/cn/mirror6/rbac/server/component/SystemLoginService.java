package cn.mirror6.rbac.server.component;

import cn.mirror6.rbac.server.security.LoginUser;
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


    public String login(String username, String password, String code, String uuid) {
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
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
        return loginUser.toString();
    }
}
