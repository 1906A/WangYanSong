package com.leyou.client;

import com.leyou.pojo.Brand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("brand")
public interface BrandClient {

    @RequestMapping("findBrandById")
    public Brand findBrandById(@RequestParam("id") Long id);
}
