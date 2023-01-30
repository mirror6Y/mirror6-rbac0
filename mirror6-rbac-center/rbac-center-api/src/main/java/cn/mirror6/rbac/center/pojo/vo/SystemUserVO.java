package cn.mirror6.rbac.center.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ：gong sun
 * @description:
 * @date ：Created in 2021/3/30 8:08 下午
 */
@Data
public class SystemUserVO implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 性别
     */
    private Integer gender;

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
    private Boolean enabled;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 角色数组
     */
    private Long[] roleIds;
}
