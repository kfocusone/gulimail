package com.example.gulimail.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.gulimail.product.entity.BrandEntity;
import com.example.gulimail.product.service.BrandService;
import com.example.gulimail.product.service.CategoryService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 单元测试
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class GulimailProductApplicationTests {

    @Resource
//    private BrandDao brandDao;
    BrandService brandService;

    @Resource
    CategoryService categoryService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void testRedisson() {
        System.out.println(redissonClient);
    }

    @Test
    public void testStringRedisTemplate() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("Hello", "Word_" + UUID.randomUUID());

        String hello = ops.get("Hello");
        System.out.println(hello);
    }

    @Test
    public void testPath() {
        Long[] catelogPath = categoryService.findCatelogPath(225L);
        log.info("完整路径：{}", Arrays.asList(catelogPath));
    }


    @Test
    public void contextLoads() {
       BrandEntity brandEntity = new BrandEntity();
       brandEntity.setBrandId(1L);
       brandEntity.setDescript("小米手机");

       brandService.updateById(brandEntity);
        System.out.println("修改成功...");
    }

    @Test
    public void listTest() {
        BrandEntity brandEntity = new BrandEntity();

        List<BrandEntity> brandId = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1l));
        brandId.forEach(System.out::println);

    }
}
