package com.leyou.listener;

import com.leyou.service.SpuService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoodsListener {

    @Autowired
    SpuService spuService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "item.edit.search.queue",durable = "true"),
            exchange = @Exchange(name = "item.exchanges",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"})
    )
    public void listenerCreate(Long spuId) throws Exception {

        System.out.println("开始监听"+spuId);
        if (spuId==null){
            return;
        }

          spuService.editGoods(spuId);

        System.out.println("监听后"+spuId);

    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "item.delete.search.queue",durable = "true"),
            exchange = @Exchange(name = "item.exchanges",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = {"item.delete"})
    )
    public void listenerDelete(Long spuId) throws Exception {

        System.out.println("开始监听"+spuId);
        if (spuId==null){
            return;
        }

        spuService.deleteGoods(spuId);

        System.out.println("监听后"+spuId);

    }

}
