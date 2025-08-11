package com.example.gulimail.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.gulimail.common.constant.WareConstant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.common.utils.Query;

import com.example.gulimail.ware.dao.PurchaseDetailDao;
import com.example.gulimail.ware.entity.PurchaseDetailEntity;
import com.example.gulimail.ware.service.PurchaseDetailService;
import org.springframework.util.StringUtils;

/**
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-25
 */
@Service
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        /**
         * status: 0
         * wareId: 1
         */
        LambdaQueryWrapper<PurchaseDetailEntity> queryWrapper = new LambdaQueryWrapper<>();
        String key = params.get("key").toString();
        if (StringUtils.hasText(key)) {
            queryWrapper.and(wrapper -> {
                wrapper.eq(PurchaseDetailEntity::getPurchaseId, key).or().like(PurchaseDetailEntity::getSkuId, key);
            });
        }

        String status = (String) params.get("status");
        if (StringUtils.hasText(status)) {
            queryWrapper.eq(PurchaseDetailEntity::getStatus, status);
        }

        String wareId = params.get("wareId").toString();
        if (StringUtils.hasText(wareId)) {
            queryWrapper.eq(PurchaseDetailEntity::getWareId, wareId);
        }
        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<PurchaseDetailEntity> listDetailByPurchased(Long id) {
        LambdaQueryWrapper<PurchaseDetailEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PurchaseDetailEntity::getPurchaseId, id);
        return this.list(queryWrapper);
    }

}