package com.example.gulimail.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.gulimail.product.controller.CategoryController;
import com.example.gulimail.product.dao.BrandDao;
import com.example.gulimail.product.entity.BrandEntity;
import com.example.gulimail.product.service.BrandService;
import com.example.gulimail.product.service.CategoryService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

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
