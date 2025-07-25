package com.example.gulimail.ware.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 商品库存
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-25
 */
@Data
@TableName("wms_ware_sku")
public class WareSkuEntity implements Serializable {
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
     * 仓库id
     */
			private Long wareId;
	    /**
     * 库存数
     */
			private Integer stock;
	    /**
     * sku_name
     */
			private String skuName;
	    /**
     * 锁定库存
     */
			private Integer stockLocked;
	}