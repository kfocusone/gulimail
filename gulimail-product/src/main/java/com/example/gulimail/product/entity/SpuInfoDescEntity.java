package com.example.gulimail.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * spu信息介绍
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-24
 */
@Data
@TableName("pms_spu_info_desc")
public class SpuInfoDescEntity {

    /**
     * 商品id
     *  其不是自增的，而是随着让后端提交数据给数据库，因此设置为 input类型
     */
    @TableId(type = IdType.INPUT)
	private Long spuId;
    /**
     * 商品介绍
     */
	private String decript;
}