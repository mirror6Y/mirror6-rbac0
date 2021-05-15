package cn.mirror6.rbac.center.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.mirror6.rbac.center.pojo.entity.SystemAuthority;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mirror6
 * @since 2021-03-21
 */
public interface ISystemAuthorityService extends IService<SystemAuthority> {

    /**
     * 根据user.id获取对应权限的标识
     *
     * @param userId user pk
     * @return str
     */
    List<String> getAuthorityMarkByUserId(Long userId);

}
