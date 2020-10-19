package team.naive.secondkillsaas.Interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import team.naive.secondkillsaas.Config.InterceptorConfiguration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author wangyuxiao
 * @date 2020/10/19
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        if (null != session && null != session.getAttribute(InterceptorConfiguration.SESSION_KEY)) {
            String url = httpServletRequest.getRequestURI();
            return true;
        }
        return false;
    }
}
