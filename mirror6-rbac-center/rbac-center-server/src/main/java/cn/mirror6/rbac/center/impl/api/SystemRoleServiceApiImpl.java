package cn.mirror6.rbac.center.impl.api;

import cn.mirror6.rbac.center.api.ISystemRoleServiceApi;
import cn.mirror6.rbac.center.pojo.dto.SystemRoleDto;
import cn.mirror6.rbac.center.pojo.entity.SystemRole;
import cn.mirror6.rbac.center.pojo.entity.SystemRoleAuthority;
import cn.mirror6.rbac.center.pojo.entity.SystemUserRole;
import cn.mirror6.rbac.center.pojo.query.SystemRoleQuery;
import cn.mirror6.rbac.center.pojo.vo.SystemRoleVo;
import cn.mirror6.rbac.center.service.ISystemRoleAuthorityService;
import cn.mirror6.rbac.center.service.ISystemRoleService;
import cn.mirror6.rbac.center.service.ISystemUserRoleService;
import cn.mirror6.rbac.constant.Constant;
import cn.mirror6.rbac.constant.ResponseConstant;
import cn.mirror6.rbac.response.ResponseFactory;
import cn.mirror6.rbac.response.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dubbo.common.utils.ArrayUtils;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SystemRoleServiceApiImpl implements ISystemRoleServiceApi {

    @Autowired
    private ISystemRoleService roleService;

    @Autowired
    private ISystemRoleAuthorityService roleAuthorityService;

    @Autowired
    private ISystemUserRoleService userRoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result createSystemRole(SystemRoleDto systemRoleDto) {
        SystemRole role = new SystemRole();
        BeanUtils.copyProperties(systemRoleDto, role);
        roleService.save(role);
        List<SystemRoleAuthority> list = buildSystemRoleAuthority(systemRoleDto.getAuthIds(), role.getId());
        return ResponseFactory.build(roleAuthorityService.saveBatch(list));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result deleteBatchSystemRole(Long[] ids) {
        List<Long> list = Arrays.asList(ids);
        if (CollectionUtils.isEmpty(list)) {
            return ResponseFactory.build(ResponseConstant.COLLECTION_IS_EMPTY_CODE, ResponseConstant.COLLECTION_IS_EMPTY_MSG);
        }
        QueryWrapper<SystemUserRole> wrapper = new QueryWrapper<>();
        wrapper.in("role_id", list);
        boolean bool = userRoleService.remove(wrapper);
        boolean roleBool = roleService.removeByIds(Arrays.asList(ids));
        return ResponseFactory.build(bool && roleBool);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result editSystemRole(SystemRoleDto systemRoleDto) {
        Long[] authIds = systemRoleDto.getAuthIds();
        if (ArrayUtils.isNotEmpty(authIds)) {
            QueryWrapper<SystemRoleAuthority> wrapper = new QueryWrapper<>();
            wrapper.eq("role_id", systemRoleDto.getId());
            roleAuthorityService.remove(wrapper);
        }
        SystemRole role = new SystemRole();
        BeanUtils.copyProperties(systemRoleDto, role);
        return ResponseFactory.build(roleService.updateById(role));
    }

    @Override
    public Result pageSystemRole(SystemRoleQuery systemRoleQuery) {
        QueryWrapper<SystemRole> wrapper = new QueryWrapper<>();
        if (Objects.nonNull(systemRoleQuery.getName())) {
            wrapper.like("name", systemRoleQuery.getName());
        }
        if (Objects.nonNull(systemRoleQuery.getDescription())) {
            wrapper.like("description", systemRoleQuery.getDescription());
        }
        if (Objects.nonNull(systemRoleQuery.getIsEnabled())) {
            wrapper.eq("is_enabled", systemRoleQuery.getIsEnabled());
        }
        Page<SystemRole> page = new Page<>(Optional.ofNullable(systemRoleQuery.getPageNum()).orElse(Constant.INIT_PAGE_NUM),
                Optional.ofNullable(systemRoleQuery.getPageSize()).orElse(Constant.INIT_PAGE_SIZE));
        return ResponseFactory.build(roleService.page(page, wrapper));
    }

    @Override
    public Result treeSystemRole() {
        List<SystemRole> list = roleService.list();
        if (CollectionUtils.isEmpty(list)) {
            ResponseFactory.build(new ArrayList<>());
        }
        List<SystemRoleVo> voList = new ArrayList<>();
        list.forEach(systemRole -> {
            SystemRoleVo roleVo = new SystemRoleVo();
            BeanUtils.copyProperties(systemRole, roleVo);
            voList.add(roleVo);
        });
        return ResponseFactory.build(getChild(voList, list.get(0).getParentId()));
    }

    private List<SystemRoleVo> getChild(List<SystemRoleVo> list, Long pid) {
        List<SystemRoleVo> res = new ArrayList<>();
        for (SystemRoleVo role : list) {
            if (Objects.equals(pid, role.getParentId())) {
                role.setChildList(getChild(list, role.getId()));
                res.add(role);
            }
        }
        return res;
    }

    private List<SystemRoleAuthority> buildSystemRoleAuthority(Long[] authIds, Long roleId) {
        if (ArrayUtils.isEmpty(authIds)) {
            ResponseFactory.build(ResponseConstant.ARRAY_IS_EMPTY_CODE, ResponseConstant.ARRAY_IS_EMPTY_MSG);
        }
        List<SystemRoleAuthority> list = new ArrayList<>();
        for (Long authId : authIds) {
            SystemRoleAuthority systemRoleAuthority = SystemRoleAuthority.builder().roleId(roleId).authId(authId).build();
            list.add(systemRoleAuthority);
        }
        return list;
    }

}
