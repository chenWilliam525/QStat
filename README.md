# QStat - 数据查询和统计系统

## 项目简介

QStat 是一个基于 Spring Boot 框架的数据查询和统计系统，提供高效的数据查询、统计分析、可视化展示和配置管理等功能。

### 核心特性

- 📊 **多维度查询**: 支持按出生年份、飞行里程、飞行时间三种维度进行数据查询
- 🎯 **灵活区间配置**: 支持自定义多个查询区间，可重叠或非重叠
- 📈 **数据可视化**: 基于 ECharts 提供柱状图、饼图、折线图等多种图表展示
- 💾 **配置持久化**: 支持保存和加载自定义查询配置，一键恢复查询条件
- 📄 **分页查询**: 完整的分页支持，处理大量数据

## 技术栈

### 后端技术

| 技术 | 版本 | 用途 |
|------|------|------|
| Spring Boot | 2.7.18 | Web框架 |
| MyBatis-Plus | 3.5.5 | ORM框架 |
| MySQL | 8.0+ | 关系型数据库 |
| HikariCP | - | 数据库连接池 |
| Lombok | - | 代码简化 |
| Jackson | - | JSON处理 |
| Apache POI | 5.2.5 | 文档导出 |

### 前端技术

| 技术 | 版本 | 用途 |
|------|------|------|
| Layui | 2.9.6 | UI组件库 |
| ECharts | 5.4.3 | 图表可视化 |

## 功能模块

### 1. 用户管理模块
- 用户 CRUD 操作
- 分页查询用户列表

### 2. 人员数据查询模块
- 多条件组合查询
- 数据统计分析
- 导出功能支持

### 3. 出生年份查询模块
- 多年龄段区间查询（支持重叠）
- 性别筛选
- 年龄分布统计图表

### 4. 飞行里程查询模块
- 多里程区间查询（支持重叠）
- 性别筛选
- 里程分布统计图表

### 5. 飞行时间查询模块
- 多时间区间查询（不允许重叠）
- 性别筛选
- 时间分布统计图表
- 趋势折线图展示

### 6. 查询模板管理模块 ⭐ 新增
- 保存当前查询配置
- 加载已保存的配置
- 模板管理（查看、删除）
- 支持三种查询类型的模板

## 项目结构

```
QStat
├── src/main/java/com/example/qstat/
│   ├── QStatApplication.java              # 主启动类
│   ├── config/                            # 配置类
│   │   ├── MybatisPlusConfig.java         # MyBatis-Plus 分页配置
│   │   ├── WebConfig.java                 # Web MVC / CORS 配置
│   │   └── MyMetaObjectHandler.java      # 字段自动填充
│   ├── controller/                         # 控制器层
│   │   ├── PageController.java            # 页面路由
│   │   ├── UserController.java           # 用户管理
│   │   ├── PersonDataController.java      # 人员数据查询
│   │   ├── BirthYearQueryController.java  # 出生年份查询
│   │   ├── MileageQueryController.java    # 飞行里程查询
│   │   ├── TravelTimeQueryController.java # 飞行时间查询
│   │   └── QueryTemplateController.java   # 查询模板管理 ⭐
│   ├── service/                            # 服务层
│   ├── mapper/                             # 数据访问层
│   ├── entity/                             # 实体类
│   ├── dto/                                # 数据传输对象
│   ├── vo/                                 # 视图对象
│   ├── exception/                          # 异常处理
│   │   ├── GlobalExceptionHandler.java
│   │   └── BusinessException.java
│   └── utils/                              # 工具类
│       └── GeneratePPT.java               # PPT 生成工具
└── src/main/resources/
    ├── application.properties              # 配置文件
    ├── db/                                 # 数据库脚本
    │   ├── schema.sql                     # 用户表结构
    │   ├── person_data.sql                # 人员数据表
    │   └── query_template.sql             # 查询模板表 ⭐
    └── templates/
        └── index.html                     # 主页面
```

## 快速开始

### 1. 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+

### 2. 数据库配置

创建数据库并导入表结构：

```bash
# 创建数据库
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS qsdata;"

# 导入表结构
mysql -u root -p qsdata < src/main/resources/db/schema.sql
mysql -u root -p qsdata < src/main/resources/db/person_data.sql
mysql -u root -p qsdata < src/main/resources/db/query_template.sql
```

修改 `src/main/resources/application.properties` 中的数据库连接信息：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/qsdata?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. 运行项目

```bash
mvn clean install
mvn spring-boot:run
```

### 4. 访问系统

打开浏览器访问：http://localhost:8088/api/

## 核心功能

### 查询功能

