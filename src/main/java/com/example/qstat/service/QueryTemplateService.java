package com.example.qstat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.qstat.dto.QueryTemplateQuery;
import com.example.qstat.dto.SaveTemplateRequest;
import com.example.qstat.entity.QueryTemplate;

import java.util.List;

/**
 * 查询区间模板服务
 *
 * @author QStat
 * @since 2024-08-11
 */
public interface QueryTemplateService extends IService<QueryTemplate> {

    /**
     * 分页查询模板列表
     */
    IPage<QueryTemplate> pageQuery(QueryTemplateQuery query);

    /**
     * 按查询类型获取模板列表
     */
    List<QueryTemplate> listByType(String queryType);

    /**
     * 保存模板
     */
    QueryTemplate saveTemplate(SaveTemplateRequest request);

    /**
     * 删除模板
     */
    boolean deleteTemplate(Long id);
}
