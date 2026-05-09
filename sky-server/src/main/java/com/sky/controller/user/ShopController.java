package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Api(tags = "店铺相关接口")
@Slf4j
public class ShopController {

    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取店铺的营业状态
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("获取店铺的营业状态")
    public Result<Integer> getStatus() {
        try {
            Object value = redisTemplate.opsForValue().get(KEY);
            Integer status = value instanceof Integer ? (Integer) value : null;
            log.info("获取到店铺的营业状态为：{}, value class: {}", status != null && status == 1 ? "营业中" : "打烊中", value != null ? value.getClass().getName() : "null");
            return Result.success(status != null ? status : 0);
        } catch (Exception e) {
            log.error("获取店铺状态失败", e);
            return Result.error(e.getMessage());
        }
    }

}