package design.first.user.controller;

import design.first.user.base.BaseResult;
import design.first.user.entity.Permission;
import design.first.user.service.PermissionService;
import design.first.user.vo.ImPowerRequest;
import design.first.user.vo.InsertPermissionRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "权限管理")
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    @ApiOperation(value = "权限列表，游客查询")
    @GetMapping("/PermissionList")
    public BaseResult PermissionList(){
        List<Permission> list = permissionService.PermissionList(null);
        return BaseResult.success(list);
    }
    @ApiOperation(value = "新增权限")
    @PostMapping("/addPermission")
    @RequiresPermissions(value = "system:permission:add")
    public BaseResult addPermission(@RequestBody InsertPermissionRequest request){
        permissionService.insert(request);
        return BaseResult.success();
    }
    @ApiOperation(value = "用户授权")
    @PostMapping("/imPower")
    @RequiresPermissions(value = {"system:permission:imPower"},logical= Logical.AND)
    public BaseResult imPower(@RequestBody ImPowerRequest request){
        permissionService.imPower(request);
        return BaseResult.success();
    }

}
