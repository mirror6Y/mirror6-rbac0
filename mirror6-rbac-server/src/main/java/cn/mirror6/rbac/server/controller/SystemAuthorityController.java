package cn.mirror6.rbac.server.controller;


import cn.mirror6.rbac.center.api.ISystemAuthorityServiceApi;
import cn.mirror6.rbac.center.pojo.dto.SystemAuthorityDto;
import cn.mirror6.rbac.center.pojo.query.SystemAuthorityQuery;
import cn.mirror6.rbac.response.Result;
import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.DubboReference;
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
@RequestMapping("/api/rbac/systemAuthority")
public class SystemAuthorityController {

    @DubboReference
    private ISystemAuthorityServiceApi authorityServiceApi;

    @PostMapping
    public Result addAuth(@RequestBody SystemAuthorityDto dto) {
        return authorityServiceApi.createSystemAuthority(dto);
    }

    @DeleteMapping
    public Result deleteAuth(@RequestBody String msg) {
        List<Long> ids = JSON.parseArray(msg, Long.class);
        return authorityServiceApi.deleteBatchSystemAuthority(ids.toArray(new Long[0]));
    }

    @PutMapping
    public Result updateAuth(@RequestBody SystemAuthorityDto dto) {
        return authorityServiceApi.editSystemAuthority(dto);
    }

    @GetMapping()
    public Result pageAuth(SystemAuthorityQuery query) {
        return authorityServiceApi.pageSystemAuthority(query);
    }

    @GetMapping("getTree")
    public Result treeAuth() {
        return authorityServiceApi.treeSystemAuthority();
    }

}

