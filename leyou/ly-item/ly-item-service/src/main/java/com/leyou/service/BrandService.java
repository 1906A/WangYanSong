package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.PageResult;
import com.leyou.dao.BrandMapper;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    BrandMapper brandMapper;

    public PageResult<Brand> findBrandAll(String key, Integer page, Integer rows, String sortBy, boolean desc) {

        //设置当页数和展示条数
        PageHelper.startPage(page,rows);

        //根据条件向后台查询数据
        List<Brand> brandList= brandMapper.findBrandAll(key,sortBy,desc);
        //得到一个pageinfo
        PageInfo<Brand> pageInfo=new PageInfo<Brand>(brandList);

        return new PageResult<Brand>(pageInfo.getTotal(),pageInfo.getList());

    }

    public PageResult<Brand> findBrandAll2(String key, Integer page, Integer rows, String sortBy, boolean desc) {

        //根据条件向后台查询数据
        List<Brand> brandList= brandMapper.findBrandAll2((page-1)*rows,rows,key,sortBy,desc);

       Long count= brandMapper.findCount(key,sortBy,desc);

        return new PageResult<Brand>(count,brandList);

    }


    /**
     *
     * 添加品牌
     * @param brand
     */
    public void insert(Brand brand) {
        brandMapper.insertBrand(brand);
    }



    public void addCategoryAndBrand(Long bid,Long cid) {
        brandMapper.isertCategoryAndBrand(bid,cid);
    }

    public void deleteByBid(Long id) {
        brandMapper.deleteByPrimaryKey(id);

        //删除管理的分类
        brandMapper.deleteBrandAndCategory(id);

    }

    public void updateCategoryAndBrand(Brand brand, List<String> cids) {

        //修改
        brandMapper.updateByPrimaryKey(brand);

        brandMapper.deleteBrandAndCategory(brand.getId());

        cids.forEach(cid->{
            brandMapper.isertCategoryAndBrand(brand.getId(),Long.parseLong(cid));
        });

    }

    public List<Category> findById(Long id) {
        return brandMapper.findById(id);
    }

    public List<Brand> findBrandByCid(Long cid) {

        return brandMapper.findBrandByCid(cid);
    }

    public Brand findBrandById(Long id) {

        return brandMapper.selectByPrimaryKey(id);
    }
}
