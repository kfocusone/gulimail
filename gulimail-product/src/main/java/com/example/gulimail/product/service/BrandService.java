package com.example.gulimail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author kfocus
 * @email kfocus@gmail.com
 * @date 2025-07-24 17:34:07
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateDetail(BrandEntity brand);
}