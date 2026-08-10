package com.example.qstat.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.qstat.dto.TravelTimeQuery;
import com.example.qstat.entity.PersonData;
import com.example.qstat.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    // TODO: 注入 Service
    // private final TravelTimeQueryService travelTimeQueryService;

    /**
     * 按飞行时间分页查询
     *
     * @param query 查询条件
     * @return 分页结果
     */
    @PostMapping("/page")
    public Result<IPage<PersonData>> queryByPage(@RequestBody TravelTimeQuery query) {
        // TODO: 实现查询逻辑
        return Result.success();
    }

    /**
     * 按飞行时间统计
     *
     * @param query 查询条件
     * @return 统计结果
     */
    @PostMapping("/statistics")
    public Result<?> statistics(@RequestBody TravelTimeQuery query) {
        // TODO: 实现统计逻辑
        return Result.success();
    }
}
