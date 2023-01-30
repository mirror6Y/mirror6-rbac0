package cn.mirror6.rbac.center.impl.api;

import cn.mirror6.rbac.center.api.ISystemAuthorityServiceApi;
import cn.mirror6.rbac.center.pojo.dto.SystemAuthorityDto;
import cn.mirror6.rbac.center.pojo.entity.SystemAuthority;
import cn.mirror6.rbac.center.pojo.entity.SystemRoleAuthority;
import cn.mirror6.rbac.center.pojo.query.SystemAuthorityQuery;
import cn.mirror6.rbac.center.pojo.vo.SystemAuthorityVo;
import cn.mirror6.rbac.center.service.ISystemAuthorityService;
import cn.mirror6.rbac.center.service.ISystemRoleAuthorityService;
import cn.mirror6.rbac.constant.Constant;
import cn.mirror6.rbac.constant.ResponseConstant;
import cn.mirror6.rbac.response.ResponseFactory;
import cn.mirror6.rbac.response.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
public class SystemAuthorityServiceApiImpl implements ISystemAuthorityServiceApi {

    @Resource
    private ISystemAuthorityService authorityService;

    @Resource
    private ISystemRoleAuthorityService roleAuthorityService;


    @Override
    public Result createSystemAuthority(SystemAuthorityDto systemAuthorityDto) {
        SystemAuthority authority = new SystemAuthority();
        BeanUtils.copyProperties(systemAuthorityDto, authority);
        return ResponseFactory.build(authorityService.save(authority));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result deleteBatchSystemAuthority(Long[] ids) {
        List<Long> list = Arrays.asList(ids);
        if (CollectionUtils.isEmpty(list)) {
            return ResponseFactory.build(ResponseConstant.COLLECTION_IS_EMPTY_CODE, ResponseConstant.COLLECTION_IS_EMPTY_MSG);
        }
        QueryWrapper<SystemRoleAuthority> wrapper = new QueryWrapper<>();
        wrapper.in("auth_id", list);
        boolean bool = roleAuthorityService.remove(wrapper);
        boolean authBool = authorityService.removeByIds(list);
        return ResponseFactory.build(bool && authBool);
    }

    @Override
    public Result editSystemAuthority(SystemAuthorityDto systemAuthorityDto) {
        SystemAuthority authority = new SystemAuthority();
        BeanUtils.copyProperties(systemAuthorityDto, authority);
        return ResponseFactory.build(authorityService.updateById(authority));
    }

    @Override
    public Result pageSystemAuthority(SystemAuthorityQuery systemAuthorityQuery) {
        QueryWrapper<SystemAuthority> wrapper = new QueryWrapper<>();
        if (Objects.nonNull(systemAuthorityQuery.getName())) {
            wrapper.like("name", systemAuthorityQuery.getName());
        }
        if (Objects.nonNull(systemAuthorityQuery.getMark())) {
            wrapper.like("mark", systemAuthorityQuery.getMark());
        }
        if (Objects.nonNull(systemAuthorityQuery.getIsEnabled())) {
            wrapper.eq("is_enabled", systemAuthorityQuery.getIsEnabled());
        }
        Page<SystemAuthority> page = new Page<>(Optional.ofNullable(systemAuthorityQuery.getPageNum()).orElse(Constant.INIT_PAGE_NUM),
                Optional.ofNullable(systemAuthorityQuery.getPageSize()).orElse(Constant.INIT_PAGE_SIZE));
        return ResponseFactory.build(authorityService.page(page, wrapper));
    }

    @Override
    public Result treeSystemAuthority() {
        List<SystemAuthority> list = authorityService.list();
        List<SystemAuthorityVo> voList = new ArrayList<>();
        List<SystemAuthorityVo> rootList = new ArrayList<>();
        list.forEach(systemAuthor -> {
            SystemAuthorityVo systemAuthorityVo = new SystemAuthorityVo();
            BeanUtils.copyProperties(systemAuthor, systemAuthorityVo);
            voList.add(systemAuthorityVo);
            if (Objects.equals(systemAuthorityVo.getParentId(), Constant.TREE_ROOT)) {
                rootList.add(systemAuthorityVo);
            }
        });
        //第二级开始
        rootList.forEach(systemAuthorityVo -> systemAuthorityVo.setChildList(getChild(voList, systemAuthorityVo.getId())));
        return ResponseFactory.build(rootList);
    }

    @Override
    public List<String> getAuthorityMarkByUserId(Long userId) {
        return authorityService.getAuthorityMarkByUserId(userId);
    }

    private List<SystemAuthorityVo> getChild(List<SystemAuthorityVo> list, Long pid) {
        List<SystemAuthorityVo> childList = new ArrayList<>();

        list.forEach(systemAuthorityVo -> {
            if (Objects.equals(systemAuthorityVo.getParentId(), pid)) {
                childList.add(systemAuthorityVo);
            }
        });

        childList.forEach(systemAuthorityVo -> systemAuthorityVo.setChildList(getChild(list, systemAuthorityVo.getId())));
        return childList;
    }
}
