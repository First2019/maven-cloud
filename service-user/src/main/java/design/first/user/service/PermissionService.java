package design.first.user.service;

import design.first.commons.tools.RandomUtil;
import design.first.user.dao.PermissionMapper;
import design.first.user.dao.UserPermssionMapper;
import design.first.user.entity.Permission;
import design.first.user.entity.UserPermssion;
import design.first.user.vo.ImPowerRequest;
import design.first.user.vo.InsertPermissionRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionService {
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private UserPermssionMapper userPermssionMapper;

    public int insert(InsertPermissionRequest request) {
        Permission perm = new Permission();
        BeanUtils.copyProperties(request, perm);
        perm.setPermNo(RandomUtil.digitNo());
        return permissionMapper.insertSelective(perm);
    }


    public List<Permission> PermissionList(String url) {
        Permission record =new Permission();
        record.setPermUrl(url);
        return permissionMapper.getByCondition(record);
    }
    public List<Permission> getPermByUserNo(Long userNo) {
        return permissionMapper.getPermByUserNo(userNo);
    }

    public void imPower(ImPowerRequest request){
        UserPermssion userPermssion=new UserPermssion();
        BeanUtils.copyProperties(request,userPermssion);
        userPermssion.setUpNo(RandomUtil.digitNo());
        userPermssionMapper.insert(userPermssion);
    }
}
