package com.example.gulimail.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 秒杀活动场次
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-25
 */
@Data
@TableName("sms_seckill_session")
public class SeckillSessionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	    /**
     * id
     */
				@TableId
			private Long id;
	    /**
     * 场次名称
     */
			private String name;
	    /**
     * 每日开始时间
     */
			private Date startTime;
	    /**
     * 每日结束时间
     */
			private Date endTime;
	    /**
     * 启用状态
     */
			private Integer status;
	    /**
     * 创建时间
     */
			private Date createTime;
	}