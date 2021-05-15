package cn.mirror6.rbac.center.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：gong sun
 * @description: SystemMenuVo
 * @date ：Created in 2021/3/22 7:33 下午
 */
@Data
public class SystemMenuVo implements Serializable {

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
     * 子list
     */
    private List<SystemMenuVo> childList;
}
