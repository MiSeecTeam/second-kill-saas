package team.naive.secondkillsaas.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import team.naive.secondkillsaas.Biz.RedisUpdateService;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/10/25
 * @description: 随系统运行而启动，用于更新redis
 */
@Component
public class RedisLoader implements CommandLineRunner {

    @Autowired
    RedisUpdateService redisUpdateService;

    @Override
    public void run(String... args) throws Exception {
        new Thread(this::loadRedis).start();
    }

    /**
     * 把三张表缓存到redis中，注意的是，只更新不删除
     */
    private void loadRedis(){
        System.out.println("开始更新redis缓存...");

        redisUpdateService.readAllItemDetailToRedis();

        redisUpdateService.readAllSkuDetailToRedis();

        redisUpdateService.readAllSkuQuantityToRedis();

        System.out.println("全部redis缓存已更新。");
    }
}
