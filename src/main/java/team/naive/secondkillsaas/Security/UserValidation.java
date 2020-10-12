package team.naive.secondkillsaas.Security;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/25
 */
public interface UserValidation {
    // 抢购是否被准许
    public Boolean isKillValid(Long userId);
}
