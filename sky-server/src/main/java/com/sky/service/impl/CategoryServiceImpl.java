package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void save(CategoryDTO dto) {

        Category category = new Category();

// 2. 复制DTO的核心属性（name/type/sort）到category
// 重点：确认DTO的属性名和Category一致（比如DTO里有name、type、sort）
        BeanUtils.copyProperties(dto, category);

// 3. 补充公共字段（只set，不重新new对象）
        category.setStatus(StatusConstant.DISABLE);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());

// 4. 插入数据库（此时category有name/type/sort + 公共字段）
        categoryMapper.insert(category);

    }

    @Override
    public PageResult pageQuery(CategoryPageQueryDTO dto) {

        //1. start page
        PageHelper.startPage(dto.getPage(), dto.getPageSize());

        //2. query data
        Page<Category> page = categoryMapper.pageQuery(dto);

        //3. encapsulate result and return
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void update(CategoryDTO dto) {
        Category category = new Category();

        //2. copy core properties (name/type/sort) from DTO to category
        BeanUtils.copyProperties(dto, category);

        //3. supplement common fields (only set, do not re-new the object)
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());

        //4. update database (at this time, category has name/type/sort + common fields)
        categoryMapper.update(category);
    }

    @Override
    public void deleteById(Long id) {

        Integer count = dishMapper.countByCategoryId(id);
        if(count > 0){
            //当前分类下有菜品，不能删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        //查询当前分类是否关联了套餐，如果关联了就抛出业务异常
        count = setmealMapper.countByCategoryId(id);
        if(count > 0){
            //当前分类下有菜品，不能删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        categoryMapper.deleteById(id);
    }

    @Override
    public void startOrStop(Integer status, Long id) {


        Category category =  Category.builder()
                .id(id)
                .status(status)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();

        categoryMapper.update(category);
    }

    @Override
    public List<Category> list(Integer type) {
       return categoryMapper.list(type);
    }


}
