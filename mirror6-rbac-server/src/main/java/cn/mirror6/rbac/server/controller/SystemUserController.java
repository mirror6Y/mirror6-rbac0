package cn.mirror6.rbac.server.controller;


import cn.mirror6.rbac.center.api.ISystemUserServiceApi;
import cn.mirror6.rbac.center.pojo.dto.SystemUserDto;
import cn.mirror6.rbac.center.pojo.query.SystemUserQuery;
import cn.mirror6.rbac.response.Result;
import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
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
    public Result add(@RequestBody SystemUserDto dto) {
        return userServiceApi.createSystemUser(dto);
    }

    @DeleteMapping
    public Result delete(@RequestBody String msg) {
        List<Long> ids = JSON.parseArray(msg, Long.class);
        return userServiceApi.deleteBatchSystemUser(ids.toArray(new Long[0]));
    }

    @PutMapping
    public Result update(@RequestBody SystemUserDto dto) {
        return userServiceApi.editSystemUser(dto);
    }

    @GetMapping
    public Result page(SystemUserQuery query) {
        return userServiceApi.pageSystemUser(query);
    }
}

