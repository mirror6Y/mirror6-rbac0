package cn.mirror6.rbac.center.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：gong sun
 * @description:
 * @date ：Created in 2021/3/30 2:25 下午
 */
@Data
public class SystemRoleVo implements Serializable {


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
     * 子角色
     */
    private List<SystemRoleVo> childList;
}
