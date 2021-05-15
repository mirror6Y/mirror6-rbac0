package cn.mirror6.rbac.center.pojo.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ：gong sun
 * @description:
 * @date ：Created in 2021/3/29 5:06 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemRoleQuery extends BaseQuery{

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 启用状态
     */
    private Integer isEnabled;
}
