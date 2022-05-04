package cn.mirror6.rbac.server.controller;


import cn.mirror6.rbac.center.api.ISystemMenuServiceApi;
import cn.mirror6.rbac.center.pojo.dto.SystemMenuDto;
import cn.mirror6.rbac.center.pojo.query.SystemMenuQuery;
import cn.mirror6.rbac.response.Result;
import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mirror6
 * @since 2021-03-21
 */
@RestController()
@RequestMapping("/api/rbac/systemMenu")
public class SystemMenuController {

    @DubboReference
    ISystemMenuServiceApi menuServiceApi;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('sys','sys:menu','sys:menu:operate')")
    public Result addMenu(@RequestBody SystemMenuDto dto) {
        return menuServiceApi.createSystemMenu(dto);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('sys','sys:menu','sys:menu:operate')")
    public Result deleteMenu(@RequestBody String msg) {
        List<Long> ids = JSON.parseArray(msg, Long.class);
        return menuServiceApi.deleteBatchSystemMenu(ids.toArray(new Long[0]));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('sys','sys:menu','sys:menu:operate')")
    public Result updateMenu(@RequestBody SystemMenuDto dto) {
        return menuServiceApi.editSystemMenu(dto);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('sys','sys:menu','sys:menu:view')")
    public Result pageMenu(SystemMenuQuery query) {
        return menuServiceApi.pageSystemMenu(query);
    }

    @GetMapping("getTree")
    public Result treeMenu() {
        return menuServiceApi.treeSystemMenu();
    }
}

