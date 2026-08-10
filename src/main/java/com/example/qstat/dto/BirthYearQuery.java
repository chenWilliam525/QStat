package com.example.qstat.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 出生年份查询条件
 *
 * @author QStat
 * @since 2024-08-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BirthYearQuery extends PageQuery {

    /**
     * 年龄段区间列表
     */
    private List<AgeRange> ageRanges;

    /**
     * 性别（0-女，1-男）
     */
    private Integer gender;

    /**
     * 年龄段区间
     */
    @Data
    public static class AgeRange {
        /**
         * 最小年龄
         */
        private Integer minAge;

        /**
         * 最大年龄
         */
        private Integer maxAge;
    }
}
