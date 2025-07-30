package com.example.gulimail.product.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.gulimail.product.entity.BrandEntity;
import com.example.gulimail.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.gulimail.product.entity.CategoryBrandRelationEntity;
import com.example.gulimail.product.service.CategoryBrandRelationService;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.common.utils.R;



/**
 * 品牌分类关联
 *
 * @author kfocus
 * @email kfocus@gmail.com
 * @date 2025-07-24 17:54:23
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    private BrandService brandService;

    /**
     * 获取当前品牌关联的所有分类
     */
    @GetMapping("/catelog/list")
    public R catelogList(@RequestParam("brandId") Long brandId) {

        LambdaQueryWrapper<CategoryBrandRelationEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CategoryBrandRelationEntity::getBrandId, brandId);
        List<CategoryBrandRelationEntity> list = categoryBrandRelationService.list(queryWrapper);

        return R.ok().put("brandIdRela", list);
    }

    /**
     * 获取当前分类关联的所有品牌
     * 1.app:处理请求,接受和校验数据(即将数据封装成service要求的形式)
     * 2.service接受controller传来的数据,进行业务处理
     * 3.Controller接受Service处理完的数据,封装成页面指定的vo
     */
    @GetMapping("/brands/list")
    public R relaionBrandList(@RequestParam(value = "catId", required = true) Long catId) {

        LambdaQueryWrapper<CategoryBrandRelationEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CategoryBrandRelationEntity::getCatelogId, catId);
        List<CategoryBrandRelationEntity> cateList = categoryBrandRelationService.list(queryWrapper);

        List<BrandEntity> brandEntities = new ArrayList<>();
        for (CategoryBrandRelationEntity entity : cateList) {
            brandEntities.add(brandService.getById(entity.getBrandId()));
        }


        return R.ok().put("brands", brandEntities);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:categorybrandrelation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:categorybrandrelation:info")
    public R info(@PathVariable("id") Long id){
            CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:categorybrandrelation:save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
        categoryBrandRelationService.saveDetail(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:categorybrandrelation:update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
            categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:categorybrandrelation:delete")
    public R delete(@RequestBody Long[] ids){
            categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
