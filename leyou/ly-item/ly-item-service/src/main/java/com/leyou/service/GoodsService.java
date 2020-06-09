package com.leyou.service;

import com.leyou.dao.SkuMapper;
import com.leyou.dao.SpuDetailMapper;
import com.leyou.dao.SpuMapper;
import com.leyou.dao.StockMapper;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.pojo.Stock;
import com.leyou.vo.SpuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GoodsService {

    @Autowired
    SpuMapper spuMapper;

    @Autowired
    SpuDetailMapper spuDetailMapper;

    @Autowired
    SkuMapper skuMapper;

    @Autowired
    StockMapper stockMapper;

    public void addGoods(SpuVo spuVo) {

        //设置一个时间
        Date nowDate=new Date();

        //新增spu
        Spu spu=new Spu();
        spu.setId(null);  //设置id
        spu.setSaleable(false); //设置默认下线状态
        spu.setValid(true); //设置有效
        spu.setCreateTime(nowDate);
        spu.setLastUpdateTime(nowDate);
        spu.setBrandId(spuVo.getBrandId());
        spu.setCid1(spuVo.getCid1());
        spu.setCid2(spuVo.getCid2());
        spu.setCid3(spuVo.getCid3());
        spu.setTitle(spuVo.getTitle());
        spu.setSubTitle(spuVo.getSubTitle());

        spuMapper.insertSelective(spu);

        //新增spuDetail
        SpuDetail spuDetail = spuVo.getSpuDetail();
        spuDetail.setSpuId(spu.getId());  //此处设置得是spuid
        spuDetailMapper.insertSelective(spuDetail);

        //新增sku
        List<Sku> skuList = spuVo.getSkus();
        skuList.forEach(sku->{
            sku.setSpuId(spu.getId());  //此处设置得是spuid
            sku.setCreateTime(nowDate);
            sku.setLastUpdateTime(nowDate);
            sku.setEnable(true);
            skuMapper.insertSelective(sku);

            //新增库存表
            Stock stock=new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insertSelective(stock);

        });





    }

    public void updateGoods(SpuVo spuVo) {

        //设置一个时间
        Date nowDate=new Date();

        //修改spu
        spuVo.setSaleable(null); //设置默认下线状态
        spuVo.setValid(null); //设置有效
        spuVo.setCreateTime(null);
        spuVo.setLastUpdateTime(nowDate);
        spuMapper.updateByPrimaryKeySelective(spuVo);

        //修改spudetail
        SpuDetail spuDetail = spuVo.getSpuDetail();
        spuDetail.setSpuId(spuVo.getId());
        spuDetailMapper.updateByPrimaryKeySelective(spuDetail);

        //删除sku
        List<Sku> skuList = spuVo.getSkus();
        skuList.forEach(sku -> {
            sku.setEnable(false);
            skuMapper.updateByPrimaryKeySelective(sku);

            stockMapper.deleteByPrimaryKey(sku.getId());
        });

        //新增sku
        List<Sku> skuList1 = spuVo.getSkus();
        skuList1.forEach(sku->{
            sku.setSpuId(spuVo.getId());  //此处设置得是spuid
            sku.setCreateTime(nowDate);
            sku.setLastUpdateTime(nowDate);
            sku.setEnable(true);
            skuMapper.insertSelective(sku);
            //新增库存表
            Stock stock=new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insertSelective(stock);

        });

    }
}
