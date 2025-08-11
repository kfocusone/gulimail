package com.example.gulimail.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * A 服务想要给 B 服务传递数据，那么spring cloud 就会通过传输
 * 协议或途径，先将数据封装成 json 数据，再由 B 服务接受json数据转换成 实体对象
 * 针对此，构建一个 to 的package，用来专门存储两个服务之间传递数据的格式
 */

@Data
public class SpuBoundTo {
    private Long spuId;

    private BigDecimal buyBounds;

    private BigDecimal growBounds;
}
