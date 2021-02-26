package design.first.server01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

@SpringBootApplication
@EnableEurekaClient
public class ReidsServer01Application {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

//    @Resource(name="redisTemplate")
//    private RedisTemplate redisTemplate1;

    public static void main(String[] args) {
        SpringApplication.run(ReidsServer01Application.class, args);
    }

}
