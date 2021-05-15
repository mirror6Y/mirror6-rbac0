package cn.mirror6.rbac.center.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：gong sun
 * @description: SystemMenuDto
 * @date ：Created in 2021/3/22 5:34 下午
 */
@Data
public class SystemMenuDto implements Serializable {

    private Long id;

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 路由
     */
    private String url;

    /**
     * 图标路径
     */
    private String iconPath;

    /**
     * 排序码
     */
    private Integer sort;

    /**
     * 启动状态
     */
    private Integer enabled;

}

