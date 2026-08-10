package com.example.qstat.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 飞行时间查询条件
 *
 * @author QStat
 * @since 2024-08-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TravelTimeQuery extends PageQuery {

    /**
     * 最小飞行时间
     */
    private Long minTravelTime;

    /**
     * 最大飞行时间
     */
    private Long maxTravelTime;

    /**
     * 性别（0-女，1-男）
     */
    private Integer gender;
}
