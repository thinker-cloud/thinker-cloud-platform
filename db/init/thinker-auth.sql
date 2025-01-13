/*
 Navicat Premium Dump SQL

 Source Server         : 阿里云服务器
 Source Server Type    : MySQL
 Source Server Version : 80036 (8.0.36)
 Source Host           : 120.77.206.150:13306
 Source Schema         : thinker-auth

 Target Server Type    : MySQL
 Target Server Version : 80036 (8.0.36)
 File Encoding         : 65001

 Date: 13/01/2025 16:09:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client`;
CREATE TABLE `oauth_client`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户端名称',
  `client_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户端id',
  `client_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户端秘钥',
  `client_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户端key',
  `enabled` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用 0.禁用 1.启用',
  `scopes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '授权范围',
  `methods` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '认证方式',
  `grant_types` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '授权类型',
  `redirect_uris` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '回调地址URL',
  `logout_redirect_uris` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登出回调地址',
  `expires_at` datetime NULL DEFAULT NULL COMMENT '客户端秘钥过期时间',
  `access_token_validity` int NULL DEFAULT NULL COMMENT '访问令牌有效期（秒）',
  `refresh_token_validity` int NULL DEFAULT NULL COMMENT '刷新令牌有效期（秒）',
  `is_encode` tinyint(1) NULL DEFAULT 1 COMMENT '前端密码加密',
  `is_captcha` tinyint(1) NULL DEFAULT 0 COMMENT '验证码开关',
  `ip_white_list` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IP白名单',
  `additional_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '附加信息',
  `auto_approve` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '自动授权',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注描述',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标识 0.未删除 1.已删除',
  `tenant_id` bigint NOT NULL COMMENT '租户id',
  `create_by` bigint NOT NULL COMMENT '创建人id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint NOT NULL COMMENT '修改人id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `cola_oauth_client_client_id_index`(`client_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'oauth2客户端配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client
-- ----------------------------
INSERT INTO `oauth_client` VALUES (1, '超级管理员租户', 'thinker', 'thinker', 'dGhpbmtlcjp0aGlua2Vy', 1, 'server', 'client_secret_basic', 'password,refresh_token,authorization_code,client_credentials', 'https://isexcuse.com', NULL, NULL, 604800, 604800, 1, 0, NULL, NULL, 'true', NULL, 0, 2, 1, '2024-11-04 14:57:31', 1, '2024-11-04 14:57:35');

-- ----------------------------
-- Table structure for oauth_client_social
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_social`;
CREATE TABLE `oauth_client_social`  (
  `id` bigint NOT NULL COMMENT '主键',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '应用类型',
  `app_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '应用id',
  `app_secret` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '应用密匙',
  `redirect_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '回调地址',
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户端id',
  `oauth_client_id` bigint NOT NULL COMMENT '认证客户端id',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注描述',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标识 0.未删除 1.已删除',
  `tenant_id` bigint NOT NULL COMMENT '租户id',
  `create_by` bigint NOT NULL COMMENT '创建人id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint NOT NULL COMMENT '修改人id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'oauth2客户端社交账号配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_social
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
