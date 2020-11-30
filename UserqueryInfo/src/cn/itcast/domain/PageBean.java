package cn.itcast.domain;

/**
 * @author QLBF
 * @version 1.0
 * @date 2020/11/30 10:49
 */

import java.util.List;

/**
 * 分页对象，分页查询要用的,加了泛型T更加通用，其他表也可以用的
 */
public class PageBean<T> {
    private int totalCount; //总记录数
    private int totalPage; //总页码
    private List<T> list; //每页展示的数据
    private int currentPage; //当前页码
    private int rows; //每页展示的记录数

    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", list=" + list +
                ", currentPage=" + currentPage +
                ", rows=" + rows +
                '}';
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
