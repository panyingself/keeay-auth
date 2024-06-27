
-- ----------------------------
-- Table structure for sys_ignore_interface_record
-- ----------------------------
DROP TABLE IF EXISTS `interface_white_list_info`;
CREATE TABLE `interface_white_list_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app` varchar(50) NULL DEFAULT NULL COMMENT '应用',
  `server` varchar(50) NULL DEFAULT NULL COMMENT '服务',
  `description` varchar(255) NULL DEFAULT NULL COMMENT '描述',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `interface_url` varchar(255)  NULL DEFAULT NULL COMMENT '忽略接口地址',
  `create_user` varchar(255) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` varchar(255) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_interface_url_idx`(`interface_url`) USING BTREE
) ENGINE = InnoDB  COMMENT '接口白名单';

-- ----------------------------
-- Records of sys_ignore_interface_record
-- ----------------------------
INSERT INTO `interface_white_list_info` VALUES (1,'数字营销平台','auth', 'C端登录接口','C端登录接口', '/api/auth/doCustomerLogin', 'py', '2023-10-06 17:10:21','py', '2024-02-22 11:08:46');
INSERT INTO `interface_white_list_info` ( `app`, `server`, `description`, `remark`, `interface_url`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES ('数字营销平台', 'auth', 'B端MFA', 'MFA二维码接口', '/keeay-auth/api/mfa/generate-qr', NULL, '2024-05-13 14:12:18', NULL, '2024-05-16 11:07:11');
INSERT INTO `interface_white_list_info` ( `app`, `server`, `description`, `remark`, `interface_url`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES ('数字营销平台', 'auth', 'B端MFA', 'MFA验证码接口', '/keeay-auth/api/mfa/verify-otp', NULL, '2024-05-13 14:12:18', NULL, '2024-05-16 11:07:11');

DROP TABLE IF EXISTS `mfa_user_relation_info`;
CREATE TABLE `mfa_user_relation_info`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `app_code` varchar(255)  NOT NULL DEFAULT '' COMMENT '应用code',
  `app_name` varchar(255)  NOT NULL DEFAULT '' COMMENT '应用名称',
  `user_code` varchar(255)  NOT NULL COMMENT '用户code',
  `mfa_secret` varchar(255)  NOT NULL COMMENT 'mfa secret',
  `mfa_text` varchar(255)  NOT NULL COMMENT 'qrCodeText',
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_user_code_idx`(`app_code` ASC, `user_code` ASC) USING BTREE COMMENT '联合唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;