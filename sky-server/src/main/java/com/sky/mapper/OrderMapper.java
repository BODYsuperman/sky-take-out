package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    void insert(Orders order);
    /**
     * 根据订单号和用户id查询订单
     * @param orderNumber
     * @param userId
     * @return
     */
    Orders getByNumberAndUserId(@Param("orderNumber") String orderNumber, @Param("userId") Long userId);

    void update(Orders orders);
}
