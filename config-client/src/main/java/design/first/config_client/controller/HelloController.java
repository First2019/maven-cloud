package design.first.config_client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloController
 * @Desc TODO   读取 git 配置文件
 * @Date 2019/6/2 16:54
 * @Version 1.0
 */
@RestController
@RefreshScope//添加在需要刷新的配置文件上
public class HelloController {

    @Value("${hello}")
    private String hello;

    /*
     * @ClassName HelloController
     * @Desc TODO   读取 git 配置文件
     * @Date 2019/6/2 16:56
     * @Version 1.0
     */
    @GetMapping("/hello")
    public String hello() {
        return this.hello;
    }
    @GetMapping("/hello2")
    public String hello2() {
        return "this.hello中文乱码";
    }

}
