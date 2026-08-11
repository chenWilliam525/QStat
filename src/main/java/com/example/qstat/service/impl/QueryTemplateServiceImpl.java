package com.example.qstat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.qstat.dto.QueryTemplateQuery;
import com.example.qstat.dto.SaveTemplateRequest;
import com.example.qstat.entity.QueryTemplate;
import com.example.qstat.mapper.QueryTemplateMapper;
import com.example.qstat.service.QueryTemplateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 查询区间模板服务实现
 *
 * @author QStat
 * @since 2024-08-11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QueryTemplateServiceImpl extends ServiceImpl<QueryTemplateMapper, QueryTemplate>
        implements QueryTemplateService {

    private final QueryTemplateMapper templateMapper;
    private final ObjectMapper objectMapper;

    @Override
    public IPage<QueryTemplate> pageQuery(QueryTemplateQuery query) {
        Page<QueryTemplate> page = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<QueryTemplate> wrapper = new LambdaQueryWrapper<>();

        if (query.getQueryType() != null) {
            wrapper.eq(QueryTemplate::getQueryType, query.getQueryType());
        }
        if (query.getTemplateName() != null) {
            wrapper.like(QueryTemplate::getTemplateName, query.getTemplateName());
        }

        wrapper.orderByDesc(QueryTemplate::getCreateTime);
        return templateMapper.selectPage(page, wrapper);
    }

    @Override
    public List<QueryTemplate> listByType(String queryType) {
        LambdaQueryWrapper<QueryTemplate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(QueryTemplate::getQueryType, queryType);
        wrapper.orderByDesc(QueryTemplate::getCreateTime);
        return templateMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QueryTemplate saveTemplate(SaveTemplateRequest request) {
        log.info("保存查询模板，参数：{}", request);

        QueryTemplate template = new QueryTemplate();
        template.setTemplateName(request.getTemplateName());
        template.setQueryType(request.getQueryType());
        template.setGender(request.getGender());
        template.setDescription(request.getDescription());

        // 将区间对象转换为JSON
        try {
            String rangesJson = objectMapper.writeValueAsString(request.getRanges());
            template.setRangesJson(rangesJson);
        } catch (JsonProcessingException e) {
            log.error("区间序列化失败", e);
            throw new RuntimeException("区间序列化失败", e);
        }

        templateMapper.insert(template);
        log.info("保存查询模板成功，ID：{}", template.getId());
        return template;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteTemplate(Long id) {
        log.info("删除查询模板，ID：{}", id);

        QueryTemplate entity = templateMapper.selectById(id);
        if (entity == null) {
            throw new IllegalArgumentException("模板不存在");
        }

        boolean result = templateMapper.deleteById(id) > 0;
        if (result) {
            log.info("删除查询模板成功，ID：{}", id);
        }
        return result;
    }
}
