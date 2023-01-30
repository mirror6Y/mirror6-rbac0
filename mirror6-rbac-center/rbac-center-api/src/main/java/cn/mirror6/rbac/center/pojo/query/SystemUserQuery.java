package cn.mirror6.rbac.center.pojo.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ：gong sun
 * @description:
 * @date ：Created in 2021/3/30 7:33 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemUserQuery extends BaseQuery {

    /**
     * 账号
     */
    private String account;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 手机号码
     */
    private String tel;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 启用状态
     */
    private Integer enabled;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;
}
