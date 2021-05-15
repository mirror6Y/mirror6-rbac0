package cn.mirror6.rbac.center.api;


import cn.mirror6.rbac.center.pojo.dto.SystemMenuDto;
import cn.mirror6.rbac.center.pojo.entity.SystemMenu;
import cn.mirror6.rbac.center.pojo.query.SystemMenuQuery;
import cn.mirror6.rbac.response.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author mirror6
 * @since 2021-03-21
 */
public interface ISystemMenuServiceApi {


    /**
     * 添加
     *
     * @param systemMenuDto dto
     * @return res
     */
    Result createSystemMenu(SystemMenuDto systemMenuDto);

    /**
     * 删除
     *
     * @param ids 主键
     * @return res
     */
    Result deleteBatchSystemMenu(Long[] ids);

    /**
     * 编辑
     *
     * @param systemMenuDto dto
     * @return res
     */
    Result editSystemMenu(SystemMenuDto systemMenuDto);

    /**
     * 列表
     *
     * @param systemMenuQuery query
     * @return res
     */
    Result pageSystemMenu(SystemMenuQuery systemMenuQuery);

    /**
     * 菜单树
     *
     * @return res
     */
    Result treeSystemMenu();
}
