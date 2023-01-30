package cn.mirror6.rbac.center.api;

import cn.mirror6.rbac.center.pojo.dto.SystemUserDto;
import cn.mirror6.rbac.center.pojo.entity.SystemUser;
import cn.mirror6.rbac.center.pojo.query.SystemUserQuery;
import cn.mirror6.rbac.response.Result;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author mirror6
 * @since 2021-03-21
 */
public interface ISystemUserServiceApi {

    /**
     * 添加
     *
     * @param systemUserDto dto
     * @return res
     */
    Result createSystemUser(SystemUserDto systemUserDto);

    /**
     * 删除
     *
     * @param ids 主键
     * @return res
     */
    Result deleteBatchSystemUser(Long[] ids);

    /**
     * 编辑
     *
     * @param systemUserDto dto
     * @return res
     */
    Result editSystemUser(SystemUserDto systemUserDto);

    /**
     * 编辑状态
     *
     * @param id id
     * @return res
     */
    Result editSystemUserStatus(Long id);

    /**
     * 列表
     *
     * @param systemUserQuery query
     * @return res
     */
    Result pageSystemUser(SystemUserQuery systemUserQuery);

    /**
     * 查询账号是否存在
     *
     * @param account 账号
     * @return res
     */
    SystemUser exitSystemUser(String account);

    SystemUser exitSystemUser(String attribute, String param);

}
