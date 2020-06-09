package com.leyou.controller;

import com.leyou.pojo.Category;
import com.leyou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    /**
     * 查询全部商品分类信息
     * @param pid
     * @return
     */
    @RequestMapping("list")
    public List<Category> list(@RequestParam("pid")Long pid){
        Category category=new Category();
        category.setParentId(pid);
        return categoryService.findCategoryAll(category);
    }



    /**
     *
     * 添加商品分类信息
     * @param category
     * @return
     */
    @RequestMapping("add")
    public String add(@RequestBody Category category){
        String result="SUCC";

            try {
                    categoryService.addCategory(category);
            }catch (Exception e){
                result="FAIL";
            }
        return result;
    }

    /**
     *
     * 修改商品分类信息
     * @param category
     * @return
     */
    @RequestMapping("update")
    public String update(@RequestBody Category category){
        String result="SUCC";
        try {
            categoryService.updateCategory(category);
        }catch (Exception e){
            result="FAIL";
        }
        return result;
    }


    /**
     *
     * 删除商品分类信息
     * @param  id
     * @return
     */
    @RequestMapping("deleteById")
    public String deleteById(@RequestParam("id")Long id){
        String result="SUCC";
        try {

            categoryService.deleteById(id);
        }catch (Exception e){
            result="FAIL";
        }
        return result;
    }

    /**
     * 根据cid查询分类
     * @param id
     * @return
     */
    @RequestMapping("findCategoryByCid")
    public Category findCategoryByCid(@RequestParam("id") Long id){
       return categoryService.findCategoryByCid(id);
    }

    /**
     * 根据cid的集合查询出三级分类名称
     * @param cids
     * @return
     */
    @RequestMapping("findCnameByCids")
    public List<Category> findCnameByCids(@RequestParam("cids") List<Long> cids){

        return categoryService.findCnameByCids(cids);
    }
}
