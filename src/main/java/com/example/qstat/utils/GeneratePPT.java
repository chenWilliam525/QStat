package com.example.qstat.utils;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * QStat项目PPT生成器
 */
public class GeneratePPT {

    public static void main(String[] args) {
        try (XMLSlideShow ppt = new XMLSlideShow()) {

            // 创建所有幻灯片
            createTitleSlide(ppt);
            createOverviewSlide(ppt);
            createTechStackSlide(ppt);
            createProjectStructureSlide(ppt);
            createCoreFeaturesSlide(ppt);
            createEntitySlide(ppt);
            createAPISlide(ppt);
            createDatabaseSlide(ppt);
            createDemoSlide(ppt);
            createThankYouSlide(ppt);

            // 保存PPT
            try (FileOutputStream out = new FileOutputStream("C:\\bjtu\\QS\\QStat项目介绍.pptx")) {
                ppt.write(out);
                System.out.println("PPT生成成功！文件位置: C:\\bjtu\\QS\\QStat项目介绍.pptx");
            }
        } catch (IOException e) {
            System.err.println("PPT生成失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createTitleSlide(XMLSlideShow ppt) {
        XSLFSlide slide = ppt.createSlide();

        XSLFTextShape title = slide.createTextBox();
        title.setAnchor(new Rectangle(50, 100, 650, 100));
        XSLFTextParagraph p1 = title.addNewTextParagraph();
        XSLFTextRun r1 = p1.addNewTextRun();
        r1.setText("QStat");
        r1.setFontSize(42.0);
        r1.setBold(true);
        r1.setFontFamily("Microsoft YaHei");

        XSLFTextRun r2 = p1.addNewTextRun();
        r2.setText("\n数据查询和统计系统");
        r2.setFontSize(36.0);
        r2.setFontFamily("Microsoft YaHei");

        XSLFTextShape subtitle = slide.createTextBox();
        subtitle.setAnchor(new Rectangle(50, 250, 650, 200));
        XSLFTextParagraph p2 = subtitle.addNewTextParagraph();

        addText(p2, "基于 Spring Boot + MyBatis-Plus", 18.0);
        addText(p2, "\n高效的数据查询、统计分析、报表导出系统", 16.0);
        addText(p2, "\n\n版本: 0.0.1-SNAPSHOT", 14.0);
        addText(p2, "\n日期: 2024年8月", 14.0);
    }

    private static void createOverviewSlide(XMLSlideShow ppt) {
        XSLFSlide slide = ppt.createSlide();

        XSLFTextShape title = slide.createTextBox();
        title.setAnchor(new Rectangle(50, 30, 650, 50));
        addText(title.addNewTextParagraph(), "项目概述", 28.0, true);

        XSLFTextShape content = slide.createTextBox();
        content.setAnchor(new Rectangle(50, 100, 650, 400));
        XSLFTextParagraph p = content.addNewTextParagraph();

        addText(p, "什么是 QStat？", 24.0, true);
        addText(p, "\nQStat 是一个基于 Spring Boot 框架的数据查询和统计系统，", 16.0);
        addText(p, "提供高效的数据查询、统计分析、报表导出等功能。", 16.0);

        addText(p, "\n核心价值", 24.0, true);
        addText(p, "\n• 高效：基于 MyBatis-Plus 的快速数据访问", 16.0);
        addText(p, "• 灵活：支持多维度查询（出生年份、里程、出行时间）", 16.0);
        addText(p, "• 便捷：统一的分页查询和异常处理", 16.0);
        addText(p, "• 可扩展：清晰的分层架构，易于扩展新功能", 16.0);

        addText(p, "\n应用场景", 24.0, true);
        addText(p, "\n• 人员数据管理与分析", 16.0);
        addText(p, "• 出行行为统计与查询", 16.0);
        addText(p, "• 数据报表生成与导出", 16.0);
    }

    private static void createTechStackSlide(XMLSlideShow ppt) {
        XSLFSlide slide = ppt.createSlide();

        XSLFTextShape title = slide.createTextBox();
        title.setAnchor(new Rectangle(50, 30, 650, 50));
        addText(title.addNewTextParagraph(), "技术栈", 28.0, true);

        XSLFTextShape content = slide.createTextBox();
        content.setAnchor(new Rectangle(50, 100, 300, 450));
        XSLFTextParagraph p = content.addNewTextParagraph();

        addText(p, "后端技术", 22.0, true);
        addText(p, "\n• Spring Boot 2.7.18", 16.0);
        addText(p, "• MyBatis-Plus 3.5.5", 16.0);
        addText(p, "• Spring MVC", 16.0);
        addText(p, "• Spring Validation", 16.0);
        addText(p, "• Thymeleaf", 16.0);

        XSLFTextShape content2 = slide.createTextBox();
        content2.setAnchor(new Rectangle(400, 100, 300, 450));
        XSLFTextParagraph p2 = content2.addNewTextParagraph();

        addText(p2, "数据与工具", 22.0, true);
        addText(p2, "\n• MySQL 8.0+", 16.0);
        addText(p2, "• HikariCP 连接池", 16.0);
        addText(p2, "• Lombok", 16.0);
        addText(p2, "• Apache POI 5.2.5", 16.0);
        addText(p2, "• Jackson", 16.0);
        addText(p2, "• JDK 17+", 16.0);

        XSLFTextShape note = slide.createTextBox();
        note.setAnchor(new Rectangle(50, 480, 650, 50));
        addText(note.addNewTextParagraph(), "支持 PostgreSQL / H2 数据库", 14.0);
    }

    private static void createProjectStructureSlide(XMLSlideShow ppt) {
        XSLFSlide slide = ppt.createSlide();

        XSLFTextShape title = slide.createTextBox();
        title.setAnchor(new Rectangle(50, 30, 650, 50));
        addText(title.addNewTextParagraph(), "项目结构", 28.0, true);

        XSLFTextShape content = slide.createTextBox();
        content.setAnchor(new Rectangle(50, 100, 650, 450));
        XSLFTextParagraph p = content.addNewTextParagraph();

        addText(p, "com.example.qstat", 20.0, true);

        addText(p, "\n├── config/           配置类", 14.0);
        addText(p, "│   ├── MybatisPlusConfig.java      MyBatis-Plus 配置", 12.0);
        addText(p, "│   ├── WebConfig.java              Web MVC 配置", 12.0);
        addText(p, "│   └── MyMetaObjectHandler.java    字段自动填充", 12.0);

        addText(p, "\n├── controller/       控制器层（REST API）", 14.0);
        addText(p, "│   ├── PersonDataController.java", 12.0);
        addText(p, "│   ├── BirthYearQueryController.java", 12.0);
        addText(p, "│   ├── MileageQueryController.java", 12.0);
        addText(p, "│   └── TravelTimeQueryController.java", 12.0);

        addText(p, "\n├── service/          服务层（业务逻辑）", 14.0);
        addText(p, "├── mapper/           数据访问层（MyBatis）", 14.0);
        addText(p, "├── entity/           实体类", 14.0);
        addText(p, "├── dto/              数据传输对象（查询条件）", 14.0);
        addText(p, "├── vo/               视图对象（返回结果）", 14.0);
        addText(p, "└── exception/        异常处理", 14.0);
    }

    private static void createCoreFeaturesSlide(XMLSlideShow ppt) {
        XSLFSlide slide = ppt.createSlide();

        XSLFTextShape title = slide.createTextBox();
        title.setAnchor(new Rectangle(50, 30, 650, 50));
        addText(title.addNewTextParagraph(), "核心功能", 28.0, true);

        XSLFTextShape content = slide.createTextBox();
        content.setAnchor(new Rectangle(50, 100, 650, 450));
        XSLFTextParagraph p = content.addNewTextParagraph();

        addText(p, "数据查询功能", 22.0, true);
        addText(p, "\n• 分页查询：支持灵活的分页参数配置", 16.0);
        addText(p, "• 多维度查询：按出生年份、里程、出行时间", 16.0);
        addText(p, "• 精确查询：按人员ID查询详细信息", 16.0);

        addText(p, "\n统计分析功能", 22.0, true);
        addText(p, "\n• 数据统计：总数、平均值、最大/最小值", 16.0);
        addText(p, "• 分组统计：按指定维度分组统计", 16.0);
        addText(p, "• 派生字段：年龄计算、性别描述", 16.0);

        addText(p, "\n系统功能", 22.0, true);
        addText(p, "\n• 统一异常处理：GlobalExceptionHandler", 16.0);
        addText(p, "• 统一返回结果：Result<T>", 16.0);
        addText(p, "• 字段自动填充：创建/更新时间", 16.0);
        addText(p, "• 跨域支持：WebConfig 配置", 16.0);
    }

    private static void createEntitySlide(XMLSlideShow ppt) {
        XSLFSlide slide = ppt.createSlide();

        XSLFTextShape title = slide.createTextBox();
        title.setAnchor(new Rectangle(50, 30, 650, 50));
        addText(title.addNewTextParagraph(), "核心数据实体 - PersonData", 28.0, true);

        XSLFTextShape content = slide.createTextBox();
        content.setAnchor(new Rectangle(50, 100, 650, 450));
        XSLFTextParagraph p = content.addNewTextParagraph();

        addText(p, "PersonData - 人员数据实体", 20.0, true);

        addText(p, "\n数据库字段", 18.0, true);
        addText(p, "\n• personId (Long)      - 人员ID（主键）", 14.0);
        addText(p, "• gender (Integer)     - 性别（0-女，1-男）", 14.0);
        addText(p, "• birthYear (Integer)  - 出生年份", 14.0);
        addText(p, "• totalMileage (Long)  - 总旅行里程", 14.0);
        addText(p, "• totalTravelTime (Long) - 总旅行时间", 14.0);

        addText(p, "\n派生字段（不存储）", 18.0, true);
        addText(p, "\n• age (Integer)        - 根据出生年份计算", 14.0);
        addText(p, "• genderDesc (String)  - 性别描述（男/女）", 14.0);

        addText(p, "\n数据表", 18.0, true);
        addText(p, "\n• 表名: person_data", 14.0);
        addText(p, "• 支持逻辑删除（可选）", 14.0);
    }

    private static void createAPISlide(XMLSlideShow ppt) {
        XSLFSlide slide = ppt.createSlide();

        XSLFTextShape title = slide.createTextBox();
        title.setAnchor(new Rectangle(50, 30, 650, 50));
        addText(title.addNewTextParagraph(), "REST API 接口", 28.0, true);

        XSLFTextShape content = slide.createTextBox();
        content.setAnchor(new Rectangle(50, 100, 300, 450));
        XSLFTextParagraph p = content.addNewTextParagraph();

        addText(p, "人员数据接口", 18.0, true);
        addText(p, "\nGET  /person-data/page", 14.0);
        addText(p, "     分页查询", 12.0);
        addText(p, "\nGET  /person-data/list", 14.0);
        addText(p, "     获取全部数据", 12.0);
        addText(p, "\nGET  /person-data/{id}", 14.0);
        addText(p, "     按ID查询", 12.0);
        addText(p, "\nGET  /person-data/statistics", 14.0);
        addText(p, "     获取统计数据", 12.0);

        XSLFTextShape content2 = slide.createTextBox();
        content2.setAnchor(new Rectangle(400, 100, 300, 450));
        XSLFTextParagraph p2 = content2.addNewTextParagraph();

        addText(p2, "查询接口", 18.0, true);
        addText(p2, "\nPOST /query/birth-year/page", 14.0);
        addText(p2, "     出生年份分页查询", 12.0);
        addText(p2, "\nPOST /query/birth-year/statistics", 14.0);
        addText(p2, "     出生年份统计", 12.0);
        addText(p2, "\nPOST /query/mileage/page", 14.0);
        addText(p2, "     里程分页查询", 12.0);
        addText(p2, "\nPOST /query/travel-time/page", 14.0);
        addText(p2, "     出行时间分页查询", 12.0);

        XSLFTextShape note = slide.createTextBox();
        note.setAnchor(new Rectangle(50, 480, 650, 50));
        addText(note.addNewTextParagraph(), "基础路径: http://localhost:8088/api", 14.0);
    }

    private static void createDatabaseSlide(XMLSlideShow ppt) {
        XSLFSlide slide = ppt.createSlide();

        XSLFTextShape title = slide.createTextBox();
        title.setAnchor(new Rectangle(50, 30, 650, 50));
        addText(title.addNewTextParagraph(), "数据库配置", 28.0, true);

        XSLFTextShape content = slide.createTextBox();
        content.setAnchor(new Rectangle(50, 100, 650, 450));
        XSLFTextParagraph p = content.addNewTextParagraph();

        addText(p, "连接配置", 20.0, true);
        addText(p, "\n数据库: MySQL 8.0+", 16.0);
        addText(p, "地址: localhost:3306", 16.0);
        addText(p, "数据库: qsdata", 16.0);
        addText(p, "字符集: UTF-8", 16.0);
        addText(p, "时区: Asia/Shanghai", 16.0);

        addText(p, "\n连接池 (HikariCP)", 20.0, true);
        addText(p, "\n• 最小空闲连接: 5", 16.0);
        addText(p, "• 最大连接数: 20", 16.0);
        addText(p, "• 连接超时: 30秒", 16.0);
        addText(p, "• 空闲超时: 10分钟", 16.0);
        addText(p, "• 最大生命周期: 30分钟", 16.0);

        addText(p, "\n支持的数据库", 20.0, true);
        addText(p, "\n• MySQL (生产环境)", 16.0);
        addText(p, "• PostgreSQL (可选)", 16.0);
        addText(p, "• H2 (开发/测试)", 16.0);
    }

    private static void createDemoSlide(XMLSlideShow ppt) {
        XSLFSlide slide = ppt.createSlide();

        XSLFTextShape title = slide.createTextBox();
        title.setAnchor(new Rectangle(50, 30, 650, 50));
        addText(title.addNewTextParagraph(), "快速开始", 28.0, true);

        XSLFTextShape content = slide.createTextBox();
        content.setAnchor(new Rectangle(50, 100, 650, 450));
        XSLFTextParagraph p = content.addNewTextParagraph();

        addText(p, "1. 环境准备", 20.0, true);
        addText(p, "\n• JDK 17+", 16.0);
        addText(p, "• Maven 3.6+", 16.0);
        addText(p, "• MySQL 8.0+", 16.0);

        addText(p, "\n2. 数据库初始化", 20.0, true);
        addText(p, "\nmysql -u root -p < src/main/resources/db/schema.sql", 14.0);

        addText(p, "\n3. 运行项目", 20.0, true);
        addText(p, "\nmvn clean install", 14.0);
        addText(p, "mvn spring-boot:run", 14.0);

        addText(p, "\n4. 访问地址", 20.0, true);
        addText(p, "\nhttp://localhost:8088/api", 16.0);

        addText(p, "\n5. 测试接口", 20.0, true);
        addText(p, "\ncurl http://localhost:8088/api/person-data/page?current=1&size=10", 14.0);
    }

    private static void createThankYouSlide(XMLSlideShow ppt) {
        XSLFSlide slide = ppt.createSlide();

        XSLFTextShape title = slide.createTextBox();
        title.setAnchor(new Rectangle(50, 200, 650, 100));
        XSLFTextParagraph p = title.addNewTextParagraph();
        XSLFTextRun r = p.addNewTextRun();
        r.setText("谢谢观看！");
        r.setFontSize(42.0);
        r.setBold(true);
        r.setFontFamily("Microsoft YaHei");

        XSLFTextShape subtitle = slide.createTextBox();
        subtitle.setAnchor(new Rectangle(50, 320, 650, 200));
        XSLFTextParagraph p2 = subtitle.addNewTextParagraph();

        addText(p2, "QStat - 数据查询和统计系统", 18.0);
        addText(p2, "\n如有问题，请联系开发团队", 16.0);
        addText(p2, "\n\nGitHub: https://github.com/your-org/QStat", 14.0);
    }

    private static void addText(XSLFTextParagraph p, String text, double fontSize) {
        addText(p, text, fontSize, false);
    }

    private static void addText(XSLFTextParagraph p, String text, double fontSize, boolean bold) {
        XSLFTextRun run = p.addNewTextRun();
        run.setText(text);
        run.setFontSize(fontSize);
        run.setFontFamily("Microsoft YaHei");
        if (bold) {
            run.setBold(true);
        }
    }
}
