-- =====================================================
-- QStat - 人员数据表
-- =====================================================

-- 创建人员数据表
CREATE TABLE IF NOT EXISTS person_data (
    person_id       BIGINT          NOT NULL COMMENT '人员ID',
    gender          TINYINT         COMMENT '性别（0-女，1-男）',
    birth_year      INT             COMMENT '出生年份',
    total_mileage   BIGINT          COMMENT '总旅行里程（公里）',
    total_travel_time BIGINT        COMMENT '总旅行时间（小时）',
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (person_id),
    KEY idx_gender (gender),
    KEY idx_birth_year (birth_year),
    KEY idx_mileage (total_mileage),
    KEY idx_travel_time (total_travel_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人员数据表';

-- 插入测试数据
INSERT INTO person_data (person_id, gender, birth_year, total_mileage, total_travel_time) VALUES
(1, 1, 1990, 50000, 120),
(2, 0, 1995, 35000, 80),
(3, 1, 1988, 80000, 200),
(4, 0, 1992, 25000, 60),
(5, 1, 1985, 120000, 300),
(6, 0, 1998, 15000, 35),
(7, 1, 1991, 45000, 110),
(8, 0, 1994, 28000, 65),
(9, 1, 1989, 95000, 240),
(10, 0, 1996, 18000, 42),
(11, 1, 1993, 60000, 150),
(12, 0, 1997, 12000, 28),
(13, 1, 1986, 150000, 380),
(14, 0, 1999, 10000, 24),
(15, 1, 1987, 110000, 280),
(16, 0, 1993, 32000, 75),
(17, 1, 1990, 70000, 175),
(18, 0, 1995, 20000, 48),
(19, 1, 1988, 85000, 215),
(20, 0, 1994, 22000, 52);
