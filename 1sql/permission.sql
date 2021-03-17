CREATE TABLE PERMISSION
(
    perm_id   INT(11)                                                 NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    perm_no   BIGINT(20)                                              NOT NULL COMMENT '权限编号',
    perm_url  VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '限制路径',
    perm_name VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限描述',
    PRIMARY KEY (perm_id) USING BTREE,
    INDEX idx_perm_no (perm_no) USING BTREE,
    INDEX idx_perm_name (perm_name) USING BTREE
) ENGINE = INNODB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8
  COLLATE utf8_general_ci COMMENT ='权限表'
  ROW_FORMAT = DYNAMIC;

CREATE TABLE user_permssion
(
    up_id   INT(11)    NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    up_no   BIGINT(20) NOT NULL COMMENT '关联编号',
    user_no BIGINT(20) NOT NULL COMMENT '用户编号',
    perm_no BIGINT(20) NOT NULL COMMENT '权限编号',
    PRIMARY KEY (up_id) USING BTREE
) ENGINE = INNODB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8
  COLLATE utf8_general_ci COMMENT ='用户权限关联表'
  ROW_FORMAT = DYNAMIC