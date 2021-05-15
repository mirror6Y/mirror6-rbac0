package cn.mirror6.rbac.center.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：gong sun
 * @description: SystemUserDto
 * @date ：Created in 2021/3/22 7:27 下午
 */
@Data
public class SystemUserDto implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

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
     * 角色数组
     */
    private Long[] roleIds;

}
