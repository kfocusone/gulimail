package com.example.gulimail.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 退货原因
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-25
 */
@Data
@TableName("oms_order_return_reason")
public class OrderReturnReasonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	    /**
     * id
     */
				@TableId
			private Long id;
	    /**
     * 退货原因名
     */
			private String name;
	    /**
     * 排序
     */
			private Integer sort;
	    /**
     * 启用状态
     */
			private Integer status;
	    /**
     * create_time
     */
			private Date createTime;
	}