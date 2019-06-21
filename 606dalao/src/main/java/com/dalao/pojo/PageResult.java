package com.dalao.pojo;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    private Long total;// 总条数
    private int totalPage;// 总页数
    private List<T> items;// 当前页数据

    public PageResult() {
    }

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, int totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }

}