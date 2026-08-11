package com.example.qstat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.qstat.entity.QueryTemplate;
import org.apache.ibatis.annotations.Mapper;

/**
 * 查询区间模板Mapper接口
 *
 * @author QStat
 * @since 2024-08-11
 */
@Mapper
public interface QueryTemplateMapper extends BaseMapper<QueryTemplate> {
}
