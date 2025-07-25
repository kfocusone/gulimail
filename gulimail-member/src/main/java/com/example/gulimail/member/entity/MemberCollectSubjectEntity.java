package com.example.gulimail.member.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 会员收藏的专题活动
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-25
 */
@Data
@TableName("ums_member_collect_subject")
public class MemberCollectSubjectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	    /**
     * id
     */
				@TableId
			private Long id;
	    /**
     * subject_id
     */
			private Long subjectId;
	    /**
     * subject_name
     */
			private String subjectName;
	    /**
     * subject_img
     */
			private String subjectImg;
	    /**
     * 活动url
     */
			private String subjectUrll;
	}