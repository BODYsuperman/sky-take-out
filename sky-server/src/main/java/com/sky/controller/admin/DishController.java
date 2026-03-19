package com.sky.controller.admin;


import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = "Dish API related")
@RestController
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    public Result<String> addDish(@RequestBody DishDTO  dto){
        log.info("add new dish {}", dto);

        dishService.addDish(dto);

        return Result.success();
    }


    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO){

        log.info("分页查询菜品列表: {}", dishPageQueryDTO);

        PageResult pageResult =  dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }



    @DeleteMapping
    @ApiOperation("Delete Dish Management API")
    public  Result delete(@RequestParam List<Long> ids){

        log.info("delete dishes {}", ids);
        dishService.delete(ids);
        return  Result.success();
    }


    @GetMapping("/{id}")
    @ApiOperation("Get dish according to id")
    public  Result getById(@PathVariable Long id){
        log.info("Query result display feature {} ",id);

        DishVO dishVO =  dishService.getById(id);
        return Result.success(dishVO);

    }


    @PutMapping
    @ApiOperation("update  dish by id")
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("Update dish by id  {} ",dishDTO);

        dishService.update(dishDTO);

        return  Result.success();

    }

    /**
     * 菜品起售停售
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售停售")
    public Result<String> startOrStop(@PathVariable Integer status, Long id){
        dishService.startOrStop(status,id);
        return Result.success();
    }


}
