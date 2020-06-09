package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    BrandService brandService;


    /**
     * 根据分页查询所有品牌信息
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @RequestMapping("page")
    public Object page(@RequestParam("key")String key,@RequestParam("page")Integer page,
                       @RequestParam("rows")Integer rows,@RequestParam(value = "sortBy",required = false)String sortBy,
                       @RequestParam(value = "desc",required = false)boolean desc
                       ){
      PageResult<Brand> brandList = brandService.findBrandAll2(key,page,rows,sortBy,desc);

        return brandList;
    }


    /**
     * 添加品牌
     * 修改品牌
     * @param brand
     * @param cids
     */
    @RequestMapping("addOrEditBrand")
    public void addBrand(Brand brand,@RequestParam(value = "cids",required = false)List<String> cids){

        if(brand.getId()==null) {
            brandService.insert(brand);
            System.out.println("1111");
            cids.forEach(id -> {
                brandService.addCategoryAndBrand(brand.getId(), Long.parseLong(id));
            });
        }else {
                brandService.updateCategoryAndBrand(brand,cids);

        }
    }


    /**
     * 删除品牌和关联分类
     * @param id
     */
    @RequestMapping("deleteById/{id}")
    public void delteCategoryAndBrnadByBid(@PathVariable("id")Long id){

        brandService.deleteByBid(id);
    }


    /**
     * 回显关联表
     * @param id
     * @return
     */
    @RequestMapping("bid/{id}")
    public List<Category> findById(@PathVariable("id")Long id){

      return  brandService.findById(id);
    }


    /**
     * 根据分类id 查询品牌
     * @param cid
     * @return
     */
    @RequestMapping("cid/{cid}")
    public List<Brand> findBrandByCid(@PathVariable("cid")Long cid){

        return brandService.findBrandByCid(cid);
    }

    /**
     * 根据品牌id查询商品
     * @param id
     * @return
     */
    @RequestMapping("findBrandById")
    public Brand findBrandById(@RequestParam("id") Long id){
        return brandService.findBrandById(id);
    }



}
