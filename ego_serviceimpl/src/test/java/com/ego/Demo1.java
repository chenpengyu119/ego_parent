package com.ego;

import com.ego.dubbo.conf.DemoConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author pengyu
 * @date 2019/10/9 19:31.
 */
@SpringBootTest(classes = ServiceApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class Demo1 {

    @Autowired
    private DemoConfig demoConfig;

    @Test
    public void demo1(){
        String en = demoConfig.getEn();
        System.out.println(en);
    }
}
