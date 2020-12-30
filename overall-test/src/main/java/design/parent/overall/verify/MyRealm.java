package design.parent.overall.verify;

import design.parent.overall.OverallApplication;
import design.parent.overall.dao.Test;
import design.parent.overall.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

public class MyRealm extends AuthorizingRealm {
    private static Logger logger= LoggerFactory.getLogger(MyRealm.class);
    String salt = UUID.randomUUID().toString().replaceAll("-","");

    @Resource
    private Test test;
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();
        List<String> perms;
        // 权限Set集合
        Set<String> permsSet = new HashSet<>();
        // 角色Set集合
        Set<String> roles = new HashSet<>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 系统管理员拥有最高权限
        if (user.getName().equals("admin")) {
            //perms = loginService.getAllPerms();
            permsSet.add("*:*:*");
            roles.add("admin");
        } else {
            permsSet.add("*:*:*");
            roles.add("admin");
        }
        // 返回权限
        info.setRoles(roles);
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 登录验证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("shiro登录认证");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user=test.getUser(token.getUsername());
        //new Md5Hash("123456",salt, 2);//加盐
        if (user == null) {
            // 用户名不存在
            // 参数一： 期望登录后，保存在Subject中信息
            // 参数二： 如果返回为null 说明用户不存在，报用户名
            // 参数三 ：realm名称
            return null;
        } else {
            // 用户名存在
            // 当返回用户密码时，securityManager安全管理器，自动比较返回密码和用户输入密码是否一致
            // 如果密码一致 登录成功， 如果密码不一致 报密码错误异常
            logger.info("数据库pwd:{},传入pwd:{}",user.getPwd(),token.getPassword());
            SimpleAuthenticationInfo simpleAuthenticationInfo= new SimpleAuthenticationInfo(user, user.getPwd(), getName());
            //simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(salt));//加盐
            return simpleAuthenticationInfo;
        }
    }
    public MyRealm() {
        //super(new AllowAllCredentialsMatcher());//指定密码验证类
        setAuthenticationTokenClass(UsernamePasswordToken.class);
        //FIXME: 暂时禁用Cache
        setCachingEnabled(false);
    }
}
