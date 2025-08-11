package com.example.gulimail.member.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.gulimail.member.entity.MemberLevelEntity;
import com.example.gulimail.member.service.MemberLevelService;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.common.utils.R;

/**
 * 会员等级
 *
 * @author kfocus
 * @email kfocus@gmail.com
 * @date 2025-07-25 11:26:51
 */
@RestController
@RequestMapping("member/memberlevel")
public class MemberLevelController {
    @Autowired
    private MemberLevelService memberLevelService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("member:memberlevel:list")
    public R list(@RequestParam Map<String, Object> params){
        System.out.println("网关执行成功...");
        PageUtils page = memberLevelService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:memberlevel:info")
    public R info(@PathVariable("id") Long id){
            MemberLevelEntity memberLevel = memberLevelService.getById(id);

        return R.ok().put("memberLevel", memberLevel);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:memberlevel:save")
    public R save(@RequestBody MemberLevelEntity memberLevel){
            memberLevelService.save(memberLevel);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("member:memberlevel:update")
    public R update(@RequestBody MemberLevelEntity memberLevel){
            memberLevelService.updateById(memberLevel);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("member:memberlevel:delete")
    public R delete(@RequestBody Long[] ids){
            memberLevelService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
