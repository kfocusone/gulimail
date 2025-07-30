package com.example.gulimail.product.service.impl;

import com.example.gulimail.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.common.utils.Query;

import com.example.gulimail.product.dao.CategoryDao;
import com.example.gulimail.product.entity.CategoryEntity;
import com.example.gulimail.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 商品三级分类
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-24
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> selectListTree() {
        // 1. 先查出数据库中的所有数据
        List<CategoryEntity> entities = baseMapper.selectList(null);

        // 2. 找出各分类及其子分类
        List<CategoryEntity> level0Menus = entities.stream().filter((categoryEntity) -> {
                    return categoryEntity.getParentCid() == 0;
                }).map((menu) -> {
                            menu.setChildren(getChildrens(menu, entities));
                            return menu;
                }).sorted((menu1, menu2) -> {
                    return (menu1.getSort() == null ? 0:menu1.getSort()) - (menu2.getCatLevel() == null ? 0:menu2.getSort());
                }).collect(Collectors.toList());

        return level0Menus;
    }

    @Override
    public void removeMenuByIds(List<Long> list) {
        // TODO 删除前检察是否被其他地方调用
//        List<Long> dList = new ArrayList<>();
//        List<CategoryEntity> collect = baseMapper.selectBatchIds(list).stream().filter(categoryEntity -> {
//            return categoryEntity.getProductCount() == 0;
//        }).toList();
//        for (CategoryEntity entity : collect) {
//            dList.add(entity.getCatId());
//        }
        List<Long> dList = list.stream().filter(id -> {
            return baseMapper.selectById(id).getProductCount() == 0;
        }).collect(Collectors.toList());

        baseMapper.deleteBatchIds(dList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {

        List<Long> path = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, path);

        Collections.reverse(parentPath);

        return parentPath.toArray(new Long[parentPath.size()]);
    }

    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        if (StringUtils.hasText(category.getName())) {

            categoryBrandRelationService.updateCatelog(category.getCatId(), category.getName());
        }
    }

    // 递归查找路径
    private List<Long> findParentPath(Long catelogId, List<Long> path) {
        path.add(catelogId);
        CategoryEntity entity = this.getById(catelogId);
        if (entity.getParentCid() != 0) {
            findParentPath(entity.getParentCid(), path);
        }
        return path;
    }


    // 递归查找所有菜单的子菜单
    private List<CategoryEntity> getChildrens(CategoryEntity root, List<CategoryEntity> all) {

        List<CategoryEntity> childrens = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == root.getCatId();
        }).map(categoryEntity -> {
            categoryEntity.setChildren(getChildrens(categoryEntity, all));
            return categoryEntity;
        }).sorted((menu1, menu2) -> {
            return (menu1.getSort() == null ? 0:menu1.getSort()) - (menu2.getCatLevel() == null ? 0:menu2.getSort());
        }).collect(Collectors.toList());
        return childrens;
    }

}