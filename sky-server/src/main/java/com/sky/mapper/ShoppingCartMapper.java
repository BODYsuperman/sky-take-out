package com.sky.mapper;

import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartMapper {


    List<ShoppingCart> list(ShoppingCart shoppingCart);

    void updateNumberById(ShoppingCart shoppingCart);

    void insert(ShoppingCart shoppingCart);

    void deleteByUserId(Long currentId);

    void deleteById(Long id);
}
