package cn.mirror6.rbac.center.pojo.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@Builder
public class SystemRoleAuthority extends Model<SystemRoleAuthority> {

    private static final long serialVersionUID=1L;

    private Long roleId;

    private Long authId;


    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }

}
