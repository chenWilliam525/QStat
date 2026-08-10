package com.example.qstat.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 人员数据查询条件
 *
 * @author QStat
 * @since 2024-08-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PersonDataQuery extends PageQuery {

    /**
     * 人员ID
     */
    private Long personIc;

    /**
     * 性别（0-女，1-男）
     */
    private Integer gender;

    /**
     * 出生年份（最小）
     */
    private Integer minBirthYear;

    /**
     * 出生年份（最大）
     */
    private Integer maxBirthYear;

    /**
     * 最小旅行里程
     */
    private Long minMileage;

    /**
     * 最大旅行里程
     */
    private Long maxMileage;

    /**
     * 最小旅行时间
     */
    private Long minTravelTime;

    /**
     * 最大旅行时间
     */
    private Long maxTravelTime;
}
