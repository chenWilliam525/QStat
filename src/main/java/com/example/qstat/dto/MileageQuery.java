package com.example.qstat.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 飞行里程查询条件
 *
 * @author QStat
 * @since 2024-08-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MileageQuery extends PageQuery {

    /**
     * 最小飞行里程
     */
    private Long minMileage;

    /**
     * 最大飞行里程
     */
    private Long maxMileage;

    /**
     * 性别（0-女，1-男）
     */
    private Integer gender;
}
