package pw.ewen.WLPT.controllers.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * created by wenliang on 2021/3/21
 * 作为分页查询时候匹配参数值的辅助类
 */
public class PageInfo {
    /**
     * 当前页
     */
    int pageIndex = 0;
    /**
     * 每页条目
     */
    int pageSize = 20;
    /**
     * 排序（ASC DESC)
     */
    String sortDirection = "ASC";
    /**
     * 排序字段
     */
    String sortField = "";
    /**
     * 过滤器
     */
    String filter = "";

    public PageRequest getPageRequest() {
        if(this.sortField.isEmpty()) {
            return new PageRequest(this.getPageIndex(), this.getPageSize());
        } else {
            return new PageRequest(this.getPageIndex(), this.getPageSize(), new Sort(Sort.Direction.fromString(this.getSortDirection()), this.getSortField()));
        }

    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
