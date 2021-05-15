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

    /**
     * 父ID
     */
    private Long parentId;

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
     * 菜单ID
     */
    private Long menuId;

    /**
     * 排序码
     */
    private Integer sort;

}
