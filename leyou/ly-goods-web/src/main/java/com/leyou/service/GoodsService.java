package com.leyou.service;

import com.leyou.client.*;
import com.leyou.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GoodsService {
    @Autowired
    SpuClientServer spuClientServer;

    @Autowired
    SkuCilentServer skuCilentServer;

    @Autowired
    SpecClientServer specClientServer;

    @Autowired
    CategoryCilentServer categoryCilentServer;

    @Autowired
    BrandClientServer brandClientServer;

    @Autowired
    TemplateEngine templateEngine;


    public HashMap<String, Object> item(Long spuId){
        //根据spuId查询商品集spu
        Spu spu = spuClientServer.findSpuBySpuId(spuId);

        //根据spuId查询spuDetail扩展表
        SpuDetail spuDetail = spuClientServer.findSpuDetailBySpuId(spuId);

        //根据spuId查询sku
        List<Sku> skuList = skuCilentServer.findSkuBySpuId(spuId);

        //根据cid查询规格组和组对应的规格参数
        List<SpecGroup> specGroupList = specClientServer.findAllSpecGroupAndSpecParamByCid(spu.getCid3());
        specGroupList.forEach(specGroup -> {
            System.out.println(specGroup.getSpecParamList());
        });

        //根据分类id查询
        //先把cid的三级id存入集合
        List<Long> cids=new ArrayList<>();
        cids.add(spu.getCid1());
        cids.add(spu.getCid2());
        cids.add(spu.getCid3());
        List<Category> categoryList = categoryCilentServer.findCnameByCids(cids);
        categoryList.forEach(c->{
            System.out.println(c.getName());
        });

        //查询品牌
        Brand brand = brandClientServer.findBrandById(spu.getBrandId());

        //查询规格参数
        List<SpecParam> paramList = specClientServer.findSpecParamsByCidAndGeneric(spu.getCid3(), false);
        HashMap<Long,String> paramMap=new HashMap<>();
        paramList.forEach(param->{
            paramMap.put(param.getId(),param.getName());
        });

        HashMap<String,Object> map=new HashMap<>();
        map.put("spu",spu);
        map.put("spuDetail",spuDetail);
        map.put("skuList",skuList);
        map.put("specGroupList",specGroupList);
        map.put("categoryList",categoryList);
        map.put("brand",brand);
        map.put("paramMap",paramMap);


        return map;

    }

    public void createHtml(Long spuId) {

        PrintWriter writer=null;

        try {
            // 创建thymeleaf上下文对象
            Context context = new Context();
//            // 把数据放入上下文对象
//            context.setVariable("spu",spu);
//            context.setVariable("spuDetail",spuDetail);
//            context.setVariable("skuList",skuList);
//            context.setVariable("specGroupList",specGroupList);
//            context.setVariable("categoryList",categoryList);
//            context.setVariable("brand",brand);
//            context.setVariable("paramMap",paramMap);
            HashMap<String, Object> item = this.item(spuId);
            context.setVariables(item);

            // 创建输出流
            File file = new File("D:\\syst\\nginx-1.16.1\\html\\" + spuId + ".html");
            writer = new PrintWriter(file);
            // 执行页面静态化方法
            templateEngine.process("item", context, writer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            //关闭流
            writer.close();
        }


    }

    public void deleteHtml(Long spuId) {
        File file = new File("D:\\syst\\nginx-1.16.1\\html\\" + spuId + ".html");
        if(file.exists() && file!=null){
            file.delete();
        }

    }
}
