package com.hotel.index.controller;

import com.github.pagehelper.PageInfo;
import com.hotel.index.pojo.Power;
import com.hotel.index.pojo.Role;
import com.hotel.index.pojo.RolePower;
import com.hotel.index.service.PowerService;
import com.hotel.index.service.RolePowerService;
import com.hotel.index.service.RoleService;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/role")
@CrossOrigin //跨域
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PowerService powerService;

    @Autowired
    private RolePowerService rolePowerService;

    /**
     * 角色分页列表
     * 角色查询列表
     */
    @PostMapping("/page")
    public Result<PageInfo> findPage(@RequestBody Role role, @RequestParam int page,@RequestParam int limit){
        PageInfo<Role> result = roleService.findPage(role, page, limit);
        return new Result(true, StatusCode.OK,"查询成功",result);
    }

    /**
     * 角色添加
     * @param role
     * @return
     */
    @PostMapping("/add")
    public Result addAll(@RequestBody Role role){
        roleService.addAll(role);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    @GetMapping("/byId")
    public Result<Role> findById(@RequestParam Integer id){
        Role result = roleService.findById(id);
        return new Result<Role>(true,StatusCode.OK,"查询成功",result);
    }

    /**
     * 修改角色数据
     * @param role
     * @return
     */
    @PutMapping("/updated")
    public Result update(@RequestBody Role role){
        roleService.update(role);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * Describe: 查询角色权限信息
     * Param: id
     * Return: 返回角色信息
     * */
    @GetMapping("rolePower")
    public List<Power> getRolePower(@RequestParam Integer id) {
        List<Power> allPower = powerService.list(null);

        List<RolePower> myPower =  rolePowerService.list(id);
        allPower.forEach(power->{
            myPower.forEach(rolePower->{
                if(rolePower.getPowerId().equals(power.getId()))power.setCheckArr("1");
            });
        });
        return allPower;
    }

    /**
     * Describe: 保存角色权限数据
     * Param: roleId powerIds
     * Return: 执行结果
     * */
    @PutMapping("upRolePower")
    public Boolean saveRolePower(@RequestParam Integer roleId,@RequestBody List<Integer> powerIds) {
        rolePowerService.delByRoleId(roleId);
        List<RolePower> rolePowers = new ArrayList<>();
        powerIds.forEach(powerId->{
            RolePower rolePower = new RolePower();
            rolePower.setRoleId(roleId);
            rolePower.setPowerId(powerId);
            rolePowers.add(rolePower);
        });
        int result = rolePowerService.batchInsert(rolePowers);
        return result > 0;
//        return null;
    }

    /**
     * Describe: 删除角色
     * Param: id
     * Return: ResuBean
     * */
    @DeleteMapping("delete/{id}")
    public Result remove(@PathVariable Integer id){
        roleService.delById(id);
        rolePowerService.delByRoleId(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * Describe: 用户批量删除接口
     * Param: ids
     * Return: ResuBean
     * */
    @DeleteMapping("batchDelete")
    public Result batchRemove(@RequestBody List<Integer> ids){
        roleService.batchRemove(ids);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 查询所有
     * @return
     */
    @GetMapping
    public Result<List<Role>> findAll(){
        List<Role> result = roleService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",result);
    }

}
