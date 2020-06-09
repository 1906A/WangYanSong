package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.vo.SpuVo;
import com.leyou.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("spu")
public class SpuController {


    @Autowired
    SpuService spuService;

    /**
     *
     * 分页展示所有商品集
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("page")
    public PageResult<SpuVo> findAllSpu(@RequestParam( value = "key",required = false)String key,@RequestParam( value = "saleable")Integer saleable,
                                @RequestParam("page")Integer page,@RequestParam("rows")Integer rows
                                ){

        PageResult<SpuVo> spuList= spuService.findAllSpu(key,saleable,page,rows);

        return spuList;
    }


    /**
     * 根据商品集得id查询扩展表信息
     * @param id
     * @return
     */
    @RequestMapping("detail/{id}")
    public SpuDetail findSpuDetailBySpuId(@PathVariable("id")Long id){

        return spuService.findSpuDetail(id);
    }


    /**
     * 根据spu的id删除商品
     * @param id
     */
    @RequestMapping("deleteById/{id}")
    public void deleteBySpuId(@PathVariable("id")Long id){
        spuService.deleteBySpuId(id);
    }


    /**
     * 商品的下架上架
     * @param saleable
     * @param id
     */
    @RequestMapping("downOrUp")
    public void downOrUpSpu(@RequestParam("saleable")int saleable,@RequestParam("spuid")Long id){
        spuService.downOrUp(saleable,id);
    }

    /**
     * 根据spuId查询spu商品集
     * @param spuId
     * @return
     */
    @RequestMapping("findSpuBySpuId")
    public Spu findSpuBySpuId(@RequestParam("spuId") Long spuId){

        return spuService.findSpuBySpuId(spuId);
    }


}
