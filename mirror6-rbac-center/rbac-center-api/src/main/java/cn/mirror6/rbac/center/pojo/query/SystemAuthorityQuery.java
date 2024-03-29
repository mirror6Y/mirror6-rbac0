package cn.mirror6.rbac.center.pojo.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: mirror6
 * @create: 2021-03-27 15:07
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemAuthorityQuery extends BaseQuery{

    /**
     * 名称
     */
    private String name;

    /**
     * 权限标识
     */
    private String mark;

    /**
     * 启用状态
     */
    private Integer isEnabled;
}
