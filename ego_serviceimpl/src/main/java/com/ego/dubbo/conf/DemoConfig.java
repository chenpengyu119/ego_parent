package com.ego.dubbo.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author pengyu
 * @date 2019/10/9 19:29.
 */
@Component
@PropertySource(value = "classpath:en.yml")
public class DemoConfig {

    @Value("${stopwords}")
    private String en;

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }
}
