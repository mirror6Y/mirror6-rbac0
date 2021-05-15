package cn.mirror6.rbac.center.impl.api;


import cn.mirror6.rbac.center.api.ISystemMenuServiceApi;
import cn.mirror6.rbac.center.pojo.dto.SystemMenuDto;
import cn.mirror6.rbac.center.pojo.entity.SystemMenu;
import cn.mirror6.rbac.center.pojo.query.SystemMenuQuery;
import cn.mirror6.rbac.center.pojo.vo.SystemMenuVo;
import cn.mirror6.rbac.center.service.ISystemAuthorityService;
import cn.mirror6.rbac.center.service.ISystemMenuService;
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
public class SystemMenuServiceApiImpl implements ISystemMenuServiceApi {

    @Autowired
    ISystemMenuService systemMenuService;

    @Autowired
    ISystemAuthorityService systemAuthorityService;

    @Override
    public Result createSystemMenu(SystemMenuDto systemMenuDto) {
        SystemMenu menu = new SystemMenu();
        BeanUtils.copyProperties(systemMenuDto, menu);
        return ResponseFactory.build(systemMenuService.save(menu));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result deleteBatchSystemMenu(Long[] ids) {
        List<Long> list = Arrays.asList(ids);
        if (CollectionUtils.isEmpty(list)) {
            return ResponseFactory.build(ResponseConstant.COLLECTION_IS_EMPTY_CODE, ResponseConstant.COLLECTION_IS_EMPTY_MSG);
        }
        boolean authBool = systemAuthorityService.removeByIds(list);
        boolean menuBool = systemMenuService.removeByIds(list);
        return ResponseFactory.build(authBool && menuBool);
    }

    @Override
    public Result editSystemMenu(SystemMenuDto systemMenuDto) {
        SystemMenu menu = new SystemMenu();
        BeanUtils.copyProperties(systemMenuDto, menu);
        return ResponseFactory.build(systemMenuService.updateById(menu));
    }

    @Override
    public Result pageSystemMenu(SystemMenuQuery systemMenuQuery) {
        QueryWrapper<SystemMenu> wrapper = new QueryWrapper<>();
        if (Objects.nonNull(systemMenuQuery.getTitle())) {
            wrapper.like("title", systemMenuQuery.getTitle());
        }
        if (Objects.nonNull(systemMenuQuery.getDescription())) {
            wrapper.like("description", systemMenuQuery.getDescription());
        }
        if (Objects.nonNull(systemMenuQuery.getEnabled())) {
            wrapper.eq("is_enabled", systemMenuQuery.getEnabled());
        }
        Page<SystemMenu> page = new Page<>(Optional.ofNullable(systemMenuQuery.getPageNum()).orElse(Constant.INIT_PAGE_NUM),
                Optional.ofNullable(systemMenuQuery.getPageSize()).orElse(Constant.INIT_PAGE_SIZE));
        return ResponseFactory.build(systemMenuService.page(page, wrapper));
    }

    @Override
    public Result treeSystemMenu() {
        List<SystemMenu> list = systemMenuService.list();
        List<SystemMenuVo> voList = new ArrayList<>();
        List<SystemMenuVo> rootList = new ArrayList<>();
        list.forEach(systemMenu -> {
            SystemMenuVo systemMenuVo = new SystemMenuVo();
            BeanUtils.copyProperties(systemMenu, systemMenuVo);
            voList.add(systemMenuVo);
            if (Objects.equals(systemMenuVo.getParentId(), Constant.TREE_ROOT)) {
                rootList.add(systemMenuVo);
            }
        });
        //第二级开始
        rootList.forEach(systemMenuVo -> systemMenuVo.setChildList(getChild(voList, systemMenuVo.getId())));
        return ResponseFactory.build(rootList);
    }

    private List<SystemMenuVo> getChild(List<SystemMenuVo> list, Long pid) {
        List<SystemMenuVo> childList = new ArrayList<>();

        list.forEach(systemMenuVo -> {
            if (Objects.equals(systemMenuVo.getParentId(), pid)) {
                childList.add(systemMenuVo);
            }
        });

        childList.forEach(systemMenuVo -> systemMenuVo.setChildList(getChild(list, systemMenuVo.getId())));
        return childList;
    }
}
