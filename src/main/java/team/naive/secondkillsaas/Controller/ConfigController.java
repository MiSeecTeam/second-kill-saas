package team.naive.secondkillsaas.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.naive.secondkillsaas.Biz.RedisUpdateService;
import team.naive.secondkillsaas.Config.InterceptorConfiguration;
import team.naive.secondkillsaas.VO.ResponseVO;
import team.naive.secondkillsaas.VO.UserVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author WangYuxiao
 * @date 2020/11/6 21:39
 */
@CrossOrigin
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private RedisUpdateService redisUpdateService;

    @GetMapping("/refresh")
    public ResponseVO refresh(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        int role = ((UserVO)session.getAttribute(InterceptorConfiguration.SESSION_KEY)).getRole();
        if (role != 2) {
            return ResponseVO.buildFailure("没有权限");
        }
        int itemAmount = redisUpdateService.readAllItemDetailToRedis();
        int skuDetailAmount = redisUpdateService.readAllSkuDetailToRedis();
        int skuQuantityAmount = redisUpdateService.readAllSkuQuantityToRedis();
        ResponseVO responseVO = new ResponseVO();
        responseVO.setSuccess(true);
        responseVO.setMessage("更新了"+ itemAmount + "条商品，" + skuDetailAmount + "条sku信息" + skuQuantityAmount + "条sku抢购数。");
        return responseVO;
    }
}
