package team.naive.secondkillsaas.Controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/10/19
 * @description:
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "redirect:/swagger-ui.html";
    }

    @GetMapping("/api")
    public String api() {
        return "redirect:/swagger-ui.html";
    }

}
