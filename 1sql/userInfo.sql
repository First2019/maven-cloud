CREATE TABLE user_info
(
    user_id            INT(11)                                                NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    user_no            BIGINT(20)                                             NOT NULL COMMENT '用户编号',
    user_login_account VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录账号',
    user_name          VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '用户名',
    user_pwd           VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '密码',
    user_pwd_salt      VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '密码盐值',
    last_login_time    DATETIME(0)                                            NULL     DEFAULT NULL COMMENT '上一次登录时间',
    is_delete          TINYINT(1)                                             NOT NULL DEFAULT 0 COMMENT '0未删除，1已删除',
    create_time        DATETIME(0)                                            NULL     DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (user_id) USING BTREE,
    INDEX idx_user_no (user_no) USING BTREE,
    INDEX idx_user_login_account (user_login_account) USING BTREE
) ENGINE = INNODB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8
  COLLATE utf8_general_ci COMMENT ='用户信息表'
  ROW_FORMAT = DYNAMIC