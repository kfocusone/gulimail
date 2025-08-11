package com.example.gulimail.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimail.common.to.SkuReductionTo;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author kfocus
 * @email kfocus@gmail.com
 * @date 2025-07-25 10:53:18
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuReduction(SkuReductionTo skuReductionTo);
}