package com.example.qstat.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.qstat.dto.PersonDataQuery;
import com.example.qstat.entity.PersonData;
import com.example.qstat.service.PersonDataService;
import com.example.qstat.vo.PersonDataStatistics;
import com.example.qstat.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 人员数据控制器
 *
 * @author QStat
 * @since 2024-08-10
 */
@RestController
@RequestMapping("/person-data")
@RequiredArgsConstructor
public class PersonDataController {

    private final PersonDataService personDataService;

    /**
     * 分页查询数据
     *
     * @param query 查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    public Result<IPage<PersonData>> pageQuery(PersonDataQuery query) {
        return Result.success(personDataService.pageQuery(query));
    }

    /**
     * 获取所有数据
     *
     * @return 数据列表
     */
    @GetMapping("/list")
    public Result<List<PersonData>> list() {
        PersonDataQuery query = new PersonDataQuery();
        query.setCurrent(1);
        query.setSize(10000);
        return Result.success(personDataService.pageQuery(query).getRecords());
    }

    /**
     * 根据人员ID查询
     *
     * @param personIc 人员ID
     * @return 数据详情
     */
    @GetMapping("/{personIc}")
    public Result<PersonData> getByPersonIc(@PathVariable Long personIc) {
        PersonData data = personDataService.getByPersonIc(personIc);
        if (data == null) {
            return Result.error("数据不存在");
        }
        return Result.success(data);
    }

    /**
     * 获取统计数据
     *
     * @return 统计结果
     */
    @GetMapping("/statistics")
    public Result<PersonDataStatistics> getStatistics() {
        return Result.success(personDataService.getStatistics());
    }

    /**
     * 获取所有出生年份
     *
     * @return 出生年份列表
     */
    @GetMapping("/birth-years")
    public Result<List<Integer>> getBirthYears() {
        return Result.success(personDataService.getAllBirthYears());
    }
}
