package com.ego.rabbitmq.send;

import com.ego.commons.pojo.OrderParam;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author pengyu
 * @date 2019/9/28 19:15.
 */
@Component
public class Sender {

    @Value("${custom.rabbitmq.queue.redis}")
    private String redisQueue;

    @Value("${custom.rabbitmq.queue.solr}")
    private String solrQueue;

    @Value("${custom.rabbitmq.queue.update}")
    private String updateQueue;

    @Value("${custom.rabbitmq.queue.createOrder}")
    private String orderCreateQueue;

    @Autowired
    AmqpTemplate template;

    public void send(String key){
        template.convertAndSend(redisQueue, key);
    }

    public void sendSolr(Long msg){
        template.convertAndSend(solrQueue, msg);
    }

    public void sendUpdate(Long id){
        template.convertAndSend(updateQueue, id);
    }

    /**
     * 发送创建订单的消息
     */
    public void sendOrderCreate(OrderParam op){
        template.convertAndSend(orderCreateQueue, op);
    }
}
