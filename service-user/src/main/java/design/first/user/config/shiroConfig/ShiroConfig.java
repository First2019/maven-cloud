package design.first.user.config.shiroConfig;

import design.first.user.verify.MyRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class ShiroConfig {

    @Value("${session.redis.expireTime}")
    private long expireTime;

    //从容器中拿去url配置规则
    @Autowired
    private ShiroProperties shiroProperties;

    //将自己的验证方式加入容器
    @Bean
    public MyRealm myShiroRealm() {
        MyRealm customRealm = new MyRealm();
        return customRealm;
    }


    //权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        securityManager.setRealm(myShiroRealm());
        //缓存管理
        //securityManager.setCacheManager(cacheManager());
        // 取消Cookie中的RememberMe参数
        //securityManager.setRememberMeManager(null);
        securityManager.setSessionManager(defaultWebSessionManager());
        //log.info("shiro会话保存时间:{}秒",expireTime);
        return securityManager;
    }

    public DefaultWebSessionManager defaultWebSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(expireTime*1000);//
        sessionManager.setDeleteInvalidSessions(true);
        //sessionManager.setSessionDAO(new ShiroSessionRedisDao());
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setDeleteInvalidSessions(true);
        /**
         * 修改Cookie中的SessionId的key，默认为JSESSIONID，自定义名称
         */
        sessionManager.setSessionIdCookie(new SimpleCookie("JSESSIONID"));
        return sessionManager;
    }
    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //以下注释部分，还可以将配置定义到yaml中
        Map<String, String> map = new HashMap<>();
        //登出
//        map.put("/logout", "logout");
//        //对所有用户认证
//        map.put("/**", "authc");//authc表示需要认证才可以访问,anon表示可以匿名访问
//        //表示可以匿名访问
//        map.put("/index2","anon"); //详细规则请见本目录下URL匹配规则.md文件
        //登录
        shiroFilterFactoryBean.setLoginUrl("/login");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        Map<String,String> filterMap=new HashMap<>();//shiroProperties.getFilterChainDefinitionMap();
        // 配置可以匿名访问的地址，可以根据实际情况自己添加，放行一些静态资源等，anon 表示放行
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/druid/**", "anon");
        filterMap.put("/sys/login", "anon");
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/doc.html", "anon");
        filterMap.put("/login/**", "anon");
        filterMap.put("/rsa/**", "anon");
        filterMap.put("/register", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/**", "authc");
        log.info("shiro放行:{},{},{}","/login/**","/rsa/**","/register");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 第一个是AdvisorAutoProxyCreator，代理生成器，需要借助SpringAOP来扫描@RequiresRoles
     * 和@RequiresPermissions等注解，生成代理类实现功能增强，从而实现权限控制。
     * 需要配合AuthorizationAttributeSourceAdvisor一起使用，否则权限注解无效。
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    /**
     * 上面配置的DefaultAdvisorAutoProxyCreator相当于一个切面，
     * 下面这个类就相当于切点了，两个一起才能实现注解权限控制
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

//    /**
//     * cookie对象
//     * @return
//     */
//    public SimpleCookie rememberMeCookie() {
//        // 设置cookie名称，对应login.html页面的<input type="checkbox" name="rememberMe"/>
//        SimpleCookie cookie = new SimpleCookie("rememberMe");
//        // 设置cookie的过期时间，单位为秒，这里为一天
//        cookie.setMaxAge(86400);
//        return cookie;
//    }
//
//    /**
//     * cookie管理对象
//     * @return
//     */
//    public CookieRememberMeManager rememberMeManager() {
//        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//        cookieRememberMeManager.setCookie(rememberMeCookie());
//        // rememberMe cookie加密的密钥
//        cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
//        return cookieRememberMeManager;
//    }

//    public RedisManager redisManager() {
//        RedisManager redisManager = new RedisManager();
//        return redisManager;
//    }
//
//    /**
//     * 不使用spring-redis包
//     * @return
//     */
//    public RedisCacheManager cacheManager() {
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        redisCacheManager.setRedisManager(redisManager());
//        return redisCacheManager;
//    }
}
