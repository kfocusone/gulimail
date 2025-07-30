package com.example.gulimail.product.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.example.gulimail.common.valid.SaveValid;
import com.example.gulimail.common.valid.UpdateStatus;
import com.example.gulimail.common.valid.UpdateVaild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.gulimail.product.entity.BrandEntity;
import com.example.gulimail.product.service.BrandService;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.common.utils.R;

import javax.validation.Valid;


/**
 * 品牌
 *
 * @author kfocus
 * @email kfocus@gmail.com
 * @date 2025-07-24 17:54:23
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:brand:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    //@RequiresPermissions("product:brand:info")
    public R info(@PathVariable("brandId") Long brandId) {
        BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     * @Valid 开启校验
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:brand:save")
    public R save(@Validated({SaveValid.class}) @RequestBody BrandEntity brand/*, BindingResult result*/) {
//        if (result.hasErrors()) {
//            Map<String, String> map = new HashMap<>();
//            // 1. 获取校验错误结果
//            result.getFieldErrors().forEach((item) -> {
//                // 获取到错误提示
//                String message = item.getDefaultMessage();
//
//                // 获取错误属性的名字
//                String field = item.getField();
//                map.put(field, message);
//            });
//
//            return R.error(400, "提交的数据不合法").put("data", map);
//        }

        brandService.save(brand);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:brand:update")
    public R update(@Validated({UpdateVaild.class}) @RequestBody BrandEntity brand) {
//        brandService.updateById(brand);
        // 当需要更新 brandName 的时候，并不能只更改当前表的名称，还需要修改其他关联表中的名称
        brandService.updateDetail(brand);
        return R.ok();
    }

    @PostMapping("/update/status")
    //@RequiresPermissions("product:brand:update")
    public R updateStatus(@Validated({UpdateStatus.class}) @RequestBody BrandEntity brand) {
        brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:brand:delete")
    public R delete(@RequestBody Long[] brandIds) {
        brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
