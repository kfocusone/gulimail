package com.example.gulimail.ware.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 库存工作单
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-25
 */
@Data
@TableName("wms_ware_order_task_detail")
public class WareOrderTaskDetailEntity implements Serializable {
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
     * sku_name
     */
			private String skuName;
	    /**
     * 购买个数
     */
			private Integer skuNum;
	    /**
     * 工作单id
     */
			private Long taskId;
	    /**
     * 仓库id
     */
			private Long wareId;
	    /**
     * 1-已锁定  2-已解锁  3-扣减
     */
			private Integer lockStatus;
	}