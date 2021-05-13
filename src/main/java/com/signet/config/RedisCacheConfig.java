package com.signet.config;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

//@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {

  private RedisConnectionFactory redisConnectionFactory;

  public RedisCacheConfig(RedisConnectionFactory redisConnectionFactory) {
    this.redisConnectionFactory = redisConnectionFactory;
  }

  @Bean
  public CacheManager redisCacheManager() {
      RedisSerializationContext.SerializationPair<Object> jsonSerializer = 
        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer());
        return RedisCacheManager.RedisCacheManagerBuilder
              .fromConnectionFactory(redisConnectionFactory)
              .cacheDefaults(
                  RedisCacheConfiguration.defaultCacheConfig()
                          .entryTtl(Duration.ofDays(1))
                          .serializeValuesWith(jsonSerializer)
              )
              .build();
  }
    
}    

