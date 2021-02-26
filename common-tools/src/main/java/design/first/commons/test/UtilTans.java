package design.first.commons.test;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UtilTans {
    @Test
    public void t0(){
        Bean2 b2=new Bean2();
        List<Bean2> list2=new ArrayList<>();
        List<Bean1> list1=new ArrayList<>();
        b2.setLean(true);
        b2.setTime(LocalDateTime.now());
        list2.add(b2);
        Bean1 b1=new Bean1();
        BeanUtils.copyProperties(list2,list1);
        System.out.println(list1);
    }
}
