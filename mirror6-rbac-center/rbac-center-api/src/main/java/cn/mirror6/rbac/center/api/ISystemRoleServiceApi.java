package cn.mirror6.rbac.center.api;

import cn.mirror6.rbac.center.pojo.dto.SystemRoleDto;
import cn.mirror6.rbac.center.pojo.query.SystemRoleQuery;
import cn.mirror6.rbac.response.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mirror6
 * @since 2021-03-21
 */
public interface ISystemRoleServiceApi {

    /**
     * 添加
     *
     * @param systemRoleDto dto
     * @return res
     */
    Result createSystemRole(SystemRoleDto systemRoleDto);

    /**
     * 删除
     *
     * @param ids 主键
     * @return res
     */
    Result deleteBatchSystemRole(Long[] ids);

    /**
     * 编辑
     *
     * @param systemRoleDto dto
     * @return res
     */
    Result editSystemRole(SystemRoleDto systemRoleDto);

    /**
     * 列表
     *
     * @param systemRoleQuery query
     * @return res
     */
    Result pageSystemRole(SystemRoleQuery systemRoleQuery);

    /**
     * 菜单树
     *
     * @return res
     */
    Result treeSystemRole();

}
