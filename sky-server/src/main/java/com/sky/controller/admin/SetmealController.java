package com.sky.controller.admin;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = "Setmeal API related")
@RestController
@RequestMapping("/admin/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;



    @GetMapping("/page")
    public Result<PageResult>  pageQuery(SetmealPageQueryDTO setmealPageQueryDTO){

        log.info("分页查询套餐列表: {}", setmealPageQueryDTO);

        PageResult pageResult =  setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);

    }

    @PostMapping
    @ApiOperation("Add setmeal operation")
    public  Result<String> addSetmeal(@RequestBody SetmealDTO setmealDTO){
        log.info("新增套餐: {}", setmealDTO);

        setmealService.saveWithDish(setmealDTO);
        return Result.success();

    }

    @PostMapping("/status/{status}")
    @ApiOperation("Start or stop setmeal")
    public  Result<String> startOrStop(@PathVariable Integer status,Long id ){

        log.info("setmeal start sellling or stop selling {} {}", status, id);
        setmealService.startOrStop(status, id);

        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("get setmeal by ID")
    public  Result<SetmealVO> getById(@PathVariable Long id){

        log.info("get setmeal {} ", id);

        SetmealVO setmealVO = setmealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }

    @PutMapping
    @ApiOperation("alter setmeal")
    public  Result<Setmeal> update(@RequestBody SetmealDTO setmealDTO ){
        log.info("Update setmeal by id  {} ",setmealDTO);
        setmealService.update(setmealDTO);

        return  Result.success();
    }

    @DeleteMapping
    @ApiOperation("批量删除套餐")
    public Result deleteBatch(@RequestParam List<Long> ids) {
        setmealService.deleteBatch(ids);
        return Result.success();
    }
}
