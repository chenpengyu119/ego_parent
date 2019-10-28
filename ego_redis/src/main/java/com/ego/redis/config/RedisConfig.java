package com.ego.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 创建Redis模板
 * @author pengyu
 * @date 2019/9/27 16:37.
 */
@Configuration
public class RedisConfig {

  @Bean
  public RedisTemplate<String,Object> getTemplate(RedisConnectionFactory factory){
      RedisTemplate<String,Object> template = new RedisTemplate<>();

      template.setKeySerializer(new StringRedisSerializer());
      template.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
      template.setConnectionFactory(factory);

      return template;
  }


}
