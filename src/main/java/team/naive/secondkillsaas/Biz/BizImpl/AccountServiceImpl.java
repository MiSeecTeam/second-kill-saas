package team.naive.secondkillsaas.Biz.BizImpl;

import org.springframework.stereotype.Service;
import team.naive.secondkillsaas.Biz.AccountService;
import team.naive.secondkillsaas.VO.BriefUserVO;
import team.naive.secondkillsaas.VO.ResponseVO;
import team.naive.secondkillsaas.VO.UserForm;
import team.naive.secondkillsaas.VO.UserVO;

/**
 * @author wangyuxiao
 * @date 2020/10/19
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public ResponseVO registerAccount(UserForm userForm) {
        return null;
    }

    @Override
    public UserVO login(UserForm userForm) {
        return null;
    }

    @Override
    public ResponseVO changePassword(int userId, String password) {
        return null;
    }

    @Override
    public BriefUserVO getUser(int userId) {
        return null;
    }
}
