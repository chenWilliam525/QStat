package com.example.qstat.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.qstat.dto.TravelTimeQuery;
import com.example.qstat.entity.PersonData;
import com.example.qstat.service.TravelTimeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 飞行时间查询服务实现
 *
 * @author QStat
 * @since 2024-08-10
 */
@Service
@RequiredArgsConstructor
public class TravelTimeQueryServiceImpl implements TravelTimeQueryService {

    // TODO: 注入 Mapper
    // private final PersonDataMapper personDataMapper;

    @Override
    public IPage<PersonData> queryByPage(TravelTimeQuery query) {
        // TODO: 实现按飞行时间分页查询逻辑
        return null;
    }

    @Override
    public Object statistics(TravelTimeQuery query) {
        // TODO: 实现按飞行时间统计逻辑
        return null;
    }
}
