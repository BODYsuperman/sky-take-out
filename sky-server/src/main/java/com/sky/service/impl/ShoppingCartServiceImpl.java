package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        // ===== 1. 构建查询条件（单独对象，绝不复用）=====
        ShoppingCart queryCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, queryCart);
        queryCart.setUserId(BaseContext.getCurrentId());

        // ===== 2. 查询是否已存在 =====
        List<ShoppingCart> shoppingCartsList = shoppingCartMapper.list(queryCart);

        // ===== 3. 存在 → 数量 +1 =====
        if (shoppingCartsList != null && !shoppingCartsList.isEmpty()) {
            ShoppingCart existCart = shoppingCartsList.get(0);
            existCart.setNumber(existCart.getNumber() + 1);

            log.info("id={}", existCart.getId());
            log.info("number={}", existCart.getNumber());
            shoppingCartMapper.updateNumberById(existCart);

            return;
        }
        // ===== 4. 不存在 → 新增 =====
        else {
            ShoppingCart shoppingCart = new ShoppingCart(); // 全新对象
            BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
            shoppingCart.setUserId(BaseContext.getCurrentId());

            Long dishId = shoppingCartDTO.getDishId();
            if (dishId != null) {
                Dish dish = dishMapper.getByDishId(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            } else {
                Setmeal setmeal = setmealMapper.getById(shoppingCartDTO.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }

            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }
    }

    @Override
    public List<ShoppingCart> showShoppingCart() {
        return shoppingCartMapper.list(ShoppingCart.builder().userId(BaseContext.getCurrentId()).build());
    }

    @Override
    public void cleanShoppingCart() {
        shoppingCartMapper.deleteByUserId(BaseContext.getCurrentId());
    }

    @Override
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();

        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);

        shoppingCart.setUserId(BaseContext.getCurrentId());

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        if(list!= null && list.size() > 0){
            shoppingCart = list.get(0);


            Integer number = shoppingCart.getNumber();
            if(number == 1){
                shoppingCartMapper.deleteById(shoppingCart.getId());
            }
            else{

                shoppingCart.setNumber(shoppingCart.getNumber() -1);
                shoppingCartMapper.updateNumberById(shoppingCart);
            }

        }


    }
}
