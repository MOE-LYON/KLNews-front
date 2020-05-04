package com.moelyon.ktnews.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author moelyon
 */
public class Pagination<T> {
    @SerializedName("totalpage")
    private int totalPage;
    private List<T> items;
    @SerializedName("pagesize")
    private int pageSize;
    private int page;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
