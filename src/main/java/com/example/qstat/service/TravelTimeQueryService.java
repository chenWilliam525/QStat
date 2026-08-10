package com.example.qstat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.qstat.dto.TravelTimeQuery;
import com.example.qstat.entity.PersonData;

/**
 * 飞行时间查询服务
 *
 * @author QStat
 * @since 2024-08-10
 */
public interface TravelTimeQueryService {

    /**
     * 按飞行时间分页查询
     *
     * @param query 查询条件
     * @return 分页结果
     */
    IPage<PersonData> queryByPage(TravelTimeQuery query);

    /**
     * 按飞行时间统计
     *
     * @param query 查询条件
     * @return 统计结果
     */
    Object statistics(TravelTimeQuery query);
}
