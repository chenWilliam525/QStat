# QStat - 数据查询和统计系统

## 项目简介

QStat 是一个基于 Spring Boot 框架的数据查询和统计系统，提供高效的数据查询、统计分析、报表导出等功能。

## 技术栈

- **后端框架**: Spring Boot 4.1.0
- **持久层**: MyBatis-Plus 3.5.5
- **数据库**: MySQL 8.0+ / PostgreSQL / H2
- **连接池**: Druid 1.2.20
- **模板引擎**: Thymeleaf
- **工具库**: Lombok
- **文档工具**: Apache POI 5.2.5 (Excel导出)

## 项目结构

```
QStat
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.example.qstat
│   │   │       ├── QStatApplication.java      # 主启动类
│   │   │       ├── config                      # 配置类
│   │   │       │   ├── MybatisPlusConfig.java  # MyBatis-Plus 配置
│   │   │       │   ├── WebConfig.java          # Web MVC 配置
│   │   │       │   └── MyMetaObjectHandler.java # 字段自动填充
│   │   │       ├── controller                   # 控制器层
│   │   │       ├── service                      # 服务层
│   │   │       ├── mapper                       # 数据访问层
│   │   │       ├── entity                       # 实体类
│   │   │       ├── dto                          # 数据传输对象
│   │   │       ├── vo                           # 视图对象
│   │   │       ├── exception                    # 异常处理
│   │   │       │   ├── GlobalExceptionHandler.java
│   │   │       │   └── BusinessException.java
│   │   │       └── utils                        # 工具类
│   │   └── resources
│   │       ├── application.properties           # 配置文件
│   │       ├── db                               # 数据库脚本
│   │       ├── mapper                           # MyBatis XML
│   │       ├── templates                        # Thymeleaf 模板
│   │       └── static                           # 静态资源
│   └── test                                     # 测试代码
└── pom.xml                                      # Maven 配置
```

## 快速开始

### 1. 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+ (可选)

### 2. 数据库配置

在 MySQL 中执行 `src/main/resources/db/schema.sql` 初始化数据库：

```bash
mysql -u root -p < src/main/resources/db/schema.sql
```

修改 `application.properties` 中的数据库连接信息：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/qstat
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. 运行项目

```bash
mvn clean install
mvn spring-boot:run
```

访问：http://localhost:8080/api

### 4. 测试接口

```bash
# 分页查询用户
curl http://localhost:8080/api/user/page?current=1&size=10

# 根据ID查询
curl http://localhost:8080/api/user/1

# 新增用户
curl -X POST http://localhost:8080/api/user \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"123456","nickname":"测试"}'
```

## 核心功能

- ✅ 用户管理（CRUD）
- ✅ 分页查询
- ✅ 统一异常处理
- ✅ 统一返回结果
- ✅ MyBatis-Plus 分页插件
- ✅ 逻辑删除支持
- ✅ 字段自动填充
- ✅ 跨域配置

## 开发指南

### 新增业务模块

1. 创建 Entity 继承 `BaseEntity`
2. 创建 Mapper 接口继承 `BaseMapper`
3. 创建 Service 类继承 `ServiceImpl`
4. 创建 Controller 并注入 Service

### 示例代码参考

项目中已包含完整的用户模块示例，可作为其他模块开发的参考模板。

## 配置说明

详细配置请查看 `application.properties` 文件，支持以下配置：

- 数据库配置（MySQL/PostgreSQL/H2）
- Druid 连接池配置
- MyBatis-Plus 配置
- 日志配置
- 文件上传配置

## 许可证

MIT License
