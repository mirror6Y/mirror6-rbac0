package cn.mirror6.rbac.server.security.filter;

import cn.mirror6.rbac.constant.security.SecurityConstant;
import cn.mirror6.rbac.server.util.JwtTokenUtil;
import cn.mirror6.rbac.util.RedisUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: jwt过滤器
 * @author: mirror6
 * @create: 2021-05-18 20:37
 **/
@Slf4j
@Component
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    @Resource
    private RedisUtil redisUtil;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, RedisUtil redisUtil) {
        super(authenticationManager);
        this.redisUtil = redisUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String token = request.getHeader(SecurityConstant.TOKEN_HEADER);
        if (StringUtils.isBlank(token) || !token.startsWith(SecurityConstant.TOKEN_PREFIX)) {
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }
        String tokenValue = token.replace(SecurityConstant.TOKEN_PREFIX, "");
        UsernamePasswordAuthenticationToken authentication = null;
        try {
            String previousToken = redisUtil.get("user_token_" + JwtTokenUtil.getId(tokenValue)).toString();
            if (!token.equals(previousToken)) {
                SecurityContextHolder.clearContext();
                chain.doFilter(request, response);
                return;
            }
            authentication = JwtTokenUtil.getAuthentication(tokenValue);
        } catch (JwtException e) {
            logger.error("Invalid jwt : " + e.getMessage());
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}


