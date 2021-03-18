package design.first.config_client.config;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 消息监听
 */
@Component
@RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
public class DirectReceiver {

    @RabbitHandler //表示process方法是用来处理接收到的消息的，我们这里收到消息后直接打印即可。
    public void process(Map msg) {
        System.out.println("Receiver:"+ JSON.toJSONString(msg));
    }
}
