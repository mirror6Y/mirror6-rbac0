package cn.mirror6.rbac.center.pojo.query;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mirror6
 * @description
 * @createTime 2020/5/9 14:05
 */
@Data
class BaseQuery implements Serializable {

    /**
     * 每页的数据数量
     */
    Integer pageSize;

    /**
     * 第几页
     */
    Integer pageNum;
}
