-- =====================================================
-- QStat 数据查询和统计系统 - 数据库初始化脚本
-- =====================================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS qstat DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE qstat;

-- =====================================================
-- 用户表示例
-- =====================================================
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    username        VARCHAR(50)     NOT NULL COMMENT '用户名',
    password        VARCHAR(100)    NOT NULL COMMENT '密码',
    nickname        VARCHAR(50)     COMMENT '昵称',
    email           VARCHAR(100)    COMMENT '邮箱',
    phone           VARCHAR(20)     COMMENT '手机号',
    status          TINYINT         NOT NULL DEFAULT 1 COMMENT '状态（0-禁用，1-启用）',
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted         TINYINT         NOT NULL DEFAULT 0 COMMENT '逻辑删除标记',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username),
    KEY idx_email (email),
    KEY idx_phone (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 插入测试数据
INSERT INTO sys_user (username, password, nickname, email, phone, status) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '管理员', 'admin@example.com', '13800138000', 1),
('user1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '测试用户1', 'user1@example.com', '13800138001', 1);

-- =====================================================
-- 其他表结构示例（根据实际业务需求创建）
-- =====================================================

-- 数据统计表
CREATE TABLE IF NOT EXISTS data_statistics (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    category        VARCHAR(50)     NOT NULL COMMENT '分类',
    value           DECIMAL(20,2)   NOT NULL COMMENT '数值',
    count           INT             NOT NULL DEFAULT 0 COMMENT '数量',
    stat_date       DATE            NOT NULL COMMENT '统计日期',
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted         TINYINT         NOT NULL DEFAULT 0 COMMENT '逻辑删除标记',
    PRIMARY KEY (id),
    KEY idx_category (category),
    KEY idx_stat_date (stat_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据统计表';
