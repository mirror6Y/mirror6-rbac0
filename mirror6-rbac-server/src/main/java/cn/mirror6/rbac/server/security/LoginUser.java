package cn.mirror6.rbac.server.security;

import cn.mirror6.rbac.center.pojo.entity.SystemUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author 17666
 */
@Data
public class LoginUser implements UserDetails {

    /**
     * 用户信息
     */
    private SystemUser user;

    /**
     * 权限列表
     */
    private List<String> authList;

    public LoginUser(SystemUser user) {
        this.user = user;
    }

    public LoginUser(SystemUser user, List<String> authList) {
        this.user = user;
        this.authList = authList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    /**
     * 是否可用 ,禁用的用户不能身份验证
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
