package team.naive.secondkillsaas.Controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team.naive.secondkillsaas.Biz.MQMonitorService;
import team.naive.secondkillsaas.VO.ResponseVO;

/**
 * @ Description
 * @ Author YangYicun
 * @ Date 2020/11/12 17:58
 */
@RestController
public class MQMonitorController {

    @Autowired
    private MQMonitorService mqMonitorService;

    @GetMapping("/mqMonitorStat")
    public ResponseVO getMQMonitorStat(){
        return mqMonitorService.getMonitorStat();
    }

}
