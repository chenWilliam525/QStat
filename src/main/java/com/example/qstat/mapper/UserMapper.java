package com.example.qstat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.qstat.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 *
 * @author QStat
 * @since 2024-08-10
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    // 继承 BaseMapper 后，自动拥有 CRUD 方法
    // 如需自定义 SQL，可在此添加方法或在 resources/mapper 目录下创建 XML 文件
}
