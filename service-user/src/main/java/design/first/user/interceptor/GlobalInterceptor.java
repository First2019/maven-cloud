package design.first.user.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import design.first.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * 全局拦截器
 */
@Slf4j
public class GlobalInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Enumeration<String> headerNames = request.getHeaderNames();
        JSONObject headerMap=new JSONObject();
        String headerName;
        while(headerNames.hasMoreElements()){//获取请求头信息
            headerName=headerNames.nextElement();
            headerMap.put(headerName,request.getHeader(headerName));
        }
        log.info("\n\t\t(preHandle)前置拦截器:请求地址:{}\n\t\t请求类型:{}\n\t\t请求参数:{}\n\t\t请求Header:{}",
                request.getRequestURI(),request.getMethod(), JSON.toJSONString(request.getParameterMap()),headerMap);

//        String username;
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //如果用户已登录，获取登录用户信息
//        if (authentication.getPrincipal() != null && authentication.getPrincipal() instanceof User) {
//            username = ((User) (authentication.getPrincipal())).getUsername();
//        } else {
//            username = authentication.getName();
//        }
//        logger.info("=====> User({}) request({}) start, params:{}", username, request.getRequestURI(), JSONObject.toJSONString(request.getParameterMap()));
//        return super.preHandle(request, response, handler);
        return true;
    }

    /**
     * 请求完成之后，如果需要渲染视图，则此操作在渲染视图之前
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        Object responseBody=request.getAttribute("responseBodyFromAdviceXXX");
        Enumeration<String> headerNames = request.getHeaderNames();
        JSONObject headerMap=new JSONObject();
        String headerName;
        while(headerNames.hasMoreElements()){//获取请求头信息
            headerName=headerNames.nextElement();
            headerMap.put(headerName,request.getHeader(headerName));
        }
        log.info("\n\t\t后置拦截器:请求地址:{}\n\t\t请求类型:{}\n\t\t请求Header:{}\n\t\t返回结果:{}",
                request.getRequestURI(),request.getMethod(), headerMap,JSON.toJSONString(responseBody));

    }

}
