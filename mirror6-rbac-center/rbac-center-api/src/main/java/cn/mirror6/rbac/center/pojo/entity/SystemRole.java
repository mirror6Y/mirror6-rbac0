package cn.mirror6.rbac.center.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class SystemRole extends Model<SystemRole> {

    private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序码
     */
    private Integer sort;

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
