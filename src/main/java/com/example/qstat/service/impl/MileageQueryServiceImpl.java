package com.example.qstat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.qstat.dto.MileageQuery;
import com.example.qstat.entity.PersonData;
import com.example.qstat.mapper.PersonDataMapper;
import com.example.qstat.service.MileageQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 飞行里程查询服务实现
 *
 * @author QStat
 * @since 2024-08-10
 */
@Service
@RequiredArgsConstructor
public class MileageQueryServiceImpl extends ServiceImpl<PersonDataMapper, PersonData> implements MileageQueryService {

    private final PersonDataMapper personDataMapper;

    @Override
    public IPage<PersonData> queryByPage(MileageQuery query) {
        Page<PersonData> page = new Page<>(query.getCurrent(), query.getSize());

        // 构建查询条件
        QueryWrapper<PersonData> wrapper = buildQueryWrapper(query);

        IPage<PersonData> result = personDataMapper.selectPage(page, wrapper);

        // 丰富数据
        result.getRecords().forEach(this::enrichPersonData);

        return result;
    }

    @Override
    public Map<String, Object> statistics(MileageQuery query) {
        Map<String, Object> stats = new HashMap<>();

        // 构建查询条件
        QueryWrapper<PersonData> wrapper = buildQueryWrapper(query);

        // 查询符合条件的数据
        List<PersonData> dataList = personDataMapper.selectList(wrapper);

        if (CollectionUtils.isEmpty(dataList)) {
            stats.put("total", 0L);
            stats.put("genderStats", new ArrayList<>());
            stats.put("mileageRangeStats", new ArrayList<>());
            stats.put("mileageSummary", new HashMap<>());
            return stats;
        }

        // 总人数
        stats.put("total", (long) dataList.size());

        // 按性别统计
        List<Map<String, Object>> genderStats = new ArrayList<>();

        long maleCount = dataList.stream().filter(d -> d.getGender() != null && d.getGender() == 1).count();
        long femaleCount = dataList.stream().filter(d -> d.getGender() != null && d.getGender() == 0).count();

        if (maleCount > 0) {
            Map<String, Object> male = new HashMap<>();
            male.put("gender", 1);
            male.put("genderDesc", "男");
            male.put("count", maleCount);
            male.put("percentage", maleCount * 100.0 / dataList.size());
            genderStats.add(male);
        }

        if (femaleCount > 0) {
            Map<String, Object> female = new HashMap<>();
            female.put("gender", 0);
            female.put("genderDesc", "女");
            female.put("count", femaleCount);
            female.put("percentage", femaleCount * 100.0 / dataList.size());
            genderStats.add(female);
        }

        stats.put("genderStats", genderStats);

        // 按里程区间统计
        if (!CollectionUtils.isEmpty(query.getMileageRanges())) {
            List<Map<String, Object>> rangeStats = new ArrayList<>();

            for (int i = 0; i < query.getMileageRanges().size(); i++) {
                MileageQuery.MileageRange range = query.getMileageRanges().get(i);

                final long minMileage = range.getMinMileage() != null ? range.getMinMileage() : 0L;
                final long maxMileage = range.getMaxMileage() != null ? range.getMaxMileage() : Long.MAX_VALUE;

                long count = dataList.stream()
                        .filter(d -> d.getTotalMileage() != null)
                        .filter(d -> d.getTotalMileage() >= minMileage && d.getTotalMileage() <= maxMileage)
                        .count();

                Map<String, Object> rangeStat = new HashMap<>();
                rangeStat.put("rangeIndex", i + 1);
                rangeStat.put("rangeName", getRangeName(range, i + 1));
                rangeStat.put("minMileage", range.getMinMileage());
                rangeStat.put("maxMileage", range.getMaxMileage());
                rangeStat.put("count", count);
                rangeStat.put("percentage", dataList.size() > 0 ? count * 100.0 / dataList.size() : 0);

                rangeStats.add(rangeStat);
            }

            stats.put("mileageRangeStats", rangeStats);
        } else {
            stats.put("mileageRangeStats", new ArrayList<>());
        }

        // 总体里程统计
        LongSummaryStatistics mileageStats = dataList.stream()
                .filter(d -> d.getTotalMileage() != null)
                .mapToLong(PersonData::getTotalMileage)
                .summaryStatistics();

        Map<String, Object> mileageSummary = new HashMap<>();
        mileageSummary.put("avg", mileageStats.getAverage());
        mileageSummary.put("max", mileageStats.getMax());
        mileageSummary.put("min", mileageStats.getMin());
        mileageSummary.put("total", mileageStats.getSum());

        stats.put("mileageSummary", mileageSummary);

        return stats;
    }

