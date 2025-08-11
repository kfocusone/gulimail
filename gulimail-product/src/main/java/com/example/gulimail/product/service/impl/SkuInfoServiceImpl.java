package com.example.gulimail.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.common.utils.Query;

import com.example.gulimail.product.dao.SkuInfoDao;
import com.example.gulimail.product.entity.SkuInfoEntity;
import com.example.gulimail.product.service.SkuInfoService;
import org.springframework.util.StringUtils;

/**
 * sku信息
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-24
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * @param params page/limit/sidx/order/key/catelogId/brandId/min/max
     * @return
     */
    @Override
    public PageUtils queryPageCondition(Map<String, Object> params) {
        LambdaQueryWrapper<SkuInfoEntity> queryWrapper = new LambdaQueryWrapper<>();

        String key = (String) params.get("key");
        if (StringUtils.hasText(key)) {
            queryWrapper.and((wrapper) -> {
                wrapper.eq(SkuInfoEntity::getSkuId, key).or()
                        .like(SkuInfoEntity::getSkuName, key);
            });

        }

        String catelogId = (String) params.get("catelogId");
        if (StringUtils.hasText(catelogId)) {
            queryWrapper.eq(SkuInfoEntity::getCatalogId, catelogId);
        }

        String brandId = (String) params.get("brandId");
        if (StringUtils.hasText(brandId)) {
            queryWrapper.eq(SkuInfoEntity::getBrandId, brandId);
        }

        // 需要判断不为0的情况，即不存在对应值的情况
        String min = (String) params.get("min");
        if (StringUtils.hasText(min) && !"0".equalsIgnoreCase(min)) {
            queryWrapper.ge(SkuInfoEntity::getPrice, min);
        }

        String max = (String) params.get("max");
        if (StringUtils.hasText(max) && "0".equalsIgnoreCase(max)) {
            queryWrapper.le(SkuInfoEntity::getPrice, max);
        }


        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}