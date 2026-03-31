package com.sky.mapper;


import com.sky.anno.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;

import java.util.List;

//@Mapper in intializer MapperScanner
public interface DishFlavorMapper {


    void insertBatch(List<DishFlavor> flavors);


    List<DishFlavor> getByDishId(Long id);


    void deleteBatch(List<Long> dishIds);

    void deleteByDishId(Long id);
}
