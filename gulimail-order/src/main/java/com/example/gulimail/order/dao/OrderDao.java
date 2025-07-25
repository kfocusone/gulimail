package com.example.gulimail.order.dao;

import com.example.gulimail.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * 订单
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-25
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}