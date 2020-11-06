package team.naive.secondkillsaas.Config.Redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/11/5
 * @description:
 */
@Configuration
@EnableCaching
public class Slave1RedisConfig extends RedisConfig {

    @Bean(name = "slave1JedisConnectionFactory")
    @Override
    public JedisConnectionFactory getRedisConnFactory(@Qualifier("slave1RedisProperties")RedisProperties redisProperties) {
        return super.getRedisConnFactory(redisProperties);
    }

    @Bean(name = "slave1RedisTemplate")
    @Override
    public RedisTemplate<Object, Object> buildRedisTemplate(@Qualifier("slave1JedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        return super.buildRedisTemplate(redisConnectionFactory);
    }

    @Bean(name = "slave1RedisProperties")
    @ConfigurationProperties(prefix = "spring.redis.slave1")
    public RedisProperties getBaseDBProperties() {
        return new RedisProperties();
    }
}
