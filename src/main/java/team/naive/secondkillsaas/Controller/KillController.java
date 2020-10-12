package team.naive.secondkillsaas.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.naive.secondkillsaas.Biz.ItemService;
import team.naive.secondkillsaas.Biz.KillService;
import team.naive.secondkillsaas.DTO.KillDTO;
import team.naive.secondkillsaas.VO.ResponseVO;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/25
 */
@RestController
@RequestMapping("/kill")
public class KillController {
    @Autowired
    private KillService killService;

    @GetMapping("/eztest")
    public ResponseVO eztest(){
        ResponseVO res = new ResponseVO();
        KillDTO killDTO = new KillDTO();
        killDTO.setSkuId(10001001L);
        killDTO.setUserId(1L);
        killDTO.setTransactionId(123L);

        Boolean res1 = false;
        try {
            res1 = killService.killItem(killDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        res.setSuccess(true);
        res.setContent(res1);

        return res;
    }

}
