package com.example.gulimail.common.constant;

public enum ProductEnum {
    ATTR_TYPE_BASE(1, "base"), ATTR_TYPR_SALE(0, "sale");

    private int code;
    private String msg;
    ProductEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
