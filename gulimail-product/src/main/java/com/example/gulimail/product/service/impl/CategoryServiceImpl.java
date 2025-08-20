package com.example.gulimail.product.service.impl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.example.gulimail.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
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

import javax.xml.crypto.Data;

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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    //TODO 产生堆外内存溢出 OutofDriverMemoryError 压测
    @Override
    public List<CategoryEntity> selectListTree() {
        /**
         * 1. 空结果缓存,解决缓存穿透
         * 2. 加随机时间,解决缓存雪崩
         * 3. 加锁,解决缓存击穿问题
         */
        // 加入缓存逻辑,缓存对象为 json字符串
        long l = System.currentTimeMillis();
        String listTree = stringRedisTemplate.opsForValue().get("listTree");
        if (StringUtils.hasText(listTree)) {
            List<CategoryEntity> list = JSONUtil.toList(listTree, CategoryEntity.class);
            System.out.println("redis缓存时间: " + (System.currentTimeMillis() - l));
            return list;
        }
        List<CategoryEntity> list = selectListTreeFromDb();
        // 使用 JSON 转换工具,将 list 转为 json 格式的字符串
//        String jsonString = JSONUtil.toJsonStr(list);
//        try {
//            Thread.sleep(2000);
//            if (StringUtils.hasText(listTree)) {
//
//                stringRedisTemplate.opsForValue().set("listTree", jsonString, 1, TimeUnit.DAYS);
//                System.out.println("写入redis 缓存...");
//            }
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }


        System.out.println("无redis缓存时间: " + (System.currentTimeMillis() - l));
        return list;
    }

    // 从数据库查询并封装分类数据
    public synchronized List<CategoryEntity> selectListTreeFromDb() {
        // 本地锁,需要先查看一下redis中是否有缓存数据
        String listTree = stringRedisTemplate.opsForValue().get("listTree");
        if (StringUtils.hasText(listTree)) {
            return JSONUtil.toList(listTree, CategoryEntity.class);
        }

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

        // 执行到此,表明redis中无数据.
        String jsonString = JSONUtil.toJsonStr(level0Menus);
        System.out.println("加锁后数据写入redis... ");
        stringRedisTemplate.opsForValue().set("listTree", jsonString, 1, TimeUnit.DAYS);
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