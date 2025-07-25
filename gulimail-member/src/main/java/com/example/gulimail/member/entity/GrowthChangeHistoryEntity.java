package com.example.gulimail.member.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 成长值变化历史记录
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-25
 */
@Data
@TableName("ums_growth_change_history")
public class GrowthChangeHistoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	    /**
     * id
     */
				@TableId
			private Long id;
	    /**
     * member_id
     */
			private Long memberId;
	    /**
     * create_time
     */
			private Date createTime;
	    /**
     * 改变的值（正负计数）
     */
			private Integer changeCount;
	    /**
     * 备注
     */
			private String note;
	    /**
     * 积分来源[0-购物，1-管理员修改]
     */
			private Integer sourceType;
	}