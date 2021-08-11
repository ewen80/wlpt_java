package pw.ewen.WLPT.controllers.utils;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * created by wenliang on 2021/7/30
 * 自定义Page类，由于@PostFilter等不能用于Page,于是自定义一个Page,每次取出所有符合权限的数据组成List,再对List进行分页，排序等
 */
public class MyPage<T>  {

    private final List<T> content;
    private final PagedListHolder<T> page;
    private final PageRequest pr;

    public MyPage(List<T> content, PageRequest pr) {
        this.content = content;
        this.page = new PagedListHolder<>(content);
        this.pr = pr;
        Sort sort = pr.getSort();
        if(sort != null) {
            for(Sort.Order order : sort) {
                MutableSortDefinition msd = new MutableSortDefinition(order.getProperty(), true, order.isAscending());
                page.setSort(msd);
                page.resort();
            }
        }

        page.setPageSize(pr.getPageSize()); // number of items per page
        page.setPage(pr.getPageNumber());      // set to first page
    }

    public PageImpl<T> getPage() {
        return new PageImpl<>(this.page.getPageList(), this.pr, this.content.size());
    }
}
