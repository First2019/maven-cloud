package design.first.config_center01.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class Sender {
    @Resource
    private AmqpTemplate rabbitTemplate;

    /**
     * 注入AmqpTemplate，然后利用AmqpTemplate向一个名为hello的消息队列中发送消息。
     */
    public void send() {
        String msg = "hello rabbitmq:"+new Date();
        System.out.println("Sender:"+msg);
        this.rabbitTemplate.convertAndSend("hello", msg);
    }
}
