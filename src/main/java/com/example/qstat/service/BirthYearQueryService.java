package com.example.qstat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.qstat.dto.BirthYearQuery;
import com.example.qstat.entity.PersonData;

/**
 * 出生年份查询服务
 *
 * @author QStat
 * @since 2024-08-10
 */
public interface BirthYearQueryService {

    /**
     * 按出生年份分页查询
     *
     * @param query 查询条件
     * @return 分页结果
     */
    IPage<PersonData> queryByPage(BirthYearQuery query);

    /**
     * 按出生年份统计
     *
     * @param query 查询条件
     * @return 统计结果
     */
    Object statistics(BirthYearQuery query);
}
