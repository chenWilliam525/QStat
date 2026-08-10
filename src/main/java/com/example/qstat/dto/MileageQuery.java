package com.example.qstat.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

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
     * 里程区间列表（支持多个区间，允许重叠）
     */
    private List<MileageRange> mileageRanges;

    /**
     * 性别（0-女，1-男）
     */
    private Integer gender;

    /**
     * 里程区间
     */
    @Data
    public static class MileageRange {
        /**
         * 最小里程（公里）
         */
        private Long minMileage;

        /**
         * 最大里程（公里）
         */
        private Long maxMileage;
    }
}
