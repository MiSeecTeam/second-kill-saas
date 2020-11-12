package team.naive.secondkillsaas.Config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/11/5
 * @description:
 */

@Configuration
public class RedisConfig {
    //order
    @Value("${spring.redis.master.host}")
    private String masterHost;

    @Value("${spring.redis.master.port}")
    private String masterPort;

    @Value("${spring.redis.master.password}")
    private String masterPassword;

    //salve1
    @Value("${spring.redis.slave1.host}")
    private String slave1Host;

    @Value("${spring.redis.slave1.port}")
    private String slave1Port;

    @Value("${spring.redis.slave1.password}")
    private String slave1Password;

    //salve2
    @Value("${spring.redis.slave2.host}")
    private String slave2Host;

    @Value("${spring.redis.slave2.port}")
    private String slave2Port;

    @Value("${spring.redis.slave2.password}")
    private String slave2Password;

    private static final int MAX_IDLE = 200; //最大空闲连接数
    private static final int MAX_TOTAL = 1024; //最大连接数
    private static final long MAX_WAIT_MILLIS = 10000; //建立连接最长等待时间


    //配置工厂
    public RedisConnectionFactory connectionFactory(String host, int port, String password, int maxIdle,
                                                    int maxTotal, long maxWaitMillis, int index) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);

        if (!StringUtils.isEmpty(password)) {
            jedisConnectionFactory.setPassword(password);
        }

        if (index != 0) {
            jedisConnectionFactory.setDatabase(index);
        }

        jedisConnectionFactory.setPoolConfig(poolConfig(maxIdle, maxTotal, maxWaitMillis, false));
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }

    //连接池配置
    public JedisPoolConfig poolConfig(int maxIdle, int maxTotal, long maxWaitMillis, boolean testOnBorrow) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        poolConfig.setTestOnBorrow(testOnBorrow);
        return poolConfig;
    }


    //------------------------------------
    @Bean(name = "masterRedisTemplate")
    public RedisTemplate<String, Object> masterRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(
                connectionFactory(masterHost, Integer.parseInt(masterPort), masterPassword, MAX_IDLE, MAX_TOTAL, MAX_WAIT_MILLIS, 0));
        redisSerializeConfig(template);
        return template;
    }

    //------------------------------------
    @Bean(name = "slave1RedisTemplate")
    public RedisTemplate<String, Object> slave1RedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(
                connectionFactory(slave1Host, Integer.parseInt(slave1Port), slave1Password, MAX_IDLE, MAX_TOTAL, MAX_WAIT_MILLIS, 0));
        redisSerializeConfig(template);
        return template;
    }

    //------------------------------------
    @Bean(name = "slave2RedisTemplate")
    public RedisTemplate<String, Object> slave2RedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(
                connectionFactory(slave2Host, Integer.parseInt(slave2Port), slave2Password, MAX_IDLE, MAX_TOTAL, MAX_WAIT_MILLIS, 0));
        redisSerializeConfig(template);
        return template;
    }


    //序列化方法
    public void redisSerializeConfig(RedisTemplate<String, Object> template) {
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper om = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSeial.setObjectMapper(om);

        // 值采用json序列化
        template.setValueSerializer(jacksonSeial);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());

        // 设置hash key 和value序列化模式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jacksonSeial);
        template.afterPropertiesSet();
    }
}