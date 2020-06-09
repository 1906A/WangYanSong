package com.leyou.client;

import com.leyou.pojo.Category;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("category")
public interface CategoryCilent {

    @RequestMapping("findCategoryByCid")
    public Category findCategoryByCid(@RequestParam("id") Long id);

    @RequestMapping("findCnameByCids")
    public List<Category> findCnameByCids(@RequestParam("cids") List<Long> cids);
}
