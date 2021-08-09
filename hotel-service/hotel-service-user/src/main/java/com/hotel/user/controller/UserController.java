package com.hotel.user.controller;

import cn.hutool.http.server.HttpServerResponse;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.hotel.user.pojo.User;
import com.hotel.user.pojo.UserRole;
import com.hotel.user.service.UserRoleService;
import com.hotel.user.service.UserService;
import entity.BCrypt;
import entity.JwtUtil;
import entity.Result;
import entity.StatusCode;
import io.netty.handler.codec.base64.Base64Encoder;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;


//    @PostMapping
//    public String login(@RequestBody User user){
//        User result = userService.findById(user.getUsername());
//
//        /***
//         * equals()与字符串进行比较的对象。
//         * equalsIgnoreCase()与字符串进行比较的对象,不区分大小写
//         */
//        if(result != null) {
//            if (BCrypt.checkpw(user.getPassword(), result.getPassword())) {
//                //创建令牌信息
//                Map<String, Object> tokenMap = new HashMap<>();
//                tokenMap.put("role", "USER");
//                tokenMap.put("success", "SUCCESS");
//                tokenMap.put("username", user.getUsername());
//                String token = JwtUtil.createJWT(UUID.randomUUID().toString(), JSON.toJSONString(tokenMap), null);
//
//                return token;
//            }
//        }
//        return "错误";
//    }

    @PostMapping(value = "/add")
//    @PreAuthorize("hasAnyRole('user','vip')")
    public Result addAll(@RequestBody User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.addAll(user);
        return new Result(true,StatusCode.OK,"添加成功",user.getId());
    }

    @GetMapping(value = {"/byId"})
    public Result<User> findById(@RequestParam Integer id){
        User result = userService.findById(id);
        return new Result<User>(true,StatusCode.OK,"查询成功",result);
    }

    @GetMapping(value = {"/byUserId"})
    public Result<List<UserRole>> findByUserId(@RequestParam Integer id){
        List<UserRole> result = userRoleService.findByUserId(id);
        return new Result(true,StatusCode.OK,"查询成功",result);
    }

    @GetMapping("/findByUsername")
    public List<User> findByUsername(@RequestParam String username){
        List<User> result = userService.findByUsername(username);
        return result;
    }

    @PutMapping("/edit")
    public Result update(@RequestBody User user){
        userService.update(user);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @DeleteMapping("del")
    public Result delete(@RequestParam Integer id){
        userService.delById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @PostMapping("/page")
    public Result<PageInfo> findPage(@RequestBody User user,@RequestParam int page, @RequestParam int limit){
        PageInfo<User> result = userService.findPage(user, page, limit);
        return new Result(true,StatusCode.OK,"查询成功",result);
    }

    /**
     * Describe: 保存用户角色数据
     * Param: SysUser
     * Return: 操作结果
     * */
    @PostMapping("/saveUserRole")
    public Boolean saveUserRole(@RequestParam Integer userId,@RequestBody List<Integer> roleIds){
            userRoleService.delByUserId(userId);
        List<UserRole> userRoles = new ArrayList<>();
        roleIds.forEach(roleId->{
            UserRole sysUserRole = new UserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(userId);
            userRoles.add(sysUserRole);
        });
        int i = userRoleService.batchInsert(userRoles);
        return i > 0;
    }


}
