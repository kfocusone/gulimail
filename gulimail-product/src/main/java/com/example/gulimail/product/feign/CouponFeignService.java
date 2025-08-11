package com.example.gulimail.product.feign;

import com.example.gulimail.common.to.SkuReductionTo;
import com.example.gulimail.common.to.SpuBoundTo;
import com.example.gulimail.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient("gulimail-coupon")
public interface CouponFeignService {

    /**
     * 1. @RequestBody 将这个对象转为 json 数据
     * 2. 找到 gulimall-coupon 服务， 给 /** 发送请求
     *      将 json 数据放到 post 请求的 请求体位置，发生请求
     * 3. 对方服务收到请求，请求体里的 json 数据转。
     *      @RequestBody SpuBoundsEntity spuBounds
     *      将请求体的数据转为 spuBounds。
     *      只要 两者的数据名一一对应就能封装
     * @param spuBoundTo
     * @return
     */
    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("/coupon/skufullreduction/saveInfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
