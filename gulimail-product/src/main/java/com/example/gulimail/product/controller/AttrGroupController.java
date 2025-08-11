package com.example.gulimail.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gulimail.product.entity.AttrEntity;
import com.example.gulimail.product.service.AttrService;
import com.example.gulimail.product.service.CategoryService;
import com.example.gulimail.product.vo.AttrGroupVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.example.gulimail.product.entity.AttrGroupEntity;
import com.example.gulimail.product.service.AttrGroupService;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.common.utils.R;



/**
 * 属性分组
 *
 * @author kfocus
 * @email kfocus@gmail.com
 * @date 2025-07-24 17:54:23
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrService attrService;

    @Operation(summary = "获取分类下所有分组和关联属性")
    @GetMapping("/{catelogId}/withattr")
    public R groupAndRelation(@PathVariable("catelogId") Long catelogId) {
        List<AttrGroupVo> attrGroupVos = attrGroupService.selectByCatId(catelogId);
        return R.ok().put("data", attrGroupVos);
    }

    @GetMapping("/{attrGroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrGroupId") Long attrGroupId) {
        //
        List<AttrEntity> entities = attrService.getRelationAttr(attrGroupId);

        return R.ok().put("data", entities);
    }

    /**
     * 列表
     * 属性分组需要传递其所属的三级分类组别，使用 {} 传参
     */
    @Operation(summary = "根据分类id查询")
    @RequestMapping("/list/{catelogId}")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("catelogId") Long catelogId){

        PageUtils page = attrGroupService.queryPage(params, catelogId);

        return R.ok().put("page", page);
    }


    /**
     * 信息 希望给前端传递 attrGroup，以及 catelogPath
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);

        Long catelogId = attrGroup.getCatelogId();

        Long[] path = categoryService.findCatelogPath(catelogId);

        attrGroup.setCatelogPath(path);

        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
            attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
            attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
            attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
