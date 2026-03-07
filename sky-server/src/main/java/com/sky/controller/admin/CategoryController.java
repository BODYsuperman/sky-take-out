package com.sky.controller.admin;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * category controller
 */
@Api(tags = "Category related API")
@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @ApiOperation("Category add API")
    @PostMapping
    public Result<String> save(@RequestBody CategoryDTO dto)
    {

        log.info("new Category add：{}", dto);
        //no return value
        categoryService.save(dto);
        return Result.success();
    }

    /**
     * page search
     * @param dto
     * @return
     */
    @ApiOperation("Category page search API")
    @GetMapping("/page")
    public Result<PageResult> page(CategoryPageQueryDTO dto)
    {

        log.info("Category page search：{}", dto);

        //no return value
        PageResult result =  categoryService.pageQuery(dto);

        return Result.success(result);
    }

    @ApiOperation("Category page update API")
    @PutMapping
    public Result<PageResult> update(@RequestBody  CategoryDTO dto)
    {

        log.info("Category page update：{}", dto);

        categoryService.update(dto);

        return Result.success();
    }

    @ApiOperation("Category delete API")
    @DeleteMapping
    public Result<String> deleteById(Long id)
    {
        log.info("Category delete：{}", id);

        categoryService.deleteById(id);

        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result<String> startOrStop(@PathVariable("status") Integer status, Long id)
    {
        log.info("Category status：{}", status , id);

        categoryService.startOrStop(status, id);

        return Result.success();
    }


    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> list(Integer type){
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }



}
