package team.naive.secondkillsaas.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @ Description
 * @ Author YangYicun
 * @ Date 2020/11/7 19:09
 */
@Configuration
public class RestTemplateConfig {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

//    @Bean
//    public ClientHttpRequestFactory clientHttpRequestFactory(){
//        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//        factory.setConnectTimeout(15000);
//        factory.setReadTimeout(5000);
//        return factory;
//    }


    @Bean
    public RestTemplate restTemplate(){
//        ClientHttpRequestFactory factory
//        return new RestTemplate(factory);
        return restTemplateBuilder.basicAuthentication("root", "root").build();    }

}
