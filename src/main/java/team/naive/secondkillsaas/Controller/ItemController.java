package team.naive.secondkillsaas.Controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.naive.secondkillsaas.Biz.ItemService;
import team.naive.secondkillsaas.VO.ResponseVO;

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

}
