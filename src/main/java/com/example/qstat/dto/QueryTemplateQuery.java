package com.example.qstat.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 模板查询条件
 *
 * @author QStat
 * @since 2024-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryTemplateQuery extends PageQuery {

    /**
     * 查询类型
     */
    private String queryType;

    /**
     * 模板名称（模糊查询）
     */
    private String templateName;
}
