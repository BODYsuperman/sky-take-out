package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {

    /**
     * add category
     * @param dto
     */

    void save(CategoryDTO dto);

    PageResult pageQuery(CategoryPageQueryDTO dto);

    void update(CategoryDTO dto);

    void deleteById(Long id);

    void startOrStop(Integer status, Long id);

    List<Category> list(Integer type);
}
