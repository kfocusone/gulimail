package com.example.gulimail.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 订单操作历史记录
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-25
 */
@Data
@TableName("oms_order_operate_history")
public class OrderOperateHistoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	    /**
     * id
     */
				@TableId
			private Long id;
	    /**
     * 订单id
     */
			private Long orderId;
	    /**
     * 操作人[用户；系统；后台管理员]
     */
			private String operateMan;
	    /**
     * 操作时间
     */
			private Date createTime;
	    /**
     * 订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】
     */
			private Integer orderStatus;
	    /**
     * 备注
     */
			private String note;
	}