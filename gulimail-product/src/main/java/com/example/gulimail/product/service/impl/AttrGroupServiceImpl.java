package com.example.gulimail.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.common.utils.Query;

import com.example.gulimail.product.dao.AttrGroupDao;
import com.example.gulimail.product.entity.AttrGroupEntity;
import com.example.gulimail.product.service.AttrGroupService;
import org.springframework.util.StringUtils;

/**
 * 属性分组
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-24
 */
@Service
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        //            // 手动测试 传递数据
//            String current = (String) params.get("current");
//            String size = (String) params.get("size");
//            IPage<AttrGroupEntity> page = new Page<>(Long.parseLong(current), Long.parseLong(size));
//
//            String key = (String) params.get("key");
//
//            // 2. 构建查询条件
//            // select * from pms_attr_group where catelog_id = ? and (attr_group_id = ? or attr_group_name like %?%;
//            LambdaQueryWrapper<AttrGroupEntity> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.eq(AttrGroupEntity::getCatelogId, catelogId);
//            if (StringUtils.hasText(key)) {
//
//                queryWrapper.and((wrapper) -> {
//                    wrapper.eq(AttrGroupEntity::getAttrGroupId, key).or().like(AttrGroupEntity::getAttrGroupName, key);
//                });
//            }
//
////            List<AttrGroupEntity> result = attrGroupService.list(page, queryWrapper);
//            IPage<AttrGroupEntity> result = attrGroupService.page(page, queryWrapper);
//
//            return R.ok().put("page", result).put("cate", categoryService.getById(catelogId));

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {

        if (catelogId == 0) {
            return queryPage(params);
        } else {
            // 前后端商定好需要传递的参数名称 "key"
            String key = (String) params.get("key");

            // 构建 查询条件 LammbdQueryWrapper
            LambdaQueryWrapper<AttrGroupEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(AttrGroupEntity::getCatelogId, catelogId);
            if (StringUtils.hasText(key)) {

                queryWrapper.and((wrapper -> {
                    wrapper.eq(AttrGroupEntity::getAttrGroupId, key)
                            .or()
                            .like(AttrGroupEntity::getAttrGroupName, key);
                }));
            }

            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params), queryWrapper);

            return new PageUtils(page);
        }
    }

}