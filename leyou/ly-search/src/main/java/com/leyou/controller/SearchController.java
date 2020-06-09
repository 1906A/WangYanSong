package com.leyou.controller;

import com.leyou.client.BrandClientServer;
import com.leyou.client.CategoryCilentServer;
import com.leyou.client.SpecClientServer;
import com.leyou.common.PageResult;
import com.leyou.pojo.*;
import com.leyou.repository.GoodsRepository;
import com.leyou.service.SpuService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchService;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("search")
public class SearchController {


    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    CategoryCilentServer categoryCilentServer;

    @Autowired
    BrandClientServer brandClientServer;

    @Autowired
    SpecClientServer specClientServer;


    @RequestMapping("page")
    public  PageResult<Goods> findAllGoods(@RequestBody SearchRequest searchRequest){

        //获取前台发送的查询数据和分页页数
        System.out.println(searchRequest.getKey()+"==="+searchRequest.getPage()+"==="+searchRequest.getFilter());

        //创建 NativeSearchQueryBuilder对象
        NativeSearchQueryBuilder queryBuilder=new NativeSearchQueryBuilder();


        //生成bool判断   分类、品牌、规格参数得过滤查询
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        // 添加基本查询条件
        boolQueryBuilder.must(QueryBuilders.matchQuery("all", searchRequest.getKey()).operator(Operator.AND));

        //判断前台得filter字段是不是有值
        if(searchRequest.getFilter()!=null && searchRequest.getFilter().size()>0) {
            //遍历filter
            searchRequest.getFilter().keySet().forEach(key->{

                String filed="specs."+key+".keyword";
                if(key.equals("分类")){
                    filed="cid3";
                }else if(key.equals("品牌")){
                    filed="brandId";
                }
                boolQueryBuilder.filter(QueryBuilders.termQuery(filed,searchRequest.getFilter().get(key)));

            });
        }

        queryBuilder.withQuery(boolQueryBuilder);

//        //通过match查询all中对应的字段
//        queryBuilder.withQuery(QueryBuilders.matchQuery("all",searchRequest.getKey()).operator(Operator.AND));

        //进行分页
        queryBuilder.withPageable(PageRequest.of(searchRequest.getPage()-1,searchRequest.getSize()));

        //排序
        queryBuilder.withSort(SortBuilders.fieldSort(searchRequest.getSortBy()).order(searchRequest.isDescending() ? SortOrder.ASC : SortOrder.DESC ));

        //对分类和品牌进行聚合
        String categoryAggName = "categorys";
        String brandAggName = "brands";

        //创建分类桶
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));


        AggregatedPage<Goods> search= (AggregatedPage<Goods>) goodsRepository.search(queryBuilder.build());

        LongTerms categoryAgg= (LongTerms) search.getAggregation(categoryAggName);
        List<Category> categoryList=new ArrayList<>();
        categoryAgg.getBuckets().forEach(bucket -> {
           Long categoryId= (Long) bucket.getKey();
            //根据cid3查询分类名称
            Category category = categoryCilentServer.findCategoryByCid(categoryId);
            categoryList.add(category);
        });

        LongTerms brandAgg = (LongTerms) search.getAggregation(brandAggName);
        List<Brand> brandList=new ArrayList<>();
        brandAgg.getBuckets().forEach(bucket -> {
          Long brandId= (Long) bucket.getKey();

          //根据品牌id查询
            Brand brand = brandClientServer.findBrandById(brandId);
            brandList.add(brand);
        });

        //创建一个集合接收参数
        List<Map<String,Object>> paramList=new ArrayList<>();

        if(categoryList.size()==1) {
            //首先根据分类id查询所需要的参数
            List<SpecParam> specParams = specClientServer.findSpecParamsByCid1(categoryList.get(0).getId());

            specParams.forEach(param -> {
                String key = param.getName();
                System.out.println(key);
                queryBuilder.addAggregation(AggregationBuilders.terms(key).field("specs." + key + ".keyword"));
            });

            AggregatedPage<Goods> search1= (AggregatedPage<Goods>) goodsRepository.search(queryBuilder.build());

            //把桶转换为map集合
            Map<String, Aggregation> aggregationMap = search1.getAggregations().asMap();

            aggregationMap.keySet().forEach(mMap -> {
                if (!(mMap.equals(categoryAggName) || mMap.equals(brandAggName))) {
                    //转换数据类型
                    StringTerms aggregation = (StringTerms) aggregationMap.get(mMap);
                    //封装到map对象
                    Map<String, Object> map = new HashMap<>();
                    map.put("key", mMap);

                    List<Map<String, String>> list = new ArrayList<>();


                    aggregation.getBuckets().forEach(bucket -> {
                        Map<String, String> valueMap = new HashMap<>();
                        valueMap.put("name", bucket.getKeyAsString());
                        list.add(valueMap);   //对应属性没有id，所以只填写属性,封装到options 中为对象
                    });

                    map.put("options",list);
                    //加入到获取参数的list中
                    paramList.add(map);
                }
            });
        }





        //Page<Goods> page = goodsRepository.search(queryBuilder.build());
        //totalelement 总条数   totalpage  总页数  content  数据内容
        return new SearchResult(search.getTotalElements(), search.getContent(), search.getTotalPages(),categoryList,brandList,paramList);
        //return new PageResult<>(page.getTotalElements(), page.getTotalPages(), page.getContent());
    }
}
