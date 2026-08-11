package com.example.qstat.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.qstat.dto.QueryTemplateQuery;
import com.example.qstat.dto.SaveTemplateRequest;
import com.example.qstat.entity.QueryTemplate;
import com.example.qstat.service.QueryTemplateService;
import com.example.qstat.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 查询区间模板控制器
 *
 * @author QStat
 * @since 2024-08-11
 */
@RestController
@RequestMapping("/query-template")
@RequiredArgsConstructor
public class QueryTemplateController {

    private final QueryTemplateService templateService;

    /**
     * 分页查询模板列表
     */
    @GetMapping("/page")
    public Result<IPage<QueryTemplate>> pageQuery(QueryTemplateQuery query) {
        return Result.success(templateService.pageQuery(query));
    }

    /**
     * 按查询类型获取模板列表
     */
    @GetMapping("/list/{queryType}")
    public Result<List<QueryTemplate>> listByType(@PathVariable String queryType) {
        return Result.success(templateService.listByType(queryType));
    }

    /**
     * 获取模板详情
     */
    @GetMapping("/{id}")
    public Result<QueryTemplate> getById(@PathVariable Long id) {
        QueryTemplate template = templateService.getById(id);
        if (template == null) {
            return Result.error("模板不存在");
        }
        return Result.success(template);
    }

    /**
     * 保存模板
     */
    @PostMapping
    public Result<QueryTemplate> save(@Valid @RequestBody SaveTemplateRequest request) {
        QueryTemplate saved = templateService.saveTemplate(request);
        return Result.success("保存成功", saved);
    }

    /**
     * 删除模板
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        boolean success = templateService.deleteTemplate(id);
        return success ? Result.success("删除成功") : Result.error("删除失败");
    }
}
