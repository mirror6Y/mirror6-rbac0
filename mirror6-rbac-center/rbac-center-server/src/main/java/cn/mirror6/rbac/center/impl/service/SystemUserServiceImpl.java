package cn.mirror6.rbac.center.impl.service;


import cn.mirror6.rbac.center.mapper.SystemUserMapper;
import cn.mirror6.rbac.center.pojo.entity.SystemUser;
import cn.mirror6.rbac.center.service.ISystemUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mirror6
 * @since 2021-03-21
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements ISystemUserService {

    @Override
    public Long addUser(SystemUser systemUser) {
        baseMapper.insert(systemUser);
        return systemUser.getId();
    }

    @Override
    public boolean removeUser() {
        return false;
    }

    @Override
    public boolean editUser() {
        return false;
    }

    @Override
    public SystemUser getUserById() {
        return null;
    }
}
