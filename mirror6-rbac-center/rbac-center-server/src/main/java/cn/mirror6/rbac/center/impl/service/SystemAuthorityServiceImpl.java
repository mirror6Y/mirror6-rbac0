package cn.mirror6.rbac.center.impl.service;

import cn.mirror6.rbac.center.mapper.SystemAuthorityMapper;
import cn.mirror6.rbac.center.pojo.entity.SystemAuthority;
import cn.mirror6.rbac.center.service.ISystemAuthorityService;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mirror6
 * @since 2021-03-21
 */
@Service
public class SystemAuthorityServiceImpl extends ServiceImpl<SystemAuthorityMapper, SystemAuthority> implements ISystemAuthorityService {

    @Resource
    SystemAuthorityMapper systemAuthorityMapper;

    @Override
    public List<String> getAuthorityMarkByUserId(Long userId) {
        return systemAuthorityMapper.getAuthorityMarkByUserId(userId);
    }
}
