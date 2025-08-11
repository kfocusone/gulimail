package com.example.gulimail.product.vo;

import lombok.Data;

@Data
public class AttrRespVo extends AttrVO{

    private String catelogName;

    private String attrGroupName;

    private Long[] catelogPath;
}
