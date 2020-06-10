package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.PageResult;
import com.leyou.dao.SkuMapper;
import com.leyou.dao.SpuDetailMapper;
import com.leyou.dao.SpuMapper;
import com.leyou.dao.StockMapper;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.vo.SpuVo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class SpuService {

    @Autowired
    SpuMapper spuMapper;

    @Autowired
    SpuDetailMapper spuDetailMapper;

    @Autowired
    SkuMapper skuMapper;

    @Autowired
    StockMapper stockMapper;

    @Autowired
    AmqpTemplate amqpTemplate;




    public  PageResult<SpuVo> findAllSpu(String key, Integer saleable, Integer page, Integer rows) {

        PageHelper.startPage(page,rows);

        List<SpuVo> spuList = spuMapper.findAllSpu(key, saleable, page, rows);

        PageInfo<SpuVo> page1=new PageInfo<>(spuList);

        return new PageResult<SpuVo>(page1.getTotal(),page1.getList());

    }

    public SpuDetail findSpuDetail(Long id) {

          return  spuDetailMapper.selectByPrimaryKey(id);
    }

    /**
     * 封装一个方法
     * @param type
     * @param spuId
     */
    public void sendMsg(String type,Long spuId){
        amqpTemplate.convertAndSend("item.exchanges","item."+type,spuId);
    }

    public void deleteBySpuId(Long id) {

        //先删除sku表


        Sku sku=new Sku();
        sku.setSpuId(id);
        List<Sku> skuList = skuMapper.select(sku);
        skuList.forEach(sku1 -> {
            sku1.setEnable(false);
            skuMapper.updateByPrimaryKeySelective(sku1);
            //根据sku删除库存
            stockMapper.deleteByPrimaryKey(sku1.getId());
        });
        //删除spudeltail
        spuDetailMapper.deleteByPrimaryKey(id);

        //删除spu
        Spu spu = spuMapper.selectByPrimaryKey(id);
        spu.setValid(false);
        spuMapper.updateByPrimaryKeySelective(spu);

        sendMsg("delete",id);


    }

    public void downOrUp(Integer saleable, Long id) {
        //查询出商品
        Spu spu = spuMapper.selectByPrimaryKey(id);
        //设置状态
        spu.setSaleable(saleable==1?true:false);
        spuMapper.updateByPrimaryKey(spu);
    }

    /**
     * 根据spudId查询spu
     * @param spuId
     * @return
     */
    public Spu findSpuBySpuId(Long spuId) {
       return spuMapper.selectByPrimaryKey(spuId);
    }


    public SpuVo findSpuVoBySpuId(Long spuId) {

        return spuMapper.findSpuVoBySpuId(spuId);
    }
}
