package team.naive.secondkillsaas.Biz.BizImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.naive.secondkillsaas.Biz.AccountService;
import team.naive.secondkillsaas.DO.secondkill.UserDO;
import team.naive.secondkillsaas.DO.secondkill.UserDOExample;
import team.naive.secondkillsaas.Mapper.AccountMapper;
import team.naive.secondkillsaas.VO.BriefUserVO;
import team.naive.secondkillsaas.VO.ResponseVO;
import team.naive.secondkillsaas.VO.UserForm;
import team.naive.secondkillsaas.VO.UserVO;

import java.util.List;

/**
 * @author wangyuxiao
 * @date 2020/10/19
 */
@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    @Autowired
    private AccountMapper accountMapper;


    @Override
    public ResponseVO registerAccount(UserForm userForm) {
        try {
            UserDO user = new UserDO(userForm.getUsername(), userForm.getPassword(), 0);
            accountMapper.insert(user);
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public UserVO login(UserForm userForm) {
        UserDOExample example = new UserDOExample();
        UserDOExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(userForm.getUsername());
        UserDO user = accountMapper.selectByExample(example).get(0);
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            System.out.println(userForm.getUsername());
            System.out.println(userForm.getPassword());
            System.out.println(user.getPassword());
            return null;
        }
        return new UserVO(user);
    }

    @Override
    public ResponseVO changePassword(int userId, String password) {
        UserDO user = new UserDO();
        user.setId(userId);
        user.setPassword(password);
        accountMapper.updateByPrimaryKeySelective(user);
        return ResponseVO.buildSuccess("修改成功！");
    }

    @Override
    public BriefUserVO getUser(int userId) {
        UserDOExample example = new UserDOExample();
        UserDOExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(userId);
        UserDO user=accountMapper.selectByExample(example).get(0);
        if(user==null) {
            return null;
        } else {
            BriefUserVO vo=new BriefUserVO();
            vo.setId(user.getId());
            vo.setUsername(user.getUsername());
            return vo;
        }
    }

}
