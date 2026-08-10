package com.example.qstat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.qstat.dto.MileageQuery;
import com.example.qstat.entity.PersonData;

import java.util.Map;

/**
 * 飞行里程查询服务
 *
 * @author QStat
 * @since 2024-08-10
 */
public interface MileageQueryService {

    /**
     * 按飞行里程分页查询
     *
     * @param query 查询条件
     * @return 分页结果
     */
    IPage<PersonData> queryByPage(MileageQuery query);

    /**
     * 按飞行里程统计
     *
     * @param query 查询条件
     * @return 统计结果
     */
    Map<String, Object> statistics(MileageQuery query);
}
