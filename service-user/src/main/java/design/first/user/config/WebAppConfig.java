package design.first.user.config;

import design.first.user.interceptor.GlobalInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@EnableWebMvc //需要全面接管SpringBoot中的SpringMVC配置时开启此注解，开启后SpringMVC自动配置失效。
@Component
@Slf4j
public class WebAppConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("添加自定义拦截器：GlobalInterceptor,拦截所有请求");
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new GlobalInterceptor());
        interceptorRegistration.addPathPatterns("/**");
        interceptorRegistration.excludePathPatterns("/swagger-resources/**","/webjars/**","/doc.html","/error");
    }
}
