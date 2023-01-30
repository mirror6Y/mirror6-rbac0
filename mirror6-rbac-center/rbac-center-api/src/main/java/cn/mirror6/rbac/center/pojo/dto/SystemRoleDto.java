package cn.mirror6.rbac.center.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：gong sun
 * @description: SystemRoleDto
 * @date ：Created in 2021/3/22 7:26 下午
 */
@Data
public class SystemRoleDto implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 父ID
     */
    private Long parentId = 0L;

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
    private Boolean enabled;

    /**
     * 权限数组
     */
    private Long[] authIds;

}
