package com.leyou.listener;

import com.leyou.service.GoodsService;
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
    GoodsService goodsService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "item.edit.web.queue", durable = "true"),
            exchange = @Exchange(name = "item.exchanges", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"item.insert", "item.update"})
    )
    public void listenerCreateTemplate(Long spuId) throws Exception {

        System.out.println("开始监听" + spuId);
        if (spuId == null) {
            return;
        }

        goodsService.createHtml(spuId);

        System.out.println("监听后" + spuId);

    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "item.delete.web.queue", durable = "true"),
            exchange = @Exchange(name = "item.exchanges", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"item.delete"})
    )
    public void listenerDeleteTemplate(Long spuId) throws Exception {

        System.out.println("开始监听" + spuId);
        if (spuId == null) {
            return;
        }

        goodsService.deleteHtml(spuId);

        System.out.println("监听后" + spuId);

    }
}