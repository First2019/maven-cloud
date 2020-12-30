package design.parent.overall.controller;

import design.parent.overall.dao.Test;
import design.parent.overall.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Api(value = "测试",tags = "测试用例")
@RestController
@RequestMapping("/api/test")
public class TestController {
    @Resource
    private Test test;

    @ApiOperation(value = "puttest",notes = "puttest测试说明2")
    @PutMapping(value = "/puttest")
    @ApiImplicitParam(value = "code",name = "code",dataType = "string",paramType = "query",defaultValue = "abc")
    public String puttest(String code){
        return code;
    }

    @ApiOperation(value = "deletetest",notes = "deletetest测试说明2")
    @DeleteMapping(value = "/deletetest")
    @ApiImplicitParam(value = "code",name = "code",dataType = "string",paramType = "query",example = "abcefg")
    public String deletetest(@RequestParam(value = "code") String code){
        return code;
    }

    @ApiOperation(value = "get",notes ="get")
    @GetMapping("/get")
    public String get(){
        return "hellow 访问到get了";
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录",notes = "登录")
    public String login(String name,String password,Boolean rememberMe){
        UsernamePasswordToken token=new UsernamePasswordToken(name,password,rememberMe);
        Subject subject= SecurityUtils.getSubject();
        try{
            subject.login(token);
            return "login-12";
        }catch (AuthenticationException e){
            String msg="验证错误";
            return msg;
        }
    }
    @GetMapping("/loginOut")
    @ApiOperation(value = "退出登录",notes = "退出登录")
    public String loginOut(){
        User user=new User();
        Subject subject = SecurityUtils.getSubject();
        Object obj = subject.getPrincipal();
        BeanUtils.copyProperties(obj,user);

        List<User> userList = test.getAll();
        return  userList.toString();
    }
    @RequiresPermissions(value = "system:user:view")
    @GetMapping("/userList")
    @ApiOperation(value = "取全部用户",notes = "用户数据")
    public String getUserList(){
        User user=new User();
        Subject subject = SecurityUtils.getSubject();
        Object obj = subject.getPrincipal();
        BeanUtils.copyProperties(obj,user);

        List<User> userList = test.getAll();
        return  userList.toString();
    }
}
