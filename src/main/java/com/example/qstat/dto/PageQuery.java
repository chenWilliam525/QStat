package com.example.qstat.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询基类
 *
 * @author QStat
 * @since 2024-08-10
 */
@Data
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    private Integer current = 1;

    /**
     * 每页条数
     */
    private Integer size = 10;

    /**
     * 排序字段
     */
    private String orderBy;

    /**
     * 是否升序
     */
    private Boolean isAsc = true;
}
