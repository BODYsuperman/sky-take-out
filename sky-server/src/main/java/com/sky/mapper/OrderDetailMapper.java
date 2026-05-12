package com.sky.mapper;

import com.sky.entity.OrderDetail;

import java.util.List;

public interface OrderDetailMapper {
    void insertBatch(List<OrderDetail> orderDetailList);


    /**
     * 查询订单明细
     * @param ordersId
     * @return
     */
    List<OrderDetail> getByOrderId(Long ordersId);
}
