package com.leyou.service;

import com.leyou.dao.CategoryMapper;
import com.leyou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryMapper categoryMapper;


    public List<Category> findCategoryAll(Category category){
        return categoryMapper.select(category);
    }

    public void addCategory(Category category) {
        categoryMapper.insertSelective(category);
    }

    public void updateCategory(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    public void deleteById(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    public Category findCategoryByCid(Long id) {
       return categoryMapper.selectByPrimaryKey(id);
    }

    public List<Category> findCnameByCids(List<Long> cids) {

        return  categoryMapper.findCnameByCids(cids);
    }
}
