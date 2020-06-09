package com.leyou.client;

import com.leyou.common.PageResult;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.vo.SpuVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("spu")
public interface SpuClient {

    @RequestMapping("page")
    public PageResult<SpuVo> findAllSpu(@RequestParam( value = "key",required = false)String key, @RequestParam( value = "saleable")Integer saleable,
                                        @RequestParam("page")Integer page, @RequestParam("rows")Integer rows);


    @RequestMapping("detail/{id}")
    public SpuDetail findSpuDetailBySpuId(@PathVariable("id")Long id);


    @RequestMapping("findSpuBySpuId")
    public Spu findSpuBySpuId(@RequestParam("spuId") Long spuId);



}
