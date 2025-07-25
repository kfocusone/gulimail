package com.example.gulimail.ware.dao;

import com.example.gulimail.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * 商品库存
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-25
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
	
}