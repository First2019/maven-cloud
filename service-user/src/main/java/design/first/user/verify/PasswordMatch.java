package design.first.user.verify;

import design.first.commons.tools.PasswordUtil;
import lombok.SneakyThrows;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * 自定义密码验证类
 */
public class PasswordMatch implements CredentialsMatcher {
    @SneakyThrows
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        SimpleAuthenticationInfo info=(SimpleAuthenticationInfo)authenticationInfo;
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        return PasswordUtil.pwdMatch(token.getPrincipal()+"",
                new String(token.getPassword()),
                info.getCredentials()+"",
                new String(info.getCredentialsSalt().getBytes()));
    }
}
