package team.naive.secondkillsaas.Biz.BizImpl;

import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import team.naive.secondkillsaas.Biz.MQMonitorService;
import team.naive.secondkillsaas.VO.ResponseVO;

/**
 * @ Description
 * @ Author YangYicun
 * @ Date 2020/11/7 19:05
 */
@Service
public class MQMonitorServiceImpl implements MQMonitorService{

//    @Value("${spring.rabbitmq.host}")
//    private static String host;
//
//    private static String managePort = "15672";

    private static String URL = "http://120.55.46.206:15672/";

    @Autowired
    private RestTemplate restTemplate;

//    @Value("${spring.rabbitmq.username}")
//    private static String userName = "root";

//    @Value("${spring.rabbitmq.password}")
//    private static String password = "root";

    @Override
    public ResponseVO getMonitorStat(){
        String api = "api/overview";
        String query = URL + api;

//        //headers
//        String auth = userName + ":" + password;
//        byte[] base64MsgBytes = Base64.encodeBase64(auth.getBytes());
//        String base64Msg = new String(base64MsgBytes);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("authorization ", "Basic " + base64Msg);
//        headers.add("Content-Type","application/json");

        try {
//            HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
//            ResponseEntity<String> response = restTemplate.exchange(query, HttpMethod.GET, httpEntity, String.class);
            ResponseEntity<String> response = restTemplate.getForEntity(query, String.class);
            JSONObject responseJson = JSONObject.parseObject(response.getBody());

            JSONObject statJson = responseJson.getJSONObject("message_stats");
            statJson.put("queue_totals", responseJson.getJSONObject("queue_totals"));

            return ResponseVO.buildSuccess(statJson);
        }catch (RestClientException e){
            e.printStackTrace();
            return ResponseVO.buildFailure("获取监控信息失败");
        }
    }
}
