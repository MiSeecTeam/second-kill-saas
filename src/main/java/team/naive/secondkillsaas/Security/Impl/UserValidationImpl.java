package team.naive.secondkillsaas.Security.Impl;

import org.springframework.stereotype.Service;
import team.naive.secondkillsaas.Security.UserValidation;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/25
 */
@Service
public class UserValidationImpl implements UserValidation {
    // 抢购是否被准许
    @Override
    public Boolean isKillValid(Long userId) {
        return Boolean.TRUE;
    }
}
