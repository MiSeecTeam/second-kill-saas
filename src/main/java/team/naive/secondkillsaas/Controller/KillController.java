package team.naive.secondkillsaas.Controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.naive.secondkillsaas.Biz.ItemService;
import team.naive.secondkillsaas.Biz.KillService;
import team.naive.secondkillsaas.DTO.KillDTO;
import team.naive.secondkillsaas.Form.KillForm;
import team.naive.secondkillsaas.VO.ResponseVO;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/25
 */
@CrossOrigin
@RestController
@RequestMapping("/kill")
public class KillController {
    @Autowired
    private KillService killService;

    @PostMapping("/killItem")
    public ResponseVO killItem(@ModelAttribute KillForm killForm) {
        KillDTO killDTO = new KillDTO();
        killDTO.setTransactionId(killForm.getTransactionId());
        killDTO.setUserId(killForm.getUserId());
        killDTO.setSkuId(killForm.getSkuId());

        ResponseVO res = new ResponseVO();

        try {
            res = killService.killItem(killDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @GetMapping("/eztest")
    public ResponseVO eztest() {
        ResponseVO res = new ResponseVO();
        KillDTO killDTO = new KillDTO();
        killDTO.setSkuId(10001001L);
        killDTO.setUserId(1L);
        killDTO.setTransactionId(123L);

        try {
            res = killService.killItem(killDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }


}
