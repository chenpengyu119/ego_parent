package com.ego.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pengyu
 * @date 2019/9/28 19:10.
 */
@Configuration
public class RabbitMqConfig {

    @Value("${custom.rabbitmq.queue.redis}")
    private String redisQueue;

    @Value("${custom.rabbitmq.queue.solr}")
    private String solrQueue;

    @Value("${custom.rabbitmq.queue.update}")
    private String updateQueue;

    @Value("${custom.rabbitmq.queue.createOrder}")
    private String orderCreateQueue;

    /**
     * 用来做redis的数据同步
     * @return
     */
    @Bean
    public Queue createQueue(){
        return new Queue(redisQueue);
    }

    /**
     * 用来做商品新增后solr数据的同步     * @return
     */
    @Bean
    public Queue createSolrQueue(){
        return new Queue(solrQueue);
    }

    @Bean
    public Queue createUpdateQueue(){
        return new Queue(updateQueue);
    }

    /**
     * 提交订单用的队列
     * @return
     */
    @Bean
    public Queue orderCreateQueue(){
        return new Queue(orderCreateQueue);
    }
}
