package design.parent.overall.config;

import design.parent.overall.verify.MyRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    private static Logger logger= LoggerFactory.getLogger(ShiroConfig.class);
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
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
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
        // 配置可以匿名访问的地址，可以根据实际情况自己添加，放行一些静态资源等，anon 表示放行
        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroProperties.getFilterChainDefinitionMap());
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
}
