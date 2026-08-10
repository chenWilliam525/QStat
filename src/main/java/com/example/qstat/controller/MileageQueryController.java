package com.example.qstat.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.qstat.dto.MileageQuery;
import com.example.qstat.entity.PersonData;
import com.example.qstat.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 飞行里程查询控制器
 *
 * @author QStat
 * @since 2024-08-10
 */
@RestController
@RequestMapping("/query/mileage")
@RequiredArgsConstructor
public class MileageQueryController {

    // TODO: 注入 Service
    // private final MileageQueryService mileageQueryService;

    /**
     * 按飞行里程分页查询
     *
     * @param query 查询条件
     * @return 分页结果
     */
    @PostMapping("/page")
    public Result<IPage<PersonData>> queryByPage(@RequestBody MileageQuery query) {
        // TODO: 实现查询逻辑
        return Result.success();
    }

    /**
     * 按飞行里程统计
     *
     * @param query 查询条件
     * @return 统计结果
     */
    @PostMapping("/statistics")
    public Result<?> statistics(@RequestBody MileageQuery query) {
        // TODO: 实现统计逻辑
        return Result.success();
    }
}
