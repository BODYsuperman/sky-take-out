package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class DishServiceImpl implements DishService {



    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;


    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Transactional
    public void addDish(DishDTO dto) {


        Dish dish = new Dish();
        BeanUtils.copyProperties(dto, dish);

        dishMapper.insert(dish);

        log.info("dishId={}", dish.getId());

        List<DishFlavor> flavors = dto.getFlavors();
        flavors.forEach(f -> {
            f.setDishId(dish.getId());
        });

        dishFlavorMapper.insertBatch(flavors);

    }


    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        //set pagination parameters

        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        //map list cast to Page

        Page<DishVO> page = dishMapper.list(dishPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }


    @Transactional
    public void delete(List<Long> ids) {

        //1.judege if dish status is enabled

        ids.forEach(id->{
            Dish dish =  dishMapper.selectById(id);

            if(dish.getStatus() == StatusConstant.ENABLE){
                throw  new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        });
        Integer count = setmealDishMapper.countByDishId(ids);
        if(count > 0){
            throw  new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        dishMapper.deleteBatch(ids);
        //2. judge if dish is related with steal
        //3. delete dish
        //4. delete dish flavor

        dishFlavorMapper.deleteBatch(ids);


    }

    @Override
    public DishVO getById(Long id) {


        DishVO dishVO = new DishVO();

        Dish dish = dishMapper.selectById(id);
        BeanUtils.copyProperties(dish, dishVO);

        List<DishFlavor> dishFlavors =  dishFlavorMapper.selectByDishId(id);

        dishVO.setFlavors(dishFlavors);

        return dishVO;
    }

    @Transactional
    @Override
    public void update(DishDTO dishDTO) {

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        dishMapper.update(dish);

        //first delete if not delete then the operations like update delete insert is complex
        dishFlavorMapper.deleteByDishId(dishDTO.getId());

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!= null && !flavors.isEmpty()){

            flavors.forEach(f->{
                f.setDishId(dish.getId());
            });
            dishFlavorMapper.insertBatch(flavors);
        }



    }
}
