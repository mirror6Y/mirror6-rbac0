package cn.mirror6.rbac.center.mapper;

import cn.mirror6.rbac.center.pojo.entity.SystemAuthority;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author mirror6
 * @since 2021-03-21
 */
public interface SystemAuthorityMapper extends BaseMapper<SystemAuthority> {

    /**
     * 根据user.id获取对应权限的标识
     *
     * @param userId user pk
     * @return str
     */
    List<String> getAuthorityMarkByUserId(Long userId);

}
