package cn.mirror6.rbac.server.controller;


import cn.mirror6.rbac.center.api.ISystemUserServiceApi;
import cn.mirror6.rbac.center.pojo.dto.SystemUserDto;
import cn.mirror6.rbac.center.pojo.query.SystemUserQuery;
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
@RestController
@RequestMapping("/api/rbac/systemUser")
public class SystemUserController {

    @DubboReference
    private ISystemUserServiceApi userServiceApi;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('sys','sys:user','sys:user:operate')")
    public Result add(@RequestBody SystemUserDto dto) {
        return userServiceApi.createSystemUser(dto);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('sys','sys:user','sys:user:operate')")
    public Result delete(@RequestBody String msg) {
        List<Long> ids = JSON.parseArray(msg, Long.class);
        return userServiceApi.deleteBatchSystemUser(ids.toArray(new Long[0]));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('sys','sys:user','sys:user:operate')")
    public Result update(@RequestBody SystemUserDto dto) {
        return userServiceApi.editSystemUser(dto);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('sys','sys:user','sys:user:view')")
    public Result page(SystemUserQuery query) {
        return userServiceApi.pageSystemUser(query);
    }
}

