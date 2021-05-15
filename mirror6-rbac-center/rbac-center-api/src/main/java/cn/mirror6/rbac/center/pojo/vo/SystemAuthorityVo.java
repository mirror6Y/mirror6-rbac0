package cn.mirror6.rbac.center.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: mirror6
 * @create: 2021-03-27 15:14
 **/
@Data
public class SystemAuthorityVo implements Serializable {

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
     * 权限标识
     */
    private String mark;

    /**
     * 描述
     */
    private String description;

    /**
     * 子list
     */
    private List<SystemAuthorityVo> childList;
}
