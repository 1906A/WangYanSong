package com.leyou.service;

import com.leyou.dao.SkuMapper;
import com.leyou.dao.StockMapper;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuService {

    @Autowired
    SkuMapper skuMapper;

    @Autowired
    StockMapper stockMapper;

    /**
     * 根据spuid查询商品
     * @param id
     * @return
     */
    public List<Sku> findSkuBySpuId(Long id) {
     List<Sku> skuList= skuMapper.findSkuBySpuId(id);
     //根据对应的商品查询库存
     skuList.forEach(sku->{
         Stock stock = stockMapper.selectByPrimaryKey(sku.getId());
         sku.setStock(stock.getStock());
     });
        return skuList;
    }

}
