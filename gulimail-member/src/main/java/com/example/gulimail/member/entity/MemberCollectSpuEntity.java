package com.example.gulimail.member.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 会员收藏的商品
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-25
 */
@Data
@TableName("ums_member_collect_spu")
public class MemberCollectSpuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	    /**
     * id
     */
				@TableId
			private Long id;
	    /**
     * 会员id
     */
			private Long memberId;
	    /**
     * spu_id
     */
			private Long spuId;
	    /**
     * spu_name
     */
			private String spuName;
	    /**
     * spu_img
     */
			private String spuImg;
	    /**
     * create_time
     */
			private Date createTime;
	}