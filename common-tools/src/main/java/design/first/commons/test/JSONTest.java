package design.first.commons.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Map;

public class JSONTest {
    @Test
    public void t0(){
        Bean1 b1=new Bean1();
        b1.setTime(LocalDateTime.now());
        System.out.println(JSON.toJSONString(b1));
        Map<String,Object> map= JSON.parseObject(JSON.toJSONString(b1),new TypeReference<Map<String,Object>>(){});
        System.out.println(JSON.toJSONString(map));
        map.put("str",map.get("time"));
        map.put("21",null);
        map.put("2e1",null);
        System.out.println(JSON.toJSONString(map));

    }
    @Test
    public void t1(){
        String s=null;
        String a="wd";
        System.out.println(a+s);
    }
}
