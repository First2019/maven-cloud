package design.first.user.handler;

import design.first.user.base.BaseResult;
import design.first.user.exception.SystemException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLException;

/**
 *统一异常处理
 */
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResult handleException(Exception e) {
        log.error("Exception:{}", e);
        String defaultMsg="系统内部异常，请联系管理员处理";
        BaseResult result=getExceptionMessage(e,defaultMsg);
        return result;
    }

    /**
     * 错误消息封装
     * @param e
     * @param msg
     * @return
     */
    private BaseResult getExceptionMessage(Exception e,String msg){
        //统一处理请求参数校验(实体对象传参)出错
        if(e instanceof BindException){
            msg=((BindException) e).getAllErrors().get(0).getDefaultMessage();
            return BaseResult.error(msg);
        }
        //自定义异常
        if(e instanceof SystemException){
            return BaseResult.error(e.getMessage());
        }
        //密码验证失败
        if(e instanceof IncorrectCredentialsException){
            return BaseResult.error("密码验证失败，"+e.getMessage());
        }
        //登录验证异常
        if(e instanceof AuthenticationException){
            return BaseResult.error("登录验证异常，"+e.getMessage());
        }
        //权限异常
        if(e instanceof AuthorizationException){
            //log.error("权限不足，{}", e.getMessage());
            return BaseResult.error("权限不足，"+e.getMessage());
        }
        if(e instanceof IOException){
            return BaseResult.error("IO异常:"+e.getMessage());
        }
        if(e instanceof SQLException){
            return BaseResult.error("SQL异常:"+e.getMessage());
        }
        return BaseResult.error(msg);
    }

}
