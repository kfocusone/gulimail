package com.example.gulimail.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimail.product.dao.BrandDao;
import com.example.gulimail.product.dao.CategoryDao;
import com.example.gulimail.product.entity.BrandEntity;
import com.example.gulimail.product.service.BrandService;
import com.example.gulimail.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.common.utils.Query;

import com.example.gulimail.product.dao.CategoryBrandRelationDao;
import com.example.gulimail.product.entity.CategoryBrandRelationEntity;
import com.example.gulimail.product.service.CategoryBrandRelationService;

/**
 * 品牌分类关联
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-24
 */
@Service
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

//    @Autowired
//    private BrandService brandService;

    @Autowired
    private BrandDao brandDao;

//    @Autowired
//    private CategoryService categoryService;


    @Autowired
    private CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCatelogId();

//        String cateName = categoryService.getById(catelogId).getName();
        String cateName = categoryDao.selectNameById(catelogId);
//        String brandName = brandServce.getById(brandId).getName();
        String brandName = brandDao.selectById(brandId).getName();


        categoryBrandRelation.setBrandName(brandName);
        categoryBrandRelation.setCatelogName(cateName);

        this.save(categoryBrandRelation);

    }

    @Override
    public void updateBrand(Long brandId, String name) {
        LambdaUpdateWrapper<CategoryBrandRelationEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CategoryBrandRelationEntity::getBrandId, brandId);
        updateWrapper.set(CategoryBrandRelationEntity::getBrandName, name);
        this.update(updateWrapper);
    }

    @Override
    public void updateCatelog(Long catId, String name) {
        LambdaUpdateWrapper<CategoryBrandRelationEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CategoryBrandRelationEntity::getBrandId, catId);
        updateWrapper.set(CategoryBrandRelationEntity::getBrandName, name);
        this.update(updateWrapper);
    }

    @Override
    public List<BrandEntity> getBrandsById(Long catId) {
        LambdaQueryWrapper<CategoryBrandRelationEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CategoryBrandRelationEntity::getCatelogId, catId);

        List<BrandEntity> brandEntities = this.list(queryWrapper).stream().map(item -> {
            Long brandId = item.getBrandId();
            return brandDao.selectById(brandId);
        }).collect(Collectors.toList());

        return brandEntities;
    }

}