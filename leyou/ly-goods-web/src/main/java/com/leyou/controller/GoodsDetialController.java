package com.leyou.controller;

import com.leyou.client.*;
import com.leyou.pojo.*;
import com.leyou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class GoodsDetialController {

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

    @Autowired
    GoodsService goodsService;

    @RequestMapping("item/{spuId}.html")
    public String item(@PathVariable("spuId") Long spuId, Model model){

//        //根据spuId查询商品集spu
//        Spu spu = spuClientServer.findSpuBySpuId(spuId);
//        model.addAttribute("spu",spu);
//
//        //根据spuId查询spuDetail扩展表
//        SpuDetail spuDetail = spuClientServer.findSpuDetailBySpuId(spuId);
//        model.addAttribute("spuDetail",spuDetail);
//
//        //根据spuId查询sku
//        List<Sku> skuList = skuCilentServer.findSkuBySpuId(spuId);
//        model.addAttribute("skuList",skuList);
//
//        //根据cid查询规格组和组对应的规格参数
//        List<SpecGroup> specGroupList = specClientServer.findAllSpecGroupAndSpecParamByCid(spu.getCid3());
//        specGroupList.forEach(specGroup -> {
//            System.out.println(specGroup.getSpecParamList());
//        });
//        model.addAttribute("specGroupList",specGroupList);
//
//        //根据分类id查询
//        //先把cid的三级id存入集合
//        List<Long> cids=new ArrayList<>();
//        cids.add(spu.getCid1());
//        cids.add(spu.getCid2());
//        cids.add(spu.getCid3());
//        List<Category> categoryList = categoryCilentServer.findCnameByCids(cids);
//        categoryList.forEach(c->{
//            System.out.println(c.getName());
//        });
//        model.addAttribute("categoryList",categoryList);
//
//        //查询品牌
//        Brand brand = brandClientServer.findBrandById(spu.getBrandId());
//        model.addAttribute("brand",brand);
//
//        //查询规格参数
//        List<SpecParam> paramList = specClientServer.findSpecParamsByCidAndGeneric(spu.getCid3(), false);
//        HashMap<Long,String> paramMap=new HashMap<>();
//        paramList.forEach(param->{
//            paramMap.put(param.getId(),param.getName());
//        });
//
//        model.addAttribute("paramMap",paramMap);

        HashMap<String, Object> item = goodsService.item(spuId);
        model.addAllAttributes(item);

        //创建静态模板
        goodsService.createHtml(spuId);


        return "item";
    }

/*    private void createHtml(Spu spu, SpuDetail spuDetail, List<Sku> skuList, List<SpecGroup> specGroupList, List<Category> categoryList, Brand brand, HashMap<Long, String> paramMap) {

        PrintWriter writer=null;

        try {
            // 创建thymeleaf上下文对象
            Context context = new Context();
            // 把数据放入上下文对象
            context.setVariable("spu",spu);
            context.setVariable("spuDetail",spuDetail);
            context.setVariable("skuList",skuList);
            context.setVariable("specGroupList",specGroupList);
            context.setVariable("categoryList",categoryList);
            context.setVariable("brand",brand);
            context.setVariable("paramMap",paramMap);

            // 创建输出流
            File file = new File("D:\\syst\\nginx-1.16.1\\html\\" + spu.getId() + ".html");
            writer = new PrintWriter(file);
            // 执行页面静态化方法
            templateEngine.process("item", context, writer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            //关闭流
            writer.close();
        }


    }*/


}
