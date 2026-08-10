package com.example.qstat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.qstat.dto.BirthYearQuery;
import com.example.qstat.entity.PersonData;
import com.example.qstat.mapper.PersonDataMapper;
import com.example.qstat.service.BirthYearQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 出生年份查询服务实现
 *
 * @author QStat
 * @since 2024-08-10
 */
@Service
@RequiredArgsConstructor
public class BirthYearQueryServiceImpl implements BirthYearQueryService {

    private final PersonDataMapper personDataMapper;

    @Override
    public IPage<PersonData> queryByPage(BirthYearQuery query) {
        Page<PersonData> page = new Page<>(query.getCurrent(), query.getSize());

        // 构建查询条件
        LambdaQueryWrapper<PersonData> wrapper = buildWrapper(query);

        // 分页查询
        IPage<PersonData> result = personDataMapper.selectPage(page, wrapper);

        // 丰富数据（添加性别描述和计算年龄）
        if (result.getRecords() != null) {
            result.getRecords().forEach(this::enrichPersonData);
        }

        return result;
    }

    @Override
    public Object statistics(BirthYearQuery query) {
        Map<String, Object> stats = new HashMap<>();

        // 构建查询条件
        LambdaQueryWrapper<PersonData> wrapper = buildWrapper(query);

        // 总人数
        Long totalCount = personDataMapper.selectCount(wrapper);
        stats.put("totalCount", totalCount);

        if (totalCount != null && totalCount > 0) {
            // 按性别统计
            List<Map<String, Object>> genderStats = new ArrayList<>();

            LambdaQueryWrapper<PersonData> maleWrapper = buildWrapper(query);
            maleWrapper.eq(PersonData::getGender, 1);
            Long maleCount = personDataMapper.selectCount(maleWrapper);
            if (maleCount != null && maleCount > 0) {
                Map<String, Object> male = new HashMap<>();
                male.put("gender", 1);
                male.put("genderDesc", "男");
                male.put("count", maleCount);
                genderStats.add(male);
            }

            LambdaQueryWrapper<PersonData> femaleWrapper = buildWrapper(query);
            femaleWrapper.eq(PersonData::getGender, 0);
            Long femaleCount = personDataMapper.selectCount(femaleWrapper);
            if (femaleCount != null && femaleCount > 0) {
                Map<String, Object> female = new HashMap<>();
                female.put("gender", 0);
                female.put("genderDesc", "女");
                female.put("count", femaleCount);
                genderStats.add(female);
            }

            stats.put("genderStats", genderStats);

            // 按年龄段统计
            List<Map<String, Object>> ageRangeStats = new ArrayList<>();
            if (!CollectionUtils.isEmpty(query.getAgeRanges())) {
                int currentYear = Year.now().getValue();
                for (BirthYearQuery.AgeRange ageRange : query.getAgeRanges()) {
                    LambdaQueryWrapper<PersonData> rangeWrapper = buildWrapperForAgeRange(ageRange, currentYear);
                    // 添加其他查询条件
                    if (query.getGender() != null) {
                        rangeWrapper.eq(PersonData::getGender, query.getGender());
                    }

                    Long count = personDataMapper.selectCount(rangeWrapper);
                    if (count != null && count > 0) {
                        Map<String, Object> rangeStat = new HashMap<>();
                        rangeStat.put("minAge", ageRange.getMinAge());
                        rangeStat.put("maxAge", ageRange.getMaxAge());
                        rangeStat.put("count", count);
                        ageRangeStats.add(rangeStat);
                    }
                }
            }
            stats.put("ageRangeStats", ageRangeStats);
        }

        return stats;
    }

    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<PersonData> buildWrapper(BirthYearQuery query) {
        LambdaQueryWrapper<PersonData> wrapper = new LambdaQueryWrapper<>();

        // 添加性别条件
        if (query.getGender() != null) {
            wrapper.eq(PersonData::getGender, query.getGender());
        }

        // 添加年龄段条件
        if (!CollectionUtils.isEmpty(query.getAgeRanges())) {
            int currentYear = Year.now().getValue();

            // 处理第一个年龄段
            boolean first = true;
            for (BirthYearQuery.AgeRange ageRange : query.getAgeRanges()) {
                if (ageRange.getMinAge() != null && ageRange.getMaxAge() != null) {
                    // 计算出生年份范围
                    int maxBirthYear = currentYear - ageRange.getMinAge();
                    int minBirthYear = currentYear - ageRange.getMaxAge();

                    if (first) {
                        wrapper.between(PersonData::getBirthYear, minBirthYear, maxBirthYear);
                        first = false;
                    } else {
                        wrapper.or(bw -> bw.between(PersonData::getBirthYear, minBirthYear, maxBirthYear));
                    }
                }
            }
        }

        return wrapper;
    }

    /**
     * 为指定年龄段构建查询条件
     */
    private LambdaQueryWrapper<PersonData> buildWrapperForAgeRange(BirthYearQuery.AgeRange ageRange, int currentYear) {
        LambdaQueryWrapper<PersonData> wrapper = new LambdaQueryWrapper<>();

        if (ageRange.getMinAge() != null && ageRange.getMaxAge() != null) {
            int maxBirthYear = currentYear - ageRange.getMinAge();
            int minBirthYear = currentYear - ageRange.getMaxAge();
            wrapper.between(PersonData::getBirthYear, minBirthYear, maxBirthYear);
        }

        return wrapper;
    }

    /**
     * 丰富人员数据
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
}
