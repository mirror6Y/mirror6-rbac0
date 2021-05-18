package cn.mirror6.rbac.server.config;

import cn.mirror6.rbac.server.security.filter.JwtAuthorizationFilter;
import cn.mirror6.rbac.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @description: SpringSecurity配置
 * @author: mirror6
 * @create: 2021-04-17 21:20
 **/
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private RedisUtil redisUtil;

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;


    /**
     * 解决 无法直接注入 AuthenticationManager
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // CSRF禁用，因为不使用session
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/login", "/captchaImage").anonymous()
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                //自定义filter
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), redisUtil));
//        // 定制请求的授权规则
//        http.authorizeRequests()
//                // 匹配许可，首页所有人可以访问
//                .antMatchers("/", "/index").permitAll()
//                // 匹配访问规则，需要vip1/2/3权限
//                .antMatchers("/level1/**").hasRole("vip1")
//                .antMatchers("/level2/**").hasRole("vip2")
//                .antMatchers("/level3/**").hasRole("vip3");
//
//        // 开启自动配置的登录功能：如果没有权限，就会跳转到登录页面！
//        http.formLogin() // 定制登录页 ↓↓↓
//                .loginPage("/toLogin") //登录页
//                .usernameParameter("username") //input提交的参数名username
//                .passwordParameter("password") //input提交的参数名password
//                .loginProcessingUrl("/user/login"); //登陆表单提交action请求
    }

    /**
     * @param auth 验证
     * @throws Exception 异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("1"));
    }

}
