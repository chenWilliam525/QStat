package com.example.qstat.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 人员数据统计结果
 *
 * @author QStat
 * @since 2024-08-10
 */
@Data
public class PersonDataStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总人数
     */
    private Long totalCount;

    /**
     * 男性人数
     */
    private Long maleCount;

    /**
     * 女性人数
     */
    private Long femaleCount;

    /**
     * 平均旅行里程
     */
    private Double avgMileage;

    /**
     * 平均旅行时间
     */
    private Double avgTravelTime;

    /**
     * 总旅行里程
     */
    private Long totalMileage;

    /**
     * 总旅行时间
     */
    private Long totalTravelTime;

    /**
     * 最大旅行里程
     */
    private Long maxMileage;

    /**
     * 最小旅行里程
     */
    private Long minMileage;

    /**
     * 最大旅行时间
     */
    private Long maxTravelTime;

    /**
     * 最小旅行时间
     */
    private Long minTravelTime;

    /**
     * 按性别统计
     */
    private List<GenderStatistics> genderStats;

    /**
     * 按出生年份统计
     */
    private List<Map<String, Object>> birthYearStats;

    @Data
    public static class GenderStatistics {
        private Integer gender;
        private String genderDesc;
        private Long count;
        private Double avgMileage;
        private Double avgTravelTime;
        private Long totalMileage;
        private Long totalTravelTime;
    }
}
