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
public class Slave2RedisConfig extends RedisConfig {

    @Bean(name = "slave2JedisConnectionFactory")
    @Override
    public JedisConnectionFactory getRedisConnFactory(@Qualifier("slave2RedisProperties")RedisProperties redisProperties) {
        return super.getRedisConnFactory(redisProperties);
    }

    @Bean(name = "slave2RedisTemplate")
    @Override
    public RedisTemplate<Object, Object> buildRedisTemplate(@Qualifier("slave2JedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        return super.buildRedisTemplate(redisConnectionFactory);
    }

    @Bean(name = "slave2RedisProperties")
    @ConfigurationProperties(prefix = "spring.redis.slave2")
    public RedisProperties getBaseDBProperties() {
        return new RedisProperties();
    }
}
