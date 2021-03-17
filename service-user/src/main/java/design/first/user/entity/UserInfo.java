package design.first.user.entity;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
    * 用户信息表
    */
@Data
public class UserInfo {

    private Integer userId;//自增主键
    private Long userNo;//用户编号
    private String userLoginAccount;//登录账号
    private String userName;//用户名
    private String userPwd;//密码
    private String userPwdSalt;//密码盐值
    private LocalDateTime lastLoginTime;//上一次登录时间
    private Boolean isDelete;//0未删除，1已删除
    private LocalDateTime createTime;//创建时间

}