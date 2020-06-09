package com.leyou.controller;

import com.leyou.service.GoodsService;
import com.leyou.vo.SpuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodsController {

    @Autowired
    GoodsService goodsService;


    /**
     * 添加,修改商品信息
     * @param spuVo
     */
    @RequestMapping("goods")
    public void addGoods(@RequestBody SpuVo spuVo){

        if(spuVo.getId()==null){
            goodsService.addGoods(spuVo);
        }else {
            goodsService.updateGoods(spuVo);
        }

    }

}
