package cn.mirror6.rbac.center.pojo.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ：gong sun
 * @description: SystemMenuQuery
 * @date ：Created in 2021/3/22 8:03 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemMenuQuery extends BaseQuery {

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 启用状态
     */
    private Integer enabled;

}
