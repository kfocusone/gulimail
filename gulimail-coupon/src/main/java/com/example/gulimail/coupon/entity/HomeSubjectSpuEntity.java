package com.example.gulimail.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 专题商品
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-25
 */
@Data
@TableName("sms_home_subject_spu")
public class HomeSubjectSpuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	    /**
     * id
     */
				@TableId
			private Long id;
	    /**
     * 专题名字
     */
			private String name;
	    /**
     * 专题id
     */
			private Long subjectId;
	    /**
     * spu_id
     */
			private Long spuId;
	    /**
     * 排序
     */
			private Integer sort;
	}