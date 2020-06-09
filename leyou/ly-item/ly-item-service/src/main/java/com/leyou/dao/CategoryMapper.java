package com.leyou.dao;

import com.leyou.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper extends tk.mybatis.mapper.common.Mapper<Category> {


    List<Category> findCnameByCids( List<Long> cids);
}
