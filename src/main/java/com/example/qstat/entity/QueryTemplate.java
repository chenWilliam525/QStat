package com.example.qstat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询区间模板实体
 *
 * @author QStat
 * @since 2024-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("query_template")
public class QueryTemplate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 模板ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 查询类型（birth-year/mileage/travel-time）
     */
    private String queryType;

    /**
     * 区间配置JSON
     */
    private String rangesJson;

    /**
     * 性别过滤（0-女，1-男）
     */
    private Integer gender;

    /**
     * 模板描述
     */
    private String description;
}
