package com.example.gulimail.product.dao;

import com.example.gulimail.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * 商品三级分类
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-24
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {

    /**
     * 只返回 name，不返回其他数据，提前预写
     * @param catId
     * @return
     */
    @Select("SELECT name from pms_category where cat_id = {id}")
    String selectNameById(Long catId);
}