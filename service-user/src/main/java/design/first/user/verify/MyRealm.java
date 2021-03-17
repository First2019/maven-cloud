package design.first.user.verify;

import design.first.user.dao.PermissionMapper;
import design.first.user.dao.UserInfoMapper;
import design.first.user.entity.Permission;
import design.first.user.entity.UserInfo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {
    private static Logger logger= LoggerFactory.getLogger(MyRealm.class);

    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private PermissionMapper permissionMapper;
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("开始用户权限匹配");
        UserInfo userInfo = (UserInfo) principalCollection.getPrimaryPrincipal();
        // 权限Set集合
        Set<String> permsSet = new HashSet<>();
        // 角色Set集合
        Set<String> roles = new HashSet<>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //尝试从redis缓存取用户权限信息
//        try{
//
//        }
        // 系统管理员拥有最高权限
        if (userInfo.getUserLoginAccount().equals("admin")) {
            //perms = loginService.getAllPerms();
            permsSet.add("*:*:*");
            roles.add("admin");
        } else {
            //数据库查询并添加权限
            List<Permission> permList = permissionMapper.getPermByUserNo(userInfo.getUserNo());
            for(Permission perm:permList){
                permsSet.add(perm.getPermName());
            }
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
        UserInfo user=userInfoMapper.selectByAccount(token.getUsername());
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
            logger.info("数据库pwd:{},传入pwd:{}",user.getUserPwd(),token.getPassword());
            logger.info("getName():{}",getName());
            SimpleAuthenticationInfo simpleAuthenticationInfo= new SimpleAuthenticationInfo(user, user.getUserPwd(), getName());
            simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(user.getUserPwdSalt()));//加盐
            return simpleAuthenticationInfo;
        }
    }
    public MyRealm() {
        super(new PasswordMatch());//指定密码验证类
        //shiro自定义AuthenticationToken适应多认证条件
        //默认的UsernamePasswordToken就能满足需求
        //现有需求，不仅需要账号和密码，还需要附带一个组织id来校验用户信息，解决方案，自行重写token
        setAuthenticationTokenClass(UsernamePasswordToken.class);
        //FIXME: 暂时禁用Cache
        setCachingEnabled(false);
    }
}
