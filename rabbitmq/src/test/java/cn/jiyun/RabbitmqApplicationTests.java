package cn.jiyun;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqApplicationTests {
    @Autowired
    AmqpTemplate amqpTemplate;


    @Test
    public void contextLoads() {
        //1.交换机
        //2.routingKey
        //3.发送消息体
        amqpTemplate.convertAndSend("item.text.exchange","a.b","222222");
    }

}
