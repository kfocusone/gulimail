package com.example.gulimail.common.to;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MemberPrice {

    private Integer id;

    private String name;

    private BigDecimal price;
}
