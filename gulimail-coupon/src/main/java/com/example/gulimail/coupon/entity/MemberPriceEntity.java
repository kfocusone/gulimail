package com.example.gulimail.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 商品会员价格
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-25
 */
@Data
@TableName("sms_member_price")
public class MemberPriceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	    /**
     * id
     */
				@TableId
			private Long id;
	    /**
     * sku_id
     */
			private Long skuId;
	    /**
     * 会员等级id
     */
			private Long memberLevelId;
	    /**
     * 会员等级名
     */
			private String memberLevelName;
	    /**
     * 会员对应价格
     */
			private BigDecimal memberPrice;
	    /**
     * 可否叠加其他优惠[0-不可叠加优惠，1-可叠加]
     */
			private Integer addOther;
	}