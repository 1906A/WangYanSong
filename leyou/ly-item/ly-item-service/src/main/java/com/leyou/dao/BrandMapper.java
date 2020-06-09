package com.leyou.dao;

import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {

    List<Brand> findBrandAll(@Param("key")String key, @Param("sortBy") String sortBy,@Param("desc") boolean desc);

    List<Brand> findBrandAll2(@Param("page")int page,@Param("rows") Integer rows,@Param("key") String key,@Param("sortBy") String sortBy,@Param("desc") boolean desc);

    Long findCount(@Param("key")String key,@Param("sortBy") String sortBy,@Param("desc") boolean desc);

    void insertBrand(Brand brand);

    void isertCategoryAndBrand(@Param("bid")Long bid,@Param("cid") Long cid);


    void deleteBrandAndCategory(Long id);

    @Select("select y.* from tb_category_brand t,tb_category y where t.category_id=y.id and t.brand_id=#{id}")
    List<Category> findById(Long id);


    List<Brand> findBrandByCid(Long cid);
}
