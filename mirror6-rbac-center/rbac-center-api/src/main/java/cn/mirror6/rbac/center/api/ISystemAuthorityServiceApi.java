package cn.mirror6.rbac.center.api;

import cn.mirror6.rbac.center.pojo.dto.SystemAuthorityDto;
import cn.mirror6.rbac.center.pojo.query.SystemAuthorityQuery;
import cn.mirror6.rbac.response.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mirror6
 * @since 2021-03-21
 */
public interface ISystemAuthorityServiceApi {

    /**
     * 添加
     *
     * @param systemAuthorityDto dto
     * @return res
     */
    Result createSystemAuthority(SystemAuthorityDto systemAuthorityDto);

    /**
     * 删除
     *
     * @param ids 主键
     * @return res
     */
    Result deleteBatchSystemAuthority(Long[] ids);

    /**
     * 编辑
     *
     * @param systemAuthorityDto dto
     * @return res
     */
    Result editSystemAuthority(SystemAuthorityDto systemAuthorityDto);

    /**
     * 列表
     *
     * @param systemAuthorityQuery query
     * @return res
     */
    Result pageSystemAuthority(SystemAuthorityQuery systemAuthorityQuery);

    /**
     * 菜单树
     *
     * @return res
     */
    Result treeSystemAuthority();


    /**
     * 根据user.id获取对应权限的标识
     *
     * @param userId user pk
     * @return str
     */
    List<String> getAuthorityMarkByUserId(Long userId);
}
