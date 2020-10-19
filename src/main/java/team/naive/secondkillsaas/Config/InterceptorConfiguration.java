package team.naive.secondkillsaas.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import team.naive.secondkillsaas.Interceptor.SessionInterceptor;

/**
 * @author wangyuxiao
 * @date 2020/10/19
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    // 不同的角色设置不同的SessionKey
    public final static String SESSION_KEY = "user";
    public final static String ISADMIN= "isAdmin";
    public final static String ISUSE = "isUse";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new SessionInterceptor());
        //不登陆也能访问的界面不予拦截
        interceptorRegistration.excludePathPatterns(
                "/login", "/index", "/signUp", "/register", "/error");
        //其他界面都拦截，进入登陆角色判断逻辑
    }

}
