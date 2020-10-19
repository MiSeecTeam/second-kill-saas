package team.naive.secondkillsaas.Biz;

import team.naive.secondkillsaas.VO.BriefUserVO;
import team.naive.secondkillsaas.VO.ResponseVO;
import team.naive.secondkillsaas.VO.UserForm;
import team.naive.secondkillsaas.VO.UserVO;

/**
 * @author wangyuxiao
 * @date 2020/10/19
 */
public interface AccountService {

    /**
     * 注册账号
     */
    public ResponseVO registerAccount(UserForm userForm);

    /**
     * 用户登录，登录成功会将用户信息保存再session中
     */
    public UserVO login(UserForm userForm);

    /**
     * 用户改变密码
     */
    public ResponseVO changePassword(int userId,String password);

    /**
     * 得到用户
     */
    public BriefUserVO getUser(int userId);

}
