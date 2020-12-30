package design.parent.overall.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.filter.DelegatingFilterProxy;

//@Configuration
public class SpringConfig {

//    @Bean(name="dataSource")
//    @Scope
//    public DruidDataSource dataSource(@Value("${jdbc.driver}")String driverClassName,
//                                      @Value("${jdbc.url}")String url,
//                                      @Value("${jdbc.username}") String username,
//                                      @Value("${jdbc.password}") String password){
//        DruidDataSource dataSource =new DruidDataSource();
//        dataSource.setDriverClassName(driverClassName);
//        dataSource.setUrl(url);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        dataSource.setDefaultAutoCommit(false);
//        return dataSource;
//    }
}
