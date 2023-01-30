package cn.mirror6.rbac.center.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：gong sun
 * @description: SystemAuthorityDto
 * @date ：Created in 2021/3/22 5:49 下午
 */
@Data
public class SystemAuthorityDto implements Serializable {

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
     * 权限标识
     */
    private String mark;

    /**
     * 描述
     */
    private String description;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 排序码
     */
    private Integer sort;

    /**
     * 启用状态
     */
    private Boolean enabled;

}
