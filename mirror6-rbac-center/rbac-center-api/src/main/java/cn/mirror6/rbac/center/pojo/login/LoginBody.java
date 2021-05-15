package cn.mirror6.rbac.center.pojo.login;

import lombok.Data;

/**
 * @description:
 * @author: mirror6
 * @create: 2021-04-24 15:02
 **/
@Data
public class LoginBody {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid = "";
}
