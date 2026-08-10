package com.example.qstat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面控制器
 *
 * @author QStat
 * @since 2024-08-10
 */
@Controller
public class PageController {

    /**
     * 主页面（映射到 /api 根路径）
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 查询页面
     */
    @GetMapping("/query")
    public String query() {
        return "index";
    }
}
