
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