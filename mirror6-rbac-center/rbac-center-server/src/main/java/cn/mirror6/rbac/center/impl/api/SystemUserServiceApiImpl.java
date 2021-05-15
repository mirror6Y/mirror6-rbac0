package cn.mirror6.rbac.center.impl.api;


import cn.mirror6.rbac.center.api.ISystemUserServiceApi;
import cn.mirror6.rbac.center.pojo.dto.SystemUserDto;
import cn.mirror6.rbac.center.pojo.entity.SystemUser;
import cn.mirror6.rbac.center.pojo.entity.SystemUserRole;
import cn.mirror6.rbac.center.pojo.query.SystemUserQuery;
import cn.mirror6.rbac.center.service.ISystemUserRoleService;
import cn.mirror6.rbac.center.service.ISystemUserService;
import cn.mirror6.rbac.constant.Constant;
import cn.mirror6.rbac.constant.ResponseConstant;
import cn.mirror6.rbac.constant.UserConstant;
import cn.mirror6.rbac.response.ResponseFactory;
import cn.mirror6.rbac.response.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dubbo.common.utils.ArrayUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.zookeeper.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mirror6
 * @since 2021-03-21
 */
@DubboService
public class SystemUserServiceApiImpl implements ISystemUserServiceApi {

    @Autowired
    private ISystemUserService userService;

    @Autowired
    private ISystemUserRoleService userRoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result createSystemUser(SystemUserDto systemUserDto) {
        SystemUser user = new SystemUser();
        if (null != exitSystemUser(UserConstant.ATTRIBUTE_ACCOUNT, systemUserDto.getAccount())) {
            return ResponseFactory.build(-1, "新增用户'" + systemUserDto.getName() + "'失败，账号已存在");
        } else if (null != systemUserDto.getTel() && null != exitSystemUser(UserConstant.ATTRIBUTE_TEL, systemUserDto.getTel())) {
            return ResponseFactory.build(-1, "新增用户'" + systemUserDto.getName() + "'失败，手机号码已存在");
        } else if (null != systemUserDto.getEmail() && null != exitSystemUser(UserConstant.ATTRIBUTE_EMAIL, systemUserDto.getEmail())) {
            return ResponseFactory.build(-1, "新增用户'" + systemUserDto.getName() + "'失败，邮箱已存在");
        } else {
            BeanUtils.copyProperties(systemUserDto, user);
            //盐和密码
            try {
                String password = new BCryptPasswordEncoder().encode(systemUserDto.getAccount());
//                String password = SecurityUtils.encryptPassword(user.getAccount());
                user.setSalt(password);
                //80位密文
                user.setPassword(password);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            user.setCreator(1L);
            userService.save(user);
            List<SystemUserRole> list = buildSystemUserRole(systemUserDto.getRoleIds(), user.getId());
            return ResponseFactory.build(list);
        }
    }

    @Override
    public Result deleteBatchSystemUser(Long[] ids) {
        return ResponseFactory.build(userService.removeByIds(Arrays.asList(ids)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result editSystemUser(SystemUserDto systemUserDto) {
        Long[] roleIds = systemUserDto.getRoleIds();
        if (ArrayUtils.isNotEmpty(roleIds)) {
            QueryWrapper<SystemUserRole> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", systemUserDto.getId());
            userRoleService.remove(wrapper);
        }
        SystemUser user = new SystemUser();
        BeanUtils.copyProperties(systemUserDto, user);
        return ResponseFactory.build(userService.updateById(user));
    }

    @Override
    public Result pageSystemUser(SystemUserQuery query) {
        QueryWrapper<SystemUser> wrapper = new QueryWrapper<>();
        if (Objects.nonNull(query.getAccount())) {
            wrapper.like("account", query.getAccount());
        }
        if (Objects.nonNull(query.getName())) {
            wrapper.like("name", query.getName());
        }
        if (Objects.nonNull(query.getTel())) {
            wrapper.like("tel", query.getTel());
        }
        if (Objects.nonNull(query.getEmail())) {
            wrapper.like("email", query.getEmail());
        }
        if (Objects.nonNull(query.getIsEnabled())) {
            wrapper.eq("is_enabled", query.getIsEnabled());
        }
        Page<SystemUser> page = new Page<>(Optional.ofNullable(query.getPageNum()).orElse(Constant.INIT_PAGE_NUM),
                Optional.ofNullable(query.getPageSize()).orElse(Constant.INIT_PAGE_SIZE));
        return ResponseFactory.build(userService.page(page, wrapper));
    }

    @Override
    public SystemUser exitSystemUser(String account) {
        QueryWrapper<SystemUser> wrapper = new QueryWrapper<>();
        if (Objects.nonNull(account)) {
            wrapper.like("account", account);
        }
        return userService.getOne(wrapper);
    }

    @Override
    public SystemUser exitSystemUser(String attribute, String param) {
        QueryWrapper<SystemUser> wrapper = new QueryWrapper<>();
        wrapper.select("id", "account", "name", "gender", "tel", "email", "is_enabled as enabled", "gmt_create");
        wrapper.eq(attribute, param);
        wrapper.eq("is_deleted", 0);
        return userService.getOne(wrapper);
    }


    private List<SystemUserRole> buildSystemUserRole(Long[] roleIds, Long userId) {
        if (ArrayUtils.isEmpty(roleIds)) {
            ResponseFactory.build(ResponseConstant.ARRAY_IS_EMPTY_CODE, ResponseConstant.ARRAY_IS_EMPTY_MSG);
        }
        List<SystemUserRole> list = new ArrayList<>();
        for (Long roleId : roleIds) {
            SystemUserRole systemUserRole = SystemUserRole.builder().roleId(roleId).userId(userId).build();
            list.add(systemUserRole);
        }
        return list;
    }
}
