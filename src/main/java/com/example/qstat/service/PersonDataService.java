package com.example.qstat.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.qstat.dto.PersonDataQuery;
import com.example.qstat.entity.PersonData;
import com.example.qstat.mapper.PersonDataMapper;
import com.example.qstat.vo.PersonDataStatistics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 人员数据服务类
 *
 * @author QStat
 * @since 2024-08-10
 */
@Service
@RequiredArgsConstructor
public class PersonDataService extends ServiceImpl<PersonDataMapper, PersonData> {

    private final PersonDataMapper personDataMapper;

    /**
     * 分页查询数据
     *
     * @param query 查询条件
     * @return 分页结果
     */
    public IPage<PersonData> pageQuery(PersonDataQuery query) {
        Page<PersonData> page = new Page<>(query.getCurrent(), query.getSize());

        LambdaQueryWrapper<PersonData> wrapper = new LambdaQueryWrapper<>();

        // 人员ID查询
        if (query.getPersonIc() != null) {
            wrapper.eq(PersonData::getPersonIc, query.getPersonIc());
        }

        // 性别查询
        if (query.getGender() != null) {
            wrapper.eq(PersonData::getGender, query.getGender());
        }

        // 出生年份范围查询
        if (query.getMinBirthYear() != null) {
            wrapper.ge(PersonData::getBirthYear, query.getMinBirthYear());
        }
        if (query.getMaxBirthYear() != null) {
            wrapper.le(PersonData::getBirthYear, query.getMaxBirthYear());
        }

        // 旅行里程范围查询
        if (query.getMinMileage() != null) {
            wrapper.ge(PersonData::getTotalMileage, query.getMinMileage());
        }
        if (query.getMaxMileage() != null) {
            wrapper.le(PersonData::getTotalMileage, query.getMaxMileage());
        }

        // 旅行时间范围查询
        if (query.getMinTravelTime() != null) {
            wrapper.ge(PersonData::getTotalTravelTime, query.getMinTravelTime());
        }
        if (query.getMaxTravelTime() != null) {
            wrapper.le(PersonData::getTotalTravelTime, query.getMaxTravelTime());
        }

        IPage<PersonData> result = personDataMapper.selectPage(page, wrapper);

        // 设置性别描述和计算年龄
        result.getRecords().forEach(this::enrichPersonData);

        return result;
    }

    /**
     * 获取统计数据
     *
     * @return 统计结果
     */
    public PersonDataStatistics getStatistics() {
        PersonDataStatistics stats = new PersonDataStatistics();

        // 总统计
        Map<String, Object> totalStats = personDataMapper.totalStatistics();
        stats.setTotalCount(getLong(totalStats.get("count")));
        stats.setAvgMileage(getDouble(totalStats.get("avg_mileage")));
        stats.setAvgTravelTime(getDouble(totalStats.get("avg_travel_time")));
        stats.setTotalMileage(getLong(totalStats.get("total_mileage")));
        stats.setTotalTravelTime(getLong(totalStats.get("total_travel_time")));
        stats.setMaxMileage(getLong(totalStats.get("max_mileage")));
        stats.setMinMileage(getLong(totalStats.get("min_mileage")));
        stats.setMaxTravelTime(getLong(totalStats.get("max_travel_time")));
        stats.setMinTravelTime(getLong(totalStats.get("min_travel_time")));

        // 按性别统计
        List<PersonDataStatistics.GenderStatistics> genderStats = new ArrayList<>();

        Map<String, Object> maleStats = personDataMapper.statisticsByGender(1);
        if (maleStats != null && getLong(maleStats.get("count")) > 0) {
            PersonDataStatistics.GenderStatistics male = new PersonDataStatistics.GenderStatistics();
            male.setGender(1);
            male.setGenderDesc("男");
            male.setCount(getLong(maleStats.get("count")));
            male.setAvgMileage(getDouble(maleStats.get("avg_mileage")));
            male.setAvgTravelTime(getDouble(maleStats.get("avg_travel_time")));
            male.setTotalMileage(getLong(maleStats.get("total_mileage")));
            male.setTotalTravelTime(getLong(maleStats.get("total_travel_time")));
            genderStats.add(male);
            stats.setMaleCount(male.getCount());
        }

        Map<String, Object> femaleStats = personDataMapper.statisticsByGender(0);
        if (femaleStats != null && getLong(femaleStats.get("count")) > 0) {
            PersonDataStatistics.GenderStatistics female = new PersonDataStatistics.GenderStatistics();
            female.setGender(0);
            female.setGenderDesc("女");
            female.setCount(getLong(femaleStats.get("count")));
            female.setAvgMileage(getDouble(femaleStats.get("avg_mileage")));
            female.setAvgTravelTime(getDouble(femaleStats.get("avg_travel_time")));
            female.setTotalMileage(getLong(femaleStats.get("total_mileage")));
            female.setTotalTravelTime(getLong(femaleStats.get("total_travel_time")));
            genderStats.add(female);
            stats.setFemaleCount(female.getCount());
        }

        stats.setGenderStats(genderStats);

        // 按出生年份统计
        stats.setBirthYearStats(personDataMapper.statisticsByBirthYear());

        return stats;
    }

    /**
     * 获取所有出生年份
     *
     * @return 出生年份列表
     */
    public List<Integer> getAllBirthYears() {
        return personDataMapper.findAllBirthYears();
    }

    /**
     * 根据ID查询
     *
     * @param personIc 人员ID
     * @return 数据详情
     */
    public PersonData getByPersonIc(Long personIc) {
        PersonData data = personDataMapper.selectById(personIc);
        if (data != null) {
            enrichPersonData(data);
        }
        return data;
    }

    /**
     * 丰富人员数据（性别描述、年龄计算）
     */
    private void enrichPersonData(PersonData data) {
        // 设置性别描述
        if (data.getGender() != null) {
            data.setGenderDesc(data.getGender() == 1 ? "男" : "女");
        }

        // 计算年龄
        if (data.getBirthYear() != null) {
            data.setAge(Year.now().getValue() - data.getBirthYear());
        }
    }

    /**
     * 安全地获取 Long 值
     */
    private Long getLong(Object obj) {
        if (obj == null) {
            return 0L;
        }
        if (obj instanceof Long) {
            return (Long) obj;
        }
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        return 0L;
    }

    /**
     * 安全地获取 Double 值
     */
    private Double getDouble(Object obj) {
        if (obj == null) {
            return 0.0;
        }
        if (obj instanceof Double) {
            return (Double) obj;
        }
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        return 0.0;
    }
}
