package com.example.qstat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.qstat.dto.TravelTimeQuery;
import com.example.qstat.entity.PersonData;
import com.example.qstat.mapper.PersonDataMapper;
import com.example.qstat.service.TravelTimeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 飞行时间查询服务实现
 *
 * @author QStat
 * @since 2024-08-10
 */
@Service
@RequiredArgsConstructor
public class TravelTimeQueryServiceImpl extends ServiceImpl<PersonDataMapper, PersonData> implements TravelTimeQueryService {

    private final PersonDataMapper personDataMapper;

    @Override
    public IPage<PersonData> queryByPage(TravelTimeQuery query) {
        Page<PersonData> page = new Page<>(query.getCurrent(), query.getSize());

        // 构建查询条件
        QueryWrapper<PersonData> wrapper = buildQueryWrapper(query);

        IPage<PersonData> result = personDataMapper.selectPage(page, wrapper);

        // 丰富数据
        result.getRecords().forEach(this::enrichPersonData);

        return result;
    }

    @Override
    public Map<String, Object> statistics(TravelTimeQuery query) {
        Map<String, Object> stats = new HashMap<>();

        // 构建查询条件
        QueryWrapper<PersonData> wrapper = buildQueryWrapper(query);

        // 查询符合条件的数据
        List<PersonData> dataList = personDataMapper.selectList(wrapper);

        if (CollectionUtils.isEmpty(dataList)) {
            stats.put("total", 0L);
            stats.put("genderStats", new ArrayList<>());
            stats.put("timeRangeStats", new ArrayList<>());
            stats.put("timeSeriesData", new ArrayList<>());
            stats.put("timeSummary", new HashMap<>());
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

        // 按时间区间统计
        if (!CollectionUtils.isEmpty(query.getTimeRanges())) {
            List<Map<String, Object>> rangeStats = new ArrayList<>();

            for (int i = 0; i < query.getTimeRanges().size(); i++) {
                TravelTimeQuery.TimeRange range = query.getTimeRanges().get(i);

                final long minTime = range.getMinTravelTime() != null ? range.getMinTravelTime() : 0L;
                final long maxTime = range.getMaxTravelTime() != null ? range.getMaxTravelTime() : Long.MAX_VALUE;

                long count = dataList.stream()
                        .filter(d -> d.getTotalTravelTime() != null)
                        .filter(d -> d.getTotalTravelTime() >= minTime && d.getTotalTravelTime() <= maxTime)
                        .count();

                Map<String, Object> rangeStat = new HashMap<>();
                rangeStat.put("rangeIndex", i + 1);
                rangeStat.put("rangeName", getRangeName(range, i + 1));
                rangeStat.put("minTravelTime", range.getMinTravelTime());
                rangeStat.put("maxTravelTime", range.getMaxTravelTime());
                rangeStat.put("count", count);
                rangeStat.put("percentage", dataList.size() > 0 ? count * 100.0 / dataList.size() : 0);

                rangeStats.add(rangeStat);
            }

            stats.put("timeRangeStats", rangeStats);
        } else {
            stats.put("timeRangeStats", new ArrayList<>());
        }

        // 时间序列数据（用于折线图）
        List<Map<String, Object>> timeSeriesData = dataList.stream()
                .filter(d -> d.getTotalTravelTime() != null)
                .sorted(Comparator.comparing(PersonData::getTotalTravelTime))
                .limit(100) // 限制数据点数量，避免图表过于密集
                .map(d -> {
                    Map<String, Object> point = new HashMap<>();
                    point.put("personId", d.getPersonId());
                    point.put("travelTime", d.getTotalTravelTime());
                    point.put("mileage", d.getTotalMileage());
                    point.put("gender", d.getGender());
                    point.put("genderDesc", d.getGender() != null ? (d.getGender() == 1 ? "男" : "女") : "-");
                    return point;
                })
                .collect(Collectors.toList());

        stats.put("timeSeriesData", timeSeriesData);

        // 总体时间统计
        LongSummaryStatistics timeStats = dataList.stream()
                .filter(d -> d.getTotalTravelTime() != null)
                .mapToLong(PersonData::getTotalTravelTime)
                .summaryStatistics();

        Map<String, Object> timeSummary = new HashMap<>();
        timeSummary.put("avg", timeStats.getAverage());
        timeSummary.put("max", timeStats.getMax());
        timeSummary.put("min", timeStats.getMin());
        timeSummary.put("total", timeStats.getSum());

        stats.put("timeSummary", timeSummary);

        return stats;
    }

    /**
     * 构建查询条件
     */
    private QueryWrapper<PersonData> buildQueryWrapper(TravelTimeQuery query) {
        QueryWrapper<PersonData> wrapper = new QueryWrapper<>();

        // 性别筛选
        if (query.getGender() != null) {
            wrapper.eq("gender", query.getGender());
        }

        // 时间区间查询（支持多个区间，使用 OR 连接）
        if (!CollectionUtils.isEmpty(query.getTimeRanges())) {
            if (query.getGender() == null) {
                // 没有性别筛选时，直接构建时间区间OR条件
                buildTimeRangeConditions(wrapper, query.getTimeRanges());
            } else {
                // 有性别筛选时，需要嵌套
                wrapper.and(w -> buildTimeRangeConditions(w, query.getTimeRanges()));
            }
        }

        return wrapper;
    }

    /**
     * 构建时间区间条件（OR连接）
     */
    private QueryWrapper<PersonData> buildTimeRangeConditions(QueryWrapper<PersonData> wrapper, List<TravelTimeQuery.TimeRange> ranges) {
        boolean first = true;
        for (TravelTimeQuery.TimeRange range : ranges) {
            if (range.getMinTravelTime() != null || range.getMaxTravelTime() != null) {
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
    private void applyRangeCondition(QueryWrapper<PersonData> wrapper, TravelTimeQuery.TimeRange range) {
        if (range.getMinTravelTime() != null && range.getMaxTravelTime() != null) {
            wrapper.between("total_travel_time", range.getMinTravelTime(), range.getMaxTravelTime());
        } else if (range.getMinTravelTime() != null) {
            wrapper.ge("total_travel_time", range.getMinTravelTime());
        } else if (range.getMaxTravelTime() != null) {
            wrapper.le("total_travel_time", range.getMaxTravelTime());
        }
    }

    /**
     * 获取区间名称
     */
    private String getRangeName(TravelTimeQuery.TimeRange range, int index) {
        if (range.getMinTravelTime() != null && range.getMaxTravelTime() != null) {
            return "区间" + index + " (" + range.getMinTravelTime() + " - " + range.getMaxTravelTime() + "小时)";
        } else if (range.getMinTravelTime() != null) {
            return "区间" + index + " (≥ " + range.getMinTravelTime() + "小时)";
        } else {
            return "区间" + index + " (≤ " + range.getMaxTravelTime() + "小时)";
        }
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
