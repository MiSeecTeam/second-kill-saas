package team.naive.secondkillsaas.Config.Redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
public class MasterRedisConfig extends RedisConfig {
    @Primary
    @Bean(name = "masterJedisConnectionFactory")
    @Override
    public JedisConnectionFactory getRedisConnFactory(@Qualifier("masterRedisProperties") RedisProperties redisProperties){
        return super.getRedisConnFactory(redisProperties);
    }

    @Bean(name = "masterRedisTemplate")
    @Override
    public RedisTemplate<Object, Object> buildRedisTemplate(@Qualifier("masterJedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        return super.buildRedisTemplate(redisConnectionFactory);
    }

    @Bean(name = "masterRedisProperties")
    @ConfigurationProperties(prefix = "spring.redis.master")
    public RedisProperties getBaseDBProperties() {
        return new RedisProperties();
    }
}
