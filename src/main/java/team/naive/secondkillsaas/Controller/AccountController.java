package team.naive.secondkillsaas.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.naive.secondkillsaas.Biz.BizImpl.AccountServiceImpl;
import team.naive.secondkillsaas.Config.InterceptorConfiguration;
import team.naive.secondkillsaas.VO.BriefUserVO;
import team.naive.secondkillsaas.VO.ResponseVO;
import team.naive.secondkillsaas.VO.UserForm;
import team.naive.secondkillsaas.VO.UserVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author wangyuxiao
 * @date 2020/10/19
 */
@CrossOrigin
@RestController
public class AccountController {

    private final static String ACCOUNT_INFO_ERROR = "用户名或密码错误";

    @Autowired
    private AccountServiceImpl accountService;

    @PostMapping("/login")
    public ResponseVO login(@ModelAttribute  UserForm userForm, HttpSession session) {
        UserVO user = accountService.login(userForm);
        if (user == null) {
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        //注册session
        session.setAttribute(InterceptorConfiguration.SESSION_KEY, user);
        if (user.getRole() == 0) {
            session.setAttribute(InterceptorConfiguration.ISUSE, "true");
        } else if (user.getRole() == 2) {
            session.setAttribute(InterceptorConfiguration.ISADMIN, "true");
        }
        return ResponseVO.buildSuccess(user);
    }

    @PostMapping("/register")
    public ResponseVO registerAccount(@ModelAttribute UserForm userForm) {
        return accountService.registerAccount(userForm);
    }

    @PostMapping("/logout")
    public String logOut(HttpSession session) {
        session.removeAttribute(InterceptorConfiguration.SESSION_KEY);
        return "index";
    }

    @GetMapping("/getUser")
    public ResponseVO getUser(@RequestParam int userId, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        if (session == null) {
            return ResponseVO.buildFailure("失败，session已失效");
        } else {
            UserVO userVO = (UserVO) session.getAttribute(InterceptorConfiguration.SESSION_KEY);
            BriefUserVO user = accountService.getUser(userId);
            if (user == null) {
                return ResponseVO.buildFailure("失败，无效的用户id");
            }
            if (userVO == null) {
                return ResponseVO.buildFailure("失败，session用户信息无效");
            }
            if (userVO.getUsername().equals(user.getUsername())) {
                return ResponseVO.buildSuccess(user);
            } else {
                return ResponseVO.buildFailure("失败，请求信息不一致");
            }
        }
    }

}
