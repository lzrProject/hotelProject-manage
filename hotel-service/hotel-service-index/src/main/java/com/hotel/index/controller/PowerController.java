package com.hotel.index.controller;

import com.hotel.index.pojo.Power;
import com.hotel.index.pojo.RolePower;
import com.hotel.index.service.PowerService;
import com.hotel.index.service.RolePowerService;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/power")
@CrossOrigin //跨域
public class PowerController {
    @Autowired
    private PowerService powerService;

    @Autowired
    private RolePowerService rolePowerService;

    /**
     * Describe: 获取权限列表数据
     * Param ModelAndView
     * Return 权限列表数据
     * */
    @PostMapping("/list")
    public Result<List<Power>> data(@RequestBody Power power){
        List<Power> list = powerService.list(power);
        return new Result<List<Power>>(true, StatusCode.OK,"查询成功",list);
    }

    /**
     * 权限添加数据
     */
    @PostMapping("/add")
    public Result addAll(@RequestBody Power power){
        powerService.addAll(power);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 根据id查询数据
     */
    @GetMapping("/byId")
    public Result<Power> findById(@RequestParam Integer id){
        Power result = powerService.findById(id);
        return new Result<Power>(true,StatusCode.OK,"查询成功",result);
    }

    /**
     * Describe: 修改权限信息
     * Param SysPower
     * Return 执行结果
     * */
    @PutMapping("/updated")
    public Result update(@RequestBody Power power) {
        powerService.update(power);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * Describe: 根据 id 进行删除
     * Param id
     * Return ResuTree
     * */
    @DeleteMapping("delete/{id}")
    public Result remove(@PathVariable Integer id){
        powerService.delById(id);
        rolePowerService.delByPowerId(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 根据用户ID查询权限信息
     * @param userId
     * @return
     */
    @GetMapping("/findPowerId")
    public ArrayList findPowerId(@RequestParam Integer userId){
        List<RolePower> powerIds = rolePowerService.findPowerId(userId);
        ArrayList<String> allCode = new ArrayList<>();
        for(RolePower powerId : powerIds){
            Power byId = powerService.findById(powerId.getPowerId());
//            List<Power> byParentIds = powerService.findByParentId(powerId.getPowerId());
            allCode.add(byId.getPowerCode());
//            for(Power byParentId : byParentIds) allCode.add(byParentId.getPowerCode());
        }
        return allCode;
    }

}
