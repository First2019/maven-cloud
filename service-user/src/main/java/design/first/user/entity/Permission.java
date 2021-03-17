package design.first.user.entity;

import lombok.Data;

/**
    * 用户信息表
    */
@Data
public class Permission {
    /**
    * 自增主键
    */
    private Integer permId;

    /**
    * 权限编号
    */
    private Long permNo;

    /**
    * 限制路径
    */
    private String permUrl;

    /**
    * 权限描述
    */
    private String permName;
}