#### 出生年份查询
- 设置多个年龄段区间（支持重叠）
- 可选性别筛选
- 柱状图 + 饼图展示分布

#### 飞行里程查询
- 设置多个里程区间（支持重叠）
- 可选性别筛选
- 柱状图 + 饼图展示分布

#### 飞行时间查询
- 设置多个时间区间（不允许重叠）
- 可选性别筛选
- 柱状图 + 折线图展示趋势

### 配置管理 ⭐ 新增

#### 保存配置
1. 配置好查询条件后
2. 点击「💾 保存当前配置」
3. 输入模板名称
4. 系统自动保存当前区间配置和性别筛选

#### 加载配置
1. 点击「📂 加载已存配置」
2. 从列表中选择已保存的模板
3. 点击「应用」
4. 查询条件自动填充到表单

## API 接口文档

### 基础信息

- **基础路径**: `http://localhost:8088/api`
- **响应格式**: JSON

### 查询接口

| 端点 | 方法 | 功能 |
|------|------|------|
| `/query/birth-year/page` | POST | 出生年份分页查询 |
| `/query/birth-year/statistics` | POST | 出生年份统计分析 |
| `/query/mileage/page` | POST | 飞行里程分页查询 |
| `/query/mileage/statistics` | POST | 飞行里程统计分析 |
| `/query/travel-time/page` | POST | 飞行时间分页查询 |
| `/query/travel-time/statistics` | POST | 飞行时间统计分析 |

### 模板管理接口 ⭐ 新增

| 端点 | 方法 | 功能 |
|------|------|------|
| `/query-template` | POST | 保存查询模板 |
| `/query-template/page` | GET | 分页查询模板 |
| `/query-template/list/{type}` | GET | 按类型获取模板 |
| `/query-template/{id}` | GET | 获取模板详情 |
| `/query-template/{id}` | DELETE | 删除模板 |

### 请求示例

```bash
# 保存出生年份查询模板
curl -X POST http://localhost:8088/api/query-template \
  -H "Content-Type: application/json" \
  -d '{
    "templateName": "青年群体",
    "queryType": "birth-year",
    "ranges": [{"minAge": 18, "maxAge": 35}],
    "gender": null
  }'

# 获取出生年份模板列表
curl http://localhost:8088/api/query-template/list/birth-year
```

## 数据库设计

### person_data 表

| 字段 | 类型 | 说明 |
|------|------|------|
| person_id | BIGINT | 人员ID（主键） |
| gender | TINYINT | 性别（0-女，1-男） |
| birth_year | INT | 出生年份 |
| total_mileage | BIGINT | 总里程（公里） |
| total_travel_time | BIGINT | 总时间（小时） |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

### query_template 表 ⭐ 新增

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 模板ID（主键） |
| template_name | VARCHAR(100) | 模板名称 |
| query_type | VARCHAR(20) | 查询类型 |
| ranges_json | TEXT | 区间配置JSON |
| gender | TINYINT | 性别筛选 |
| description | VARCHAR(500) | 模板描述 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

## 开发指南

### 新增业务模块

1. 创建 Entity 继承 `BaseEntity`
2. 创建 Mapper 接口继承 `BaseMapper`
3. 创建 Service 接口继承 `IService`，实现类继承 `ServiceImpl`
4. 创建 Controller 并注入 Service
5. 定义 DTO 和 VO 类

### 示例代码参考

项目中已包含完整的「查询模板管理模块」实现，可作为其他模块开发的参考模板。

## 系统截图

### 主界面
- 三种查询方式的标签页切换
- 可视化区间配置界面
- 实时查询结果展示

### 图表展示
- ECharts 柱状图（区间分布）
- ECharts 饼图（占比分析）
- ECharts 折线图（趋势分析）

### 配置管理
- 一键保存当前配置
- 快速加载历史配置
- 配置模板管理界面

## 配置说明

| 配置项 | 默认值 | 说明 |
|--------|--------|------|
| server.port | 8088 | 服务端口 |
| server.servlet.context-path | /api | 应用上下文路径 |
| spring.datasource.url | - | 数据库连接地址 |
| mybatis-plus.configuration.log-impl | StdOutImpl | SQL日志实现 |

## 版本历史

| 版本 | 日期 | 功能 |
|------|------|------|
| 0.0.1 | 2024-08-10 | 初始版本，基础查询功能 |
| 0.0.2 | 2024-08-11 | 新增查询区间保存功能 |

## 许可证

MIT License

## 联系方式

- **GitHub**: https://github.com/chenWilliam525/QStat
- **Issues**: https://github.com/chenWilliam525/QStat/issues

---

*最后更新: 2024-08-11*
