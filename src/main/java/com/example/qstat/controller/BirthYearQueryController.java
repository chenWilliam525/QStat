package com.example.qstat.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.qstat.dto.BirthYearQuery;
import com.example.qstat.entity.PersonData;
import com.example.qstat.service.BirthYearQueryService;
import com.example.qstat.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 出生年份查询控制器
 *
 * @author QStat
 * @since 2024-08-10
 */
@RestController
@RequestMapping("/query/birth-year")
@RequiredArgsConstructor
public class BirthYearQueryController {

    private final BirthYearQueryService birthYearQueryService;

    /**
     * 按出生年份分页查询
     *
     * @param query 查询条件
     * @return 分页结果
     */
    @PostMapping("/page")
    public Result<IPage<PersonData>> queryByPage(@RequestBody BirthYearQuery query) {
        return Result.success(birthYearQueryService.queryByPage(query));
    }

    /**
     * 按出生年份统计
     *
     * @param query 查询条件
     * @return 统计结果
     */
    @PostMapping("/statistics")
    public Result<?> statistics(@RequestBody BirthYearQuery query) {
        return Result.success(birthYearQueryService.statistics(query));
    }
}
