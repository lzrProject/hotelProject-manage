package com.hotel.entrance.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hotel.index.feign.RoleFeign;
import com.hotel.index.pojo.Role;
import com.hotel.user.feign.UserFeign;
import com.hotel.user.pojo.EditPassword;
import com.hotel.user.pojo.User;
import com.hotel.user.pojo.UserRole;
import entity.BCrypt;
import entity.Result;
import entity.StatusCode;
import entity.TokenDecode;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * 用户管理
 */

@RestController
@RequestMapping(value = "user")
public class UserController {
    /**
     * Describe: 基础路径
     */
    private static String MODULE_PATH = "system/user/";

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private RoleFeign roleFeign;

    /**
     * Describe: 获取用户列表视图
     * Param ModelAndView
     * Return 用户列表视图
     */
    @GetMapping("main")
    @PreAuthorize("hasAuthority('sys:user:main')")
    public ModelAndView main() {
        return new Result().jumpPage(MODULE_PATH + "main");
    }

    /**
     * Describe: 获取用户列表数据
     * Param ModelAndView
     * Return 用户列表数据
     */
    @GetMapping("data")
    @PreAuthorize("hasAuthority('sys:user:data')")
    public JSONObject data(User user, @RequestParam int page, @RequestParam int limit) {
        Result<PageInfo> result = userFeign.findPage(user, page, limit);
        if(result.isFlag()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",0);
            jsonObject.put("message",result.getMessage());
            jsonObject.put("count",result.getData().getTotal());
            jsonObject.put("data",result.getData().getList());
            return jsonObject;
        }
        return null;
    }

    /**
     * Describe: 用户新增视图
     * Param ModelAndView
     * Return 返回用户新增视图
     */
    @GetMapping("add")
    @PreAuthorize("hasAuthority('sys:user:add')")
    public ModelAndView add(Model model) {
        Result<List<Role>> all = roleFeign.findAll();
        model.addAttribute("sysRoles",all.getData());
        return new Result().jumpPage(MODULE_PATH + "add");
    }

    /**
     * Describe: 用户新增接口
     * Param ModelAndView
     * Return 操作结果
     */
    @PostMapping("save")
    @PreAuthorize("hasAuthority('sys:user:add')")
    public Result save(@RequestBody User user) {
        List<String> strings = Arrays.asList(user.getRoleIds().split(","));
        List<Integer> integers = strings.stream().map(Integer::parseInt).collect(Collectors.toList());

        if(userFeign.findByUsername(user.getUsername()).size() != 0){
            return new Result(false, StatusCode.ERROR,"用户名存在");
        }

        user.setLogin("0");
        user.setEnable("1");
        user.setStatus("1");
        user.setCreateTime(LocalDateTime.now());
        Result result = userFeign.addAll(user);
        Boolean a = userFeign.saveUserRole((Integer)result.getData(), integers);

        if(a && result.isFlag()){
            return result;
        }
        return null;
    }

    /**
     * Describe: 用户修改视图
     * Param ModelAndView
     * Return 返回用户修改视图
     */
    @GetMapping("edit")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public ModelAndView edit(Model model,@RequestParam Integer id) {
        Result<List<Role>> allRole = roleFeign.findAll();
        Result<List<UserRole>> myRole = userFeign.findByUserId(id);
        allRole.getData().forEach(sysRole->{
            myRole.getData().forEach(sysUserRole->{
                if(sysRole.getId().equals(sysUserRole.getRoleId()))sysRole.setChecked(true);
            });
        });

        model.addAttribute("sysRoles", allRole.getData());
        model.addAttribute("sysUser", userFeign.findById(id).getData());
        return new Result().jumpPage(MODULE_PATH + "edit");
    }

    /**
     * Describe: 用户修改接口
     * Param ModelAndView
     * Return 返回用户修改接口
     */
    @PutMapping("update")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public Result update(@RequestBody User user) {
        List<String> strings = Arrays.asList(user.getRoleIds().split(","));
        List<Integer> integers = strings.stream().map(Integer::parseInt).collect(Collectors.toList());

        userFeign.saveUserRole(user.getId(), integers);
        Result result = userFeign.update(user);
        return result;
    }

    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Integer id) {
        Result result = userFeign.delete(id);
        return result;
    }

    /**
     * Describe: 根据 userId 开启用户
     * Param: User
     * Return: 执行结果
     */
    @PutMapping("enable")
    public Result enable(@RequestBody User user) {
        user.setEnable("1");
        Result result = userFeign.update(user);
        return result;
    }

    /**
     * Describe: 根据 userId 禁用用户
     * Param: User
     * Return: 执行结果
     */
    @PutMapping("disable")
    public Result disable(@RequestBody User user) {
        user.setEnable("0");
        Result result = userFeign.update(user);
        return result;
    }



    /**
     * Describe: 个人资料
     * Param: null
     * Return: ModelAndView
     */
    @GetMapping("center")
    public ModelAndView center(Model model) {
        Map<String, String> userInfo = TokenDecode.getUserInfo();
        model.addAttribute("userInfo", userFeign.findByUsername( userInfo.get("username")).get(0));
        return new Result().jumpPage(MODULE_PATH + "center");
    }

    /**
     * Describe: 头像修改接口
     * Param: SysUser
     * Return: Result
     */
    @PutMapping("updateAvatar")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public Result updateAvatar(@RequestBody User user) {
        Map<String, String> userInfo = TokenDecode.getUserInfo();
        user.setId(Integer.valueOf(userInfo.get("id")));
        Result result = userFeign.update(user);
        return result;
    }

    /**
     * Describe: 用户修改接口
     * Param ModelAndView
     * Return 返回用户修改接口
     */
    @PutMapping("updateInfo")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public Result updateInfo(@RequestBody User user) {
        Result result = userFeign.update(user);
        return result;
    }

    /**
     * Describe: 更换头像
     * Param: null
     * Return: ModelAndView
     */
    @GetMapping("profile/{id}")
    public ModelAndView profile(Model model,@PathVariable("id")Integer id){
        model.addAttribute("id",id);
        return new Result().jumpPage(MODULE_PATH + "profile");
    }

    /**
     * Describe: 用户密码修改视图
     * Param ModelAndView
     * Return 返回用户密码修改视图
     */
    @GetMapping("editPassword")
    public ModelAndView editPasswordView() {
        return new Result().jumpPage(MODULE_PATH + "password");
    }

    /**
     * Describe: 用户密码修改接口
     * Param editPassword
     * Return: Result
     */
    @PutMapping("editPassword")
    public Result editPassword(@RequestBody EditPassword editPassword) {

        Map<String, String> userInfo = TokenDecode.getUserInfo();
        User userList = userFeign.findByUsername(userInfo.get("username")).get(0);


//        if (Strings.isBlank(confirmPassword)
//                || Strings.isBlank(newPassword)
//                || Strings.isBlank(oldPassword)) {
//            return new Result(false,StatusCode.ERROR,"输入不能为空");
//        }
        if(!BCrypt.checkpw(editPassword.getOldPassword(), userList.getPassword())){
            return new Result(false,StatusCode.ERROR,"密码验证失败");
        }
        if (!editPassword.getNewPassword().equals(editPassword.getConfirmPassword())) {
            return new Result(false,StatusCode.ERROR,"两次密码输入不一致");
        }

        User user = new User();
        user.setId(userList.getId());


        user.setPassword(new BCryptPasswordEncoder().encode(editPassword.getNewPassword()));

        Result result = userFeign.update(user);
        return result;
    }
}
