///*
// *
// *  Copyright 2020 byai.com All right reserved. This software is the
// *  confidential and proprietary information of byai.com ("Confidential
// *  Information"). You shall not disclose such Confidential Information and shall
// *  use it only in accordance with the terms of the license agreement you entered
// *  into with byai.com.
// * /
// */
//
//package cn.mirror6.rbac.util;
//
////import com.github.pagehelper.Page;
//import com.github.pagehelper.Page;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.io.Serializable;
//import java.util.List;
//
///**
// * Created by tianjin.lp on 16/12/1.
// */
//@Getter
//@Setter
//public class SimplePageInfo<T> implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    //当前页
//    private int pageNum;
//    //每页的数量
//    private int pageSize;
//    //当前页的数量
//    private int size;
//    //排序
//    private String orderBy;
//
//    //由于startRow和endRow不常用，这里说个具体的用法
//    //可以在页面中"显示startRow到endRow 共size条数据"
//
//    //当前页面第一个元素在数据库中的行号
//    private int startRow;
//    //当前页面最后一个元素在数据库中的行号
//    private int endRow;
//    //总记录数
//    private long total;
//    //总页数
//    private int pages;
//    //结果集
//    private List<T> list;
//
//    /**
//     * 包装Page对象
//     *
//     * @param list
//     */
//    public SimplePageInfo(List<T> list) {
//        this(list, 8);
//    }
//
//    /**
//     * 包装Page对象
//     *
//     * @param list          page结果
//     * @param navigatePages 页码数量
//     */
//    public SimplePageInfo(List<T> list, int navigatePages) {
//        if (list instanceof Page) {
//            Page page = (Page) list;
//            this.pageNum = page.getPageNum();
//            this.pageSize = page.getPageSize();
//            this.orderBy = page.getOrderBy();
//
//            this.pages = page.getPages();
//            this.list = page;
//            this.size = page.size();
//            this.total = page.getTotal();
//            //由于结果是>startRow的，所以实际的需要+1
//            if (this.size == 0) {
//                this.startRow = 0;
//                this.endRow = 0;
//            } else {
//                this.startRow = page.getStartRow() + 1;
//                //计算实际的endRow（最后一页的时候特殊）
//                this.endRow = this.startRow - 1 + this.size;
//            }
//        } else if (list != null) {
//            this.pageNum = 1;
//            this.pageSize = list.size();
//
//            this.pages = 1;
//            this.list = list;
//            this.size = list.size();
//            this.total = list.size();
//            this.startRow = 0;
//            this.endRow = list.isEmpty() ? 0 : list.size() - 1;
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "PageInfo{" + "pageNum=" + pageNum +
//                ", pageSize=" + pageSize +
//                ", size=" + size +
//                ", startRow=" + startRow +
//                ", endRow=" + endRow +
//                ", total=" + total +
//                ", pages=" + pages +
//                ", list=" + list +
//                '}';
//    }
//
//}
