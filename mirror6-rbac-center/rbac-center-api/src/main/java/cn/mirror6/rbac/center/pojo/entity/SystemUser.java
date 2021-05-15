package cn.mirror6.rbac.center.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author mirror6
 * @since 2021-03-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemUser extends Model<SystemUser> {

    private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
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
     * 盐
     */
    private String salt;

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
    @TableField(value = "is_enabled")
    private Integer enabled;

    /**
     * 创建者
     */
    private Long creator;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改者
     */
    private Long updater;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    /**
     * 逻辑删除
     */
    @TableField(value = "is_deleted")
    private Integer deleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
