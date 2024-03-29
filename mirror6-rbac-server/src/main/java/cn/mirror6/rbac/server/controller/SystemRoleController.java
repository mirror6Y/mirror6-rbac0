package cn.mirror6.rbac.server.controller;


import cn.mirror6.rbac.center.api.ISystemRoleServiceApi;
import cn.mirror6.rbac.center.pojo.dto.SystemRoleDto;
import cn.mirror6.rbac.center.pojo.query.SystemRoleQuery;
import cn.mirror6.rbac.response.Result;
import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.DubboReference;
//import org.springframework.security.access.prepost.PreAuthorize;
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
@RestController
@RequestMapping("/api/rbac/systemRole")
public class SystemRoleController {

    @DubboReference
    ISystemRoleServiceApi roleServiceApi;

    @PostMapping
//    @PreAuthorize("hasAnyAuthority('sys','sys:role','sys:role:operate')")
    public Result addRole(@RequestBody SystemRoleDto dto) {
        return roleServiceApi.createSystemRole(dto);
    }

    @DeleteMapping
//    @PreAuthorize("hasAnyAuthority('sys','sys:role','sys:role:operate')")
    public Result deleteRole(@RequestBody String msg) {
        List<Long> ids = JSON.parseArray(msg, Long.class);
        return roleServiceApi.deleteBatchSystemRole(ids.toArray(new Long[0]));
    }

    @PutMapping
//    @PreAuthorize("hasAnyAuthority('sys','sys:role','sys:role:operate')")
    public Result updateRole(@RequestBody SystemRoleDto dto) {
        return roleServiceApi.editSystemRole(dto);
    }

    @GetMapping
//    @PreAuthorize("hasAnyAuthority('sys','sys:role','sys:role:view')")
    public Result pageRole(SystemRoleQuery query) {
        return roleServiceApi.pageSystemRole(query);
    }

    @GetMapping("getTree")
    public Result treeRole() {
        return roleServiceApi.treeSystemRole();
    }

}

