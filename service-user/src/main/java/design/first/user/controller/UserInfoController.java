package design.first.user.controller;

import design.first.user.base.BaseResult;
import design.first.user.dao.UserInfoMapper;
import design.first.user.entity.UserInfo;
import design.first.user.service.UserInfoService;
import design.first.user.vo.UserInfoRegisterRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "用户信息管理")
public class UserInfoController {

    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserInfoService userInfoService;

    @RequiresPermissions(value = "system:user:view")
    @GetMapping("/userList")
    @ApiOperation(value = "取全部用户",notes = "用户数据")
    public BaseResult getUserList(){
        UserInfo user=new UserInfo();
        List<UserInfo> userList = userInfoMapper.selectListByCondition(null);
        return  BaseResult.success(userList);
    }

    @ApiOperation(value = "用户注册",notes = "用户注册")
    @PostMapping("/register")
    public BaseResult register(@RequestBody UserInfoRegisterRequest request){
        String account = userInfoService.register(request);
        return BaseResult.success((Object)account);
    }
}
