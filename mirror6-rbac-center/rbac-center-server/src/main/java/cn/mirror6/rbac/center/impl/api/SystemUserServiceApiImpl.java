package cn.mirror6.rbac.center.impl.api;


import cn.mirror6.rbac.center.api.ISystemUserServiceApi;
import cn.mirror6.rbac.center.pojo.dto.SystemUserDto;
import cn.mirror6.rbac.center.pojo.entity.SystemUser;
import cn.mirror6.rbac.center.pojo.entity.SystemUserRole;
import cn.mirror6.rbac.center.pojo.query.SystemUserQuery;
import cn.mirror6.rbac.center.pojo.vo.SystemUserVO;
import cn.mirror6.rbac.center.service.ISystemUserRoleService;
import cn.mirror6.rbac.center.service.ISystemUserService;
import cn.mirror6.rbac.constant.Constant;
import cn.mirror6.rbac.constant.ResponseConstant;
import cn.mirror6.rbac.constant.UserConstant;
import cn.mirror6.rbac.exception.CommonErrorCode;
import cn.mirror6.rbac.exception.CommonException;
import cn.mirror6.rbac.response.ResponseFactory;
import cn.mirror6.rbac.response.Result;
//import cn.mirror6.rbac.util.SimplePageInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.utils.ArrayUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.zookeeper.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    @Resource
    private ISystemUserService userService;

    @Resource
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
            if (CollectionUtils.isNotEmpty(list)) {
                userRoleService.saveBatch(list);
            }
            return ResponseFactory.build(user.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result deleteBatchSystemUser(Long[] ids) {
        QueryWrapper<SystemUserRole> wrapper = new QueryWrapper<>();
        userRoleService.remove(wrapper.in("user_id", ids));
        return ResponseFactory.build(userService.removeByIds(Arrays.asList(ids)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result editSystemUser(SystemUserDto systemUserDto) {
        QueryWrapper<SystemUserRole> wrapper = new QueryWrapper<>();
        userRoleService.remove(wrapper.eq("user_id", systemUserDto.getId()));
        List<SystemUserRole> list = buildSystemUserRole(systemUserDto.getRoleIds(), systemUserDto.getId());
        if (CollectionUtils.isNotEmpty(list)) {
            userRoleService.saveBatch(list);
        }
        SystemUser user = new SystemUser();
        BeanUtils.copyProperties(systemUserDto, user);
        return ResponseFactory.build(userService.updateById(user));
    }

    @Override
    public Result editSystemUserStatus(Long id) {
        SystemUser systemUser = userService.getById(id);
        if (Objects.isNull(systemUser)) {
            return ResponseFactory.build("用户不存在");
        }
        SystemUser user = new SystemUser();
        user.setId(id);
        user.setEnabled(!systemUser.getEnabled());
        userService.updateById(user);
        return ResponseFactory.build(user);
    }

    @Override
    public Result pageSystemUser(SystemUserQuery query) {
        QueryWrapper<SystemUser> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(query.getAccount())) {
            wrapper.like("account", query.getAccount());
        }
        if (StringUtils.isNotBlank(query.getName())) {
            System.out.println("name不是空");
            wrapper.like("name", query.getName());
        }
        if (StringUtils.isNotBlank(query.getTel())) {
            wrapper.like("tel", query.getTel());
        }
        if (StringUtils.isNotBlank(query.getEmail())) {
            wrapper.like("email", query.getEmail());
        }
        if (Objects.nonNull(query.getEnabled())) {
            wrapper.eq("is_enabled", query.getEnabled());
        }
        if (Objects.nonNull(query.getStartTime()) && Objects.nonNull(query.getEndTime())) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sdf.format(date);
            wrapper.ge(query.getStartTime().toString(), format);
            wrapper.le(query.getEndTime().toString(), format);
        }
        Page<SystemUser> page = new Page<>(Optional.ofNullable(query.getPageNum()).orElse(Constant.INIT_PAGE_NUM),
                Optional.ofNullable(query.getPageSize()).orElse(Constant.INIT_PAGE_SIZE));
        List<SystemUser> userList = userService.page(page, wrapper).getRecords();
        List<Long> ids = userList.stream().map(SystemUser::getId).collect(Collectors.toList());
        QueryWrapper<SystemUserRole> relationWrapper = new QueryWrapper<>();
        List<SystemUserRole> userRoleList = userRoleService.list(relationWrapper.in("user_id", ids));

        List<SystemUserVO> voList = new ArrayList<>();
        userList.forEach(systemUser -> {
            SystemUserVO systemUserVO = new SystemUserVO();
            BeanUtils.copyProperties(systemUser, systemUserVO);
            //todo
            List<Long> roleIds = userRoleList.stream().filter(o -> o.getUserId().equals(systemUser.getId())).map(SystemUserRole::getRoleId).collect(Collectors.toList());
            systemUserVO.setRoleIds(roleIds.toArray(new Long[0]));
            voList.add(systemUserVO);
        });
//        return ResponseFactory.build(new SimplePageInfo<>(voList));
        return ResponseFactory.build(voList);
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
        List<SystemUserRole> list = new ArrayList<>();
        if (ArrayUtils.isEmpty(roleIds)) {
            throw new CommonException(CommonErrorCode.ARRAY_IS_EMPTY);
        }
        for (Long roleId : roleIds) {
            SystemUserRole systemUserRole = SystemUserRole.builder().roleId(roleId).userId(userId).build();
            list.add(systemUserRole);
        }
        return list;
    }
}
