package com.example.qstat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.qstat.dto.PageQuery;
import com.example.qstat.entity.User;
import com.example.qstat.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 用户服务类
 *
 * @author QStat
 * @since 2024-08-10
 */
@Service
@RequiredArgsConstructor
public class UserService extends ServiceImpl<UserMapper, User> {

    private final UserMapper userMapper;

    /**
     * 分页查询用户列表
     *
     * @param pageQuery 分页参数
     * @return 用户列表
     */
    public IPage<User> pageUser(PageQuery pageQuery) {
        Page<User> page = new Page<>(pageQuery.getCurrent(), pageQuery.getSize());
        return userMapper.selectPage(page, null);
    }

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    public User getByUsername(String username) {
        return userMapper.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<User>()
                        .eq(User::getUsername, username)
        );
    }
}
