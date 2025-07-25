package com.example.gulimail.ware.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-25
 */
@Data
@TableName("undo_log")
public class UndoLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	    /**
     * 
     */
				@TableId
			private Long id;
	    /**
     * 
     */
			private Long branchId;
	    /**
     * 
     */
			private String xid;
	    /**
     * 
     */
			private String context;
	    /**
     * 
     */
			private unknowType rollbackInfo;
	    /**
     * 
     */
			private Integer logStatus;
	    /**
     * 
     */
			private Date logCreated;
	    /**
     * 
     */
			private Date logModified;
	    /**
     * 
     */
			private String ext;
	}