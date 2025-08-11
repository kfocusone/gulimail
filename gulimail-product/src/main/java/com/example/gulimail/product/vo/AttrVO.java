package com.example.gulimail.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 一个 VO 对象的操作
 * 1. 创建需要的 VO 对象，为 vo 对象赋予需要的属性
 * 2. 对应的 controller 层中，使用 vo类 作为参数传递
 * 3. 在 service 层创建方法，并在 impl 中实现
 */
@Data
public class AttrVO {

    /**
     * 属性id
     */
    private Long attrId;
    /**
     * 属性名
     */
    private String attrName;
    /**
     * 是否需要检索[0-不需要，1-需要]
     */
    private Integer searchType;
    /**
     * 属性图标
     */
    private String icon;
    /**
     * 可选值列表[用逗号分隔]
     */
    private String valueSelect;
    /**
     * 属性类型[0-销售属性，1-基本属性，2-既是销售属性又是基本属性]
     */
    private Integer attrType;
    /**
     * 启用状态[0 - 禁用，1 - 启用]
     */
    private Long enable;
    /**
     * 所属分类
     */
    private Long catelogId;
    /**
     * 快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整
     */
    private Integer showDesc;

    private Long attrGroupId;
}
