package com.example.gulimail.product.dao;

import com.example.gulimail.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * 商品三级分类
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-24
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}