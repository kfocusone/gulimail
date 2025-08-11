package com.example.gulimail.product.service.impl;

import com.example.gulimail.common.to.SkuReductionTo;
import com.example.gulimail.common.to.SpuBoundTo;
import com.example.gulimail.product.entity.SpuImagesEntity;
import com.example.gulimail.product.feign.CouponFeignService;
import com.example.gulimail.product.service.SkuSaleAttrValueService;

import com.example.gulimail.product.vo.spuVo.SpuSaveVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.common.utils.Query;

import com.example.gulimail.product.dao.SpuInfoDao;
import com.example.gulimail.product.entity.SpuInfoEntity;
import com.example.gulimail.product.service.SpuInfoService;
import org.springframework.transaction.annotation.Transactional;

/**
 * spu信息
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-24
 */
@Service
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    private CouponFeignService couponFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveSpuInfo(SpuSaveVo vo) {
        //1. 保存 spu 基本信息 spu_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(vo, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.save(spuInfoEntity);

        // 2. 保存 spu 的描述图片 spu info desc
//        List<String> descript = vo.getDescript();
//        SpuInfoDescEntity descEntity = new SpuInfoDescEntity();
//        descEntity.setDecript(String.join(",", descript);
//        descEntity.setSpuId(spuInfoEntity.getId());

        // 3. 保存 spu 的图片集 spu_images
        SpuImagesEntity spuImagesEntity = new SpuImagesEntity();

        // 4. 保存 spu 的规格参数； pms_product_attr_value

        // 保存 spu 的积分信息 sms_spu_bounds
//        Bounds bounds = vo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
//        spuBoundTo.setBuyBounds(bounds.getBuyBounds);
//        spuBoundTo.setGrowBounds(bounds.getGrowBounds);
        spuBoundTo.setSpuId(spuImagesEntity.getId());
        couponFeignService.saveSpuBounds(spuBoundTo);

        //5. 保存 当前 spu 对应的所有 sku 信息
        List<?/*Skus*/> skus = vo.getSkus();
        if (skus != null && !skus.isEmpty()) {
            skus.forEach(item -> {

                // 5.1  sku 的基本信息 sku info
                // 5.2  sku 的图片信息 sku_images
                // 5.3  sku 的销售属性信息 sku sale attr value
                // 5.4  sku 的优惠、 满减等信息 sms_sku_ladder sms_member_price
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(item, skuReductionTo);
                Long skuId = 0L;
                skuReductionTo.setSkuId(skuId);
                couponFeignService.saveSkuReduction(skuReductionTo);
            });
        }
    }

}