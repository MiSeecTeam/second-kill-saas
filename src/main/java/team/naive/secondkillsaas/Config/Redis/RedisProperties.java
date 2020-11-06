package team.naive.secondkillsaas.Config.Redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/11/5
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisProperties {
    private Integer database;
    private String host;
    private Integer port;
    private String password;
    private Integer timeout;
    private Pool pool;

    @Data
    public static class Pool {
        private Integer maxActive;
        private Integer minIdle;
        private Integer maxIdle;
        private Integer maxWait;
    }
}
