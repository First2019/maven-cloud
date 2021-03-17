package design.first.user.entity;

import lombok.Data;

/**
    * 用户权限关联表
    */
@Data
public class UserPermssion {
    /**
    * 自增主键
    */
    private Integer upId;

    /**
    * 关联编号
    */
    private Long upNo;

    /**
    * 用户编号
    */
    private Long userNo;

    /**
    * 权限编号
    */
    private Long permNo;
}