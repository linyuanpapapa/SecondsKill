package com.yuan.SecondsKill.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class JedisPoolFactory {
    @Autowired
    RedisConfig redisConfig;

    /**
     * 读取配置文件并且生成Jedis连接池
     * @return
     */
    @Bean
    public JedisPool JedisPoolFactory(){
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait()*1000);
        JedisPool jp=new JedisPool(poolConfig,redisConfig.getHost()
                ,redisConfig.getPort(),redisConfig.getTimeout()*1000
                ,redisConfig.getPassword(),0);
        return jp;
    }
}
