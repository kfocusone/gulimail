package com.example.gulimail.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 商品满减信息
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-25
 */
@Data
@TableName("sms_sku_full_reduction")
public class SkuFullReductionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	    /**
     * id
     */
				@TableId
			private Long id;
	    /**
     * spu_id
     */
			private Long skuId;
	    /**
     * 满多少
     */
			private BigDecimal fullPrice;
	    /**
     * 减多少
     */
			private BigDecimal reducePrice;
	    /**
     * 是否参与其他优惠
     */
			private Integer addOther;
	}