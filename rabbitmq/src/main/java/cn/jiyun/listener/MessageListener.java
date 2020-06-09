package cn.jiyun.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class MessageListener {
    //消费者  接受rabbitMq消息
    @RabbitListener(bindings = @QueueBinding(
            //队列-对应的是消息队列    durable指是否把rabbitMq的消息持久化
            value =@Queue(value = "item.text.queue",durable ="true" ),
            //交换机
            exchange =@Exchange(value ="item.text.exchange",ignoreDeclarationExceptions ="true",
                                type = ExchangeTypes.TOPIC),
            //过滤规格
            key ={"*.*"}
    ))
    public void message(String name){
        System.out.println("接受到的消息"+name);
    }
}
