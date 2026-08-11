package com.example.qstat.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 保存模板请求
 *
 * @author QStat
 * @since 2024-08-11
 */
@Data
public class SaveTemplateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 模板名称
     */
    @NotBlank(message = "模板名称不能为空")
    private String templateName;

    /**
     * 查询类型（birth-year/mileage/travel-time）
     */
    @NotBlank(message = "查询类型不能为空")
    private String queryType;

    /**
     * 区间列表 (List<AgeRange> or List<MileageRange> or List<TimeRange>)
     */
    private List<?> ranges;

    /**
     * 性别（0-女，1-男，null-全部）
     */
    private Integer gender;

    /**
     * 模板描述
     */
    private String description;
}
