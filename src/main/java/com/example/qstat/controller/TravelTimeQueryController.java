package com.example.qstat.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.qstat.dto.TravelTimeQuery;
import com.example.qstat.entity.PersonData;
import com.example.qstat.service.TravelTimeQueryService;
import com.example.qstat.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 飞行时间查询控制器
 *
 * @author QStat
 * @since 2024-08-10
 */
@RestController
@RequestMapping("/query/travel-time")
@RequiredArgsConstructor
public class TravelTimeQueryController {

    private final TravelTimeQueryService travelTimeQueryService;

    /**
     * 按飞行时间分页查询
     *
     * @param query 查询条件
     * @return 分页结果
     */
    @PostMapping("/page")
    public Result<IPage<PersonData>> queryByPage(@RequestBody TravelTimeQuery query) {
        return Result.success(travelTimeQueryService.queryByPage(query));
    }

    /**
     * 按飞行时间统计
     *
     * @param query 查询条件
     * @return 统计结果
     */
    @PostMapping("/statistics")
    public Result<Map<String, Object>> statistics(@RequestBody TravelTimeQuery query) {
        return Result.success(travelTimeQueryService.statistics(query));
    }
}
