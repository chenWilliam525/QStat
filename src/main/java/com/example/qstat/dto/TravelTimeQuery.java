package com.example.qstat.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

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
     * 时间区间列表（支持多个区间，不允许重叠）
     */
    private List<TimeRange> timeRanges;

    /**
     * 性别（0-女，1-男）
     */
    private Integer gender;

    /**
     * 时间区间
     */
    @Data
    public static class TimeRange {
        /**
         * 最小时间（小时）
         */
        private Long minTravelTime;

        /**
         * 最大时间（小时）
         */
        private Long maxTravelTime;
    }
}