    /**
     * 构建查询条件
     */
    private QueryWrapper<PersonData> buildQueryWrapper(MileageQuery query) {
        QueryWrapper<PersonData> wrapper = new QueryWrapper<>();

        // 性别筛选
        if (query.getGender() != null) {
            wrapper.eq("gender", query.getGender());
        }

        // 里程区间查询（支持多个区间，使用 OR 连接）
        if (!CollectionUtils.isEmpty(query.getMileageRanges())) {
            // 先添加性别条件后，再用嵌套OR条件处理里程区间
            if (query.getGender() == null) {
                // 没有性别筛选时，直接构建里程区间OR条件
                buildMileageRangeConditions(wrapper, query.getMileageRanges());
            } else {
                // 有性别筛选时，需要嵌套
                wrapper.and(w -> buildMileageRangeConditions(w, query.getMileageRanges()));
            }
        }

        return wrapper;
    }

    /**
     * 构建里程区间条件（OR连接）
     */
    private QueryWrapper<PersonData> buildMileageRangeConditions(QueryWrapper<PersonData> wrapper, List<MileageQuery.MileageRange> ranges) {
        boolean first = true;
        for (MileageQuery.MileageRange range : ranges) {
            if (range.getMinMileage() != null || range.getMaxMileage() != null) {
                if (first) {
                    applyRangeCondition(wrapper, range);
                    first = false;
                } else {
                    wrapper.or(sub -> applyRangeCondition(sub, range));
                }
            }
        }
        return wrapper;
    }

    /**
     * 应用单个区间条件
     */
    private void applyRangeCondition(QueryWrapper<PersonData> wrapper, MileageQuery.MileageRange range) {
        if (range.getMinMileage() != null && range.getMaxMileage() != null) {
            wrapper.between("total_mileage", range.getMinMileage(), range.getMaxMileage());
        } else if (range.getMinMileage() != null) {
            wrapper.ge("total_mileage", range.getMinMileage());
        } else if (range.getMaxMileage() != null) {
            wrapper.le("total_mileage", range.getMaxMileage());
        }
    }

    /**
     * 获取区间名称
     */
    private String getRangeName(MileageQuery.MileageRange range, int index) {
        if (range.getMinMileage() != null && range.getMaxMileage() != null) {
            return "区间" + index + " (" + formatNumber(range.getMinMileage()) + " - " + formatNumber(range.getMaxMileage()) + "公里)";
        } else if (range.getMinMileage() != null) {
            return "区间" + index + " (≥ " + formatNumber(range.getMinMileage()) + "公里)";
        } else {
            return "区间" + index + " (≤ " + formatNumber(range.getMaxMileage()) + "公里)";
        }
    }

    /**
     * 格式化数字
     */
    private String formatNumber(Long num) {
        if (num == null) return "-";
        return String.format("%,d", num);
    }

    /**
     * 丰富人员数据
     */
    private void enrichPersonData(PersonData data) {
        if (data.getGender() != null) {
            data.setGenderDesc(data.getGender() == 1 ? "男" : "女");
        }
        if (data.getBirthYear() != null) {
            data.setAge(java.time.Year.now().getValue() - data.getBirthYear());
        }
    }
}
