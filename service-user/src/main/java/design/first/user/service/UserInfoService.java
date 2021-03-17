package design.first.user.service;

import design.first.commons.tools.PasswordUtil;
import design.first.commons.tools.RandomUtil;
import design.first.user.dao.UserInfoMapper;
import design.first.user.entity.UserInfo;
import design.first.user.vo.UserInfoRegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
@Slf4j
public class UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * 用户注册，注册成功返回用户编号
     * @param request
     * @return
     */
    public String register(UserInfoRegisterRequest request){
        UserInfo info=new UserInfo();
        info.setCreateTime(LocalDateTime.now());
        info.setIsDelete(false);
        info.setUserName(request.getUserName());
        info.setUserPwdSalt(PasswordUtil.getSalt());
        info.setUserNo(RandomUtil.digitNo());
        info.setUserLoginAccount(RandomUtil.getLoginAccount());
        info.setUserPwd(PasswordUtil.encrypt(info.getUserLoginAccount(),request.getUserPwd(),info.getUserPwdSalt()));
        userInfoMapper.insert(info);
        return info.getUserLoginAccount();
    }

    public boolean updateByUserNo(UserInfo record){
        int count = userInfoMapper.updateByUserNo(record);
        if(count>0){
            return true;
        }
        return false;
    }

}
