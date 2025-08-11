package com.example.gulimail.ware.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 采购信息
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-25
 */
@Data
@TableName("wms_purchase")
public class PurchaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	    /**
     * 
     */
				@TableId
			private Long id;
	    /**
     * 
     */
			private Long assigneeId;
	    /**
     * 
     */
			private String assigneeName;
	    /**
     * 
     */
			private String phone;
	    /**
     * 
     */
			private Integer priority;
	    /**
     * 
     */
			private Integer status;
	    /**
     * 
     */
			private Long wareId;
	    /**
     * 
     */
			private BigDecimal amount;
	    /**
     * 
     */
			private Date createTime;
	    /**
     * 
     */
			private Date updateTime;
	}