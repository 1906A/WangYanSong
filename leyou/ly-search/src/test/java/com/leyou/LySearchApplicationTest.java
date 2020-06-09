package com.leyou;

import com.leyou.client.SpuClientServer;
import com.leyou.common.PageResult;
import com.leyou.pojo.Goods;
import com.leyou.repository.GoodsRepository;
import com.leyou.service.SpuService;
import com.leyou.vo.SpuVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LySearchApplication.class)
public class LySearchApplicationTest {

    @Autowired
    SpuClientServer spuClientServer;

    @Autowired
    SpuService spuService;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    GoodsRepository goodsRepository;


    @Test
    public void findAll(){

        //创建索引
        elasticsearchTemplate.createIndex(Goods.class);

        //创建映射
        elasticsearchTemplate.putMapping(Goods.class);


        PageResult<SpuVo> spuList = spuClientServer.findAllSpu("", 2, 1, 200);

        spuList.getItems().forEach(spu->{

            System.out.println(spu.getId());
            try {
                Goods goods = spuService.convert(spu);
                goodsRepository.save(goods);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }
}
