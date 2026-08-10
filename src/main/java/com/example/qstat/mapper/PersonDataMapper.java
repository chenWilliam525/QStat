package com.example.qstat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.qstat.entity.PersonData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 人员数据Mapper接口
 *
 * @author QStat
 * @since 2024-08-10
 */
@Mapper
public interface PersonDataMapper extends BaseMapper<PersonData> {

    /**
     * 根据性别统计人数和平均数据
     *
     * @param gender 性别
     * @return 统计结果
     */
    @Select("SELECT COUNT(*) as count, " +
            "AVG(total_mileage) as avg_mileage, " +
            "AVG(total_travel_time) as avg_travel_time, " +
            "SUM(total_mileage) as total_mileage, " +
            "SUM(total_travel_time) as total_travel_time " +
            "FROM person_data WHERE gender = #{gender}")
    Map<String, Object> statisticsByGender(@Param("gender") Integer gender);

    /**
     * 统计总数据
     *
     * @return 总统计数据
     */
    @Select("SELECT COUNT(*) as count, " +
            "AVG(total_mileage) as avg_mileage, " +
            "AVG(total_travel_time) as avg_travel_time, " +
            "SUM(total_mileage) as total_mileage, " +
            "SUM(total_travel_time) as total_travel_time, " +
            "MAX(total_mileage) as max_mileage, " +
            "MIN(total_mileage) as min_mileage, " +
            "MAX(total_travel_time) as max_travel_time, " +
            "MIN(total_travel_time) as min_travel_time " +
            "FROM person_data")
    Map<String, Object> totalStatistics();

    /**
     * 按出生年份分组统计
     *
     * @return 出生年份统计数据
     */
    @Select("SELECT birth_year, COUNT(*) as count, " +
            "AVG(total_mileage) as avg_mileage, " +
            "AVG(total_travel_time) as avg_travel_time " +
            "FROM person_data GROUP BY birth_year ORDER BY birth_year DESC")
    List<Map<String, Object>> statisticsByBirthYear();

    /**
     * 查询所有出生年份
     *
     * @return 出生年份列表
     */
    @Select("SELECT DISTINCT birth_year FROM person_data WHERE birth_year IS NOT NULL ORDER BY birth_year DESC")
    List<Integer> findAllBirthYears();
}
