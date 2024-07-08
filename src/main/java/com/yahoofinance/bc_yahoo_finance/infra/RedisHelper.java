package com.yahoofinance.bc_yahoo_finance.infra;

import java.time.Duration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RedisHelper {
  // Encapsulate RestTemplate.class
  // 1. Remove Generics <String, String>
  // 2. remove OpsForValue()
  // 3. remove Initialize the object states (serializer)
  private RedisTemplate<String, Object> redisTemplate;
  private ObjectMapper objectMapper;

  public RedisHelper(RedisConnectionFactory factory,
      ObjectMapper objectMapper) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(factory);
    redisTemplate.setKeySerializer(RedisSerializer.string());
    redisTemplate.setValueSerializer(RedisSerializer.json());
    redisTemplate.afterPropertiesSet();
    this.redisTemplate = redisTemplate;
    this.objectMapper = objectMapper;
  }

  // Serialization
  public <T> void set(String key, T obj) throws JsonProcessingException {
    String json = this.objectMapper.writeValueAsString(obj);
    this.redisTemplate.opsForValue().set(key, json);
  }

  public <T> void set(String key, T obj, Duration duration)
      throws JsonProcessingException {
    this.redisTemplate.opsForValue().set(key, obj, duration);
  }

  // restTemplate.getForObject(url, User.class);
  // redisHelper.get("jph-user", User[].class);
  // Deserialization
  public <T> T get(String key, Class<T> clazz) throws JsonProcessingException {
    Object obj = this.redisTemplate.opsForValue().get(key);
    return (T) obj;
  }

  public void delete(String key) {
    redisTemplate.delete(key);
  }

}
