package com.example.gulimail.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.example.gulimail.product.vo.AttrRespVo;
import com.example.gulimail.product.vo.AttrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.gulimail.product.entity.AttrEntity;
import com.example.gulimail.product.service.AttrService;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.common.utils.R;



/**
 * 商品属性
 *
 * @author kfocus
 * @email kfocus@gmail.com
 * @date 2025-07-24 17:54:23
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    // product/attr/sale/list/{?} 销售属性
    // product/attr/base/list/{?} 基本属性
    @GetMapping("/{attrType}/list/{catelogId}")
    public R baseList(@RequestParam Map<String, Object> params,
                      @PathVariable("catelogId") Long catelogId,
                      @PathVariable("attrType") String type) {

        PageUtils page =  attrService.queryBaseAttr(params, catelogId, type);
        return R.ok().put("page", page);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId){
        AttrRespVo respVo = attrService.getAttrInfo(attrId);

        return R.ok().put("attr", respVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVO attrVO){
//            attrService.save(attr);
        attrService.saveAttr(attrVO);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrVO attr){
            attrService.updateAttr(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds){
            attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
