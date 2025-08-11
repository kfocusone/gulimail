package com.example.gulimail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.common.valid.SaveValid;
import com.example.gulimail.common.valid.UpdateStatus;
import com.example.gulimail.common.valid.UpdateVaild;
import com.example.gulimail.product.entity.BrandEntity;
import com.example.gulimail.product.entity.CategoryBrandRelationEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author kfocus
 * @email kfocus@gmail.com
 * @date 2025-07-24 17:34:07
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

    void updateBrand( Long brandId,  String name);

    void updateCatelog(Long catId, String name);

    List<BrandEntity> getBrandsById(Long catId);
}