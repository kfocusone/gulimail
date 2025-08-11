package com.example.gulimail.product.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.example.gulimail.common.to.MemberCollectSpuTO;
import com.example.gulimail.common.to.SpuBoundTo;
import com.example.gulimail.product.feign.CouponFeignService;
import com.example.gulimail.product.feign.MemberFeignService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.example.gulimail.product.entity.SkuInfoEntity;
import com.example.gulimail.product.service.SkuInfoService;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.common.utils.R;



/**
 * sku信息
 *
 * @author kfocus
 * @email kfocus@gmail.com
 * @date 2025-07-24 17:54:23
 */
@RestController
@RequestMapping("product/skuinfo")
public class SkuInfoController {
    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private MemberFeignService memberFeignService;

    @Autowired
    private CouponFeignService couponFeignService;

    /**
     * 多模块联动测试
     *  调用 coupon 模块的方法，将数据保存到其中，数据写死进行测试
     *  调用 member 模块的方法，同上
     *  会使用到 OpenFeign，
     */
    @Transactional
    @Operation(summary = "测试多模块 OpenFeign")
    @PostMapping("/saveFeign")
    public R saveFeign(@RequestParam Long id) {
        // 虽然传 id 但是未使用，这就是一个测试，
        // 调用 Member 的测试 这里其实应该是使用 impl，就不具体再那样了
        MemberCollectSpuTO spuTO = new MemberCollectSpuTO();
        spuTO.setSpuImg("http://gips3.baidu.com/it/u=1821127123,1149655687&fm=3028&app=3028&f=JPEG&fmt=auto?w=720&h=1280");
        spuTO.setSpuName("feignTest");
        spuTO.setCreateTime(new Date());
        spuTO.setSpuId(1L);
        spuTO.setMemberId(1L);
        spuTO.setId(1L);
        memberFeignService.saveMemberCollectSpu(spuTO);

        // 调用 coupon 的测试
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        spuBoundTo.setBuyBounds(new BigDecimal("0"));
        spuBoundTo.setSpuId(1L);
        spuBoundTo.setGrowBounds(new BigDecimal("40"));
        couponFeignService.saveSpuBounds(spuBoundTo);

        SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
        skuInfoEntity.setSkuId(1L);
        skuInfoEntity.setSkuName("feignTest");
        skuInfoEntity.setSpuId(1L);
        skuInfoEntity.setPrice(new BigDecimal("12.58"));
        skuInfoService.save(skuInfoEntity);

        R ok = R.ok();
        ok.put("data-spuTO", spuTO);
        ok.put("data-spuBoundTO", spuBoundTo);
        ok.put("data-skuInfo", skuInfoEntity);
        return ok;

    }



    /**
     * 列表
     * @Params page/limit/sidx/order/key/catelogId/brandId/min/max
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:skuinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = skuInfoService.queryPageCondition(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{skuId}")
    //@RequiresPermissions("product:skuinfo:info")
    public R info(@PathVariable("skuId") Long skuId){
            SkuInfoEntity skuInfo = skuInfoService.getById(skuId);

        return R.ok().put("skuInfo", skuInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:skuinfo:save")
    public R save(@RequestBody SkuInfoEntity skuInfo){
            skuInfoService.save(skuInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:skuinfo:update")
    public R update(@RequestBody SkuInfoEntity skuInfo){
            skuInfoService.updateById(skuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:skuinfo:delete")
    public R delete(@RequestBody Long[] skuIds){
            skuInfoService.removeByIds(Arrays.asList(skuIds));

        return R.ok();
    }

}
