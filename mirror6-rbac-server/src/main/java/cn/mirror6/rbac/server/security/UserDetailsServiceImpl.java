//package cn.mirror6.rbac.server.security;
//
//import cn.mirror6.rbac.center.api.ISystemAuthorityServiceApi;
//import cn.mirror6.rbac.center.api.ISystemUserServiceApi;
//import cn.mirror6.rbac.center.pojo.entity.SystemUser;
//import cn.mirror6.rbac.constant.UserConstant;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.dubbo.config.annotation.DubboReference;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Objects;
//
///**
// * 自定义用户验证
// *
// * @author 17666
// */
//@Slf4j
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @DubboReference
//    private ISystemUserServiceApi userService;
//
//    @DubboReference
//    private ISystemAuthorityServiceApi authorityServiceApi;
//
//    @Override
//    public UserDetails loadUserByUsername(String account) {
//        SystemUser user = userService.exitSystemUser(account);
//        if (Objects.isNull(user)) {
//            log.info("用户：{} 不存在.", account);
//            throw new UsernameNotFoundException("用户：" + account + " 不存在");
//        } else if (UserConstant.DISABLED.equals(user.getEnabled())) {
//            log.info("用户：{} 已被停用.", account);
//            throw new UsernameNotFoundException("对不起，您的账号：" + account + " 已停用");
//        } else if (UserConstant.DELETED.equals(user.getDeleted())) {
//            log.info("用户：{} 已删除.", account);
//            throw new UsernameNotFoundException("对不起，您的账号：" + account + " 已删除");
//        }
//        return createLoginUser(user);
//    }
//
//    private UserDetails createLoginUser(SystemUser user) {
//        return new LoginUser(user, authorityServiceApi.getAuthorityMarkByUserId(user.getId()));
////        return new LoginUser(user);
//    }
//}
