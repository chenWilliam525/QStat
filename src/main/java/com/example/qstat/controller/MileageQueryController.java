package com.example.qstat.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.qstat.dto.MileageQuery;
import com.example.qstat.entity.PersonData;
import com.example.qstat.service.MileageQueryService;
import com.example.qstat.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    private final MileageQueryService mileageQueryService;

    /**
     * 按飞行里程分页查询
     *
     * @param query 查询条件
     * @return 分页结果
     */
    @PostMapping("/page")
    public Result<IPage<PersonData>> queryByPage(@RequestBody MileageQuery query) {
        return Result.success(mileageQueryService.queryByPage(query));
    }

    /**
     * 按飞行里程统计
     *
     * @param query 查询条件
     * @return 统计结果
     */
    @PostMapping("/statistics")
    public Result<Map<String, Object>> statistics(@RequestBody MileageQuery query) {
        return Result.success(mileageQueryService.statistics(query));
    }
}
