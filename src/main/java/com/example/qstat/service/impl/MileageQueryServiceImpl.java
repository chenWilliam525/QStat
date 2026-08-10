package com.example.qstat.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.qstat.dto.MileageQuery;
import com.example.qstat.entity.PersonData;
import com.example.qstat.service.MileageQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 飞行里程查询服务实现
 *
 * @author QStat
 * @since 2024-08-10
 */
@Service
@RequiredArgsConstructor
public class MileageQueryServiceImpl implements MileageQueryService {

    // TODO: 注入 Mapper
    // private final PersonDataMapper personDataMapper;

    @Override
    public IPage<PersonData> queryByPage(MileageQuery query) {
        // TODO: 实现按飞行里程分页查询逻辑
        return null;
    }

    @Override
    public Object statistics(MileageQuery query) {
        // TODO: 实现按飞行里程统计逻辑
        return null;
    }
}
