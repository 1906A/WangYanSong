package com.leyou.pojo;

import com.leyou.common.PageResult;

import java.util.List;
import java.util.Map;

public class SearchResult extends PageResult<Goods> {

    private List<Category> categories;
    private List<Brand> brands;
    private List<Map<String,Object>> parmaList;

    public List<Map<String, Object>> getParmaList() {
        return parmaList;
    }

    public void setParmaList(List<Map<String, Object>> parmaList) {
        this.parmaList = parmaList;
    }

    public SearchResult(Long total, List<Goods> items, Integer totalPage, List<Category> categories, List<Brand> brands, List<Map<String, Object>> parmaList) {
        super(total, items, totalPage);
        this.categories = categories;
        this.brands = brands;
        this.parmaList = parmaList;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }
}