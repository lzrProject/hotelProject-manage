package com.hotel.user.feign;

import com.github.pagehelper.PageInfo;
import com.hotel.index.pojo.Role;
import com.hotel.user.pojo.User;
import com.hotel.user.pojo.UserRole;
import entity.Result;
import entity.StatusCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(value = "user")
@RequestMapping(value = "/user")
public interface UserFeign {

    @GetMapping(value = {"/byId"})
    Result<User> findById(@RequestParam Integer id);

    @GetMapping("/findByUsername")
    List<User> findByUsername(@RequestParam String username);

    @PutMapping("/edit")
    Result update(@RequestBody User user);

    @DeleteMapping("del")
    Result delete(@RequestParam Integer id);

    @PostMapping("/page")
    Result<PageInfo> findPage(@RequestBody User user, @RequestParam int page, @RequestParam int limit);

    /**
     * Describe: 保存用户角色数据
     * Param: SysUser
     * Return: 操作结果
     * */
    @PostMapping("/saveUserRole")
    Boolean saveUserRole(@RequestParam Integer userId,@RequestBody List<Integer> roleIds);

    /**
     * 添加用户数据
     * @param user
     * @return
     */
    @PostMapping(value = "/add")
    Result addAll(@RequestBody User user);

    @GetMapping(value = {"/byUserId"})
    Result<List<UserRole>> findByUserId(@RequestParam Integer id);
}
