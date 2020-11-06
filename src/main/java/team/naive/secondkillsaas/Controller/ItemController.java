package team.naive.secondkillsaas.Controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.naive.secondkillsaas.Biz.ItemService;
import team.naive.secondkillsaas.Config.InterceptorConfiguration;
import team.naive.secondkillsaas.VO.ItemVO;
import team.naive.secondkillsaas.VO.ResponseVO;
import team.naive.secondkillsaas.VO.SkuDetailVO;
import team.naive.secondkillsaas.VO.UserVO;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/22
 */

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/welcome")
    public ResponseVO welcome(){
        ResponseVO res = new ResponseVO();
        res.setSuccess(true);
        res.setMessage("welcome");
        return res;
    }

    @GetMapping("/listItems")
    public ResponseVO listItems(){
        ResponseVO res = new ResponseVO();
        res.setSuccess(true);
        res.setContent(itemService.listItemDetail());
        return res;
    }

    @PostMapping("/getItem")
    public ResponseVO getItem(@RequestParam Long itemId){
        ResponseVO res = new ResponseVO();
        res.setSuccess(true);
        res.setContent(itemService.getItemDetail(itemId));
        return res;
    }

    @PostMapping("/deleteItem")
    public ResponseVO deleteItem(@RequestParam Long itemId){
        return itemService.deleteItem(itemId);
    }

    /**
     * 获得
     * 1、某商品的sku列表。
     * 2、价格最低到最高
     * 3、秒杀起止时间
     * @return
     */
    @GetMapping("/sku/all")
    public ResponseVO getItemSkuDetail(@RequestParam("itemId") Long itemId){
        ResponseVO res = new ResponseVO();
        res.setSuccess(true);
        res.setContent(itemService.getItemSkuDetailByItemId(itemId));
        return res;
    }

    @GetMapping("/all")
    public ResponseVO getAllItemSkuDetail(HttpSession session){
        int role = ((UserVO)session.getAttribute(InterceptorConfiguration.SESSION_KEY)).getRole();
        if (role != 2) {
            return ResponseVO.buildFailure("没有权限");
        }
        return itemService.getAllItemSku();
    }

    @PostMapping("/addItem")
    public ResponseVO addItem(@ModelAttribute ItemVO itemVO, HttpSession session){
        int role = ((UserVO)session.getAttribute(InterceptorConfiguration.SESSION_KEY)).getRole();
        if (role != 2) {
            return ResponseVO.buildFailure("没有权限");
        }
        return itemService.addItem(itemVO);
    }

    @PostMapping("/addSku")
    public ResponseVO addSku(@ModelAttribute SkuDetailVO skuDetailVO, HttpSession session){
        int role = ((UserVO)session.getAttribute(InterceptorConfiguration.SESSION_KEY)).getRole();
        if (role != 2) {
            return ResponseVO.buildFailure("没有权限");
        }
        return itemService.addSku(skuDetailVO);
    }

    @PostMapping("/modifySku")
    public ResponseVO modifySku(@RequestParam Long skuId, @RequestParam Long amount, HttpSession session){
        int role = ((UserVO)session.getAttribute(InterceptorConfiguration.SESSION_KEY)).getRole();
        if (role != 2) {
            return ResponseVO.buildFailure("没有权限");
        }
        return itemService.updateSku(skuId, amount);
    }

}
