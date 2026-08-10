package com.example.qstat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 人员数据实体类
 *
 * @author QStat
 * @since 2024-08-10
 */
@Data
@TableName("person_data")
public class PersonData {

    private static final long serialVersionUID = 1L;

    /**
     * 人员ID（主键）
     */
    @TableId(type = IdType.INPUT)
    private Long personId;

    /**
     * 性别（0-女，1-男）
     */
    private Integer gender;

    /**
     * 出生年份
     */
    private Integer birthYear;

    /**
     * 总旅行里程
     */
    private Long totalMileage;

    /**
     * 总旅行时间
     */
    private Long totalTravelTime;

    // 以下为统计计算的派生字段（不存储在数据库中）

    /**
     * 年龄（根据出生年份计算）
     */
    private transient Integer age;

    /**
     * 性别描述
     */
    private transient String genderDesc;
}
