-- =====================================================
-- QStat - 查询区间模板表
-- =====================================================

-- 创建查询区间模板表
CREATE TABLE IF NOT EXISTS query_template (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '模板ID',
    template_name   VARCHAR(100)    NOT NULL COMMENT '模板名称',
    query_type      VARCHAR(20)     NOT NULL COMMENT '查询类型（birth-year/mileage/travel-time）',
    ranges_json     TEXT            NOT NULL COMMENT '区间配置JSON',
    gender          TINYINT         COMMENT '性别过滤（0-女，1-男）',
    description     VARCHAR(500)    COMMENT '模板描述',
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_name_type (template_name, query_type),
    KEY idx_query_type (query_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='查询区间模板表';
