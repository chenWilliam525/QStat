package com.example.qstat.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.qstat.dto.PageQuery;
import com.example.qstat.entity.User;
import com.example.qstat.service.UserService;
import com.example.qstat.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器（示例）
 *
 * @author QStat
 * @since 2024-08-10
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 分页查询用户列表
     *
     * @param pageQuery 分页参数
     * @return 用户列表
     */
    @GetMapping("/page")
    public Result<IPage<User>> pageUser(PageQuery pageQuery) {
        return Result.success(userService.pageUser(pageQuery));
    }

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        return Result.success(user);
    }

    /**
     * 新增用户
     *
     * @param user 用户信息
     * @return 操作结果
     */
    @PostMapping
    public Result<Void> save(@RequestBody User user) {
        userService.save(user);
        return Result.success();
    }

    /**
     * 更新用户
     *
     * @param user 用户信息
     * @return 操作结果
     */
    @PutMapping
    public Result<Void> update(@RequestBody User user) {
        userService.updateById(user);
        return Result.success();
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        userService.removeById(id);
        return Result.success();
    }
}
