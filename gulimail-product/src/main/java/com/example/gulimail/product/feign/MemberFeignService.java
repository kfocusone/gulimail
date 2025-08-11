package com.example.gulimail.product.feign;

import com.example.gulimail.common.to.MemberCollectSpuTO;
import com.example.gulimail.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// 传入想要调用的模块名, 如果没有使用网关，需要传入 url 值
@FeignClient("gulimail-member")
@Component
public interface MemberFeignService {

    @PostMapping("/member/membercollectspu/save")
    R saveMemberCollectSpu(@RequestBody MemberCollectSpuTO memberCollectSpuTO); // 传输数据使用 TO， 这样就不需要各个模块互相依赖导入了


}
