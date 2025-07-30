package com.example.gulimail.product.service.impl;

import com.example.gulimail.product.service.CategoryBrandRelationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.common.utils.Query;

import com.example.gulimail.product.dao.BrandDao;
import com.example.gulimail.product.entity.BrandEntity;
import com.example.gulimail.product.service.BrandService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 品牌
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-24
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Resource
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                new QueryWrapper<BrandEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void updateDetail(BrandEntity brand) {
        // 保证冗余字段的数据一致性
        this.updateById(brand);
        if (StringUtils.hasText(brand.getName())) {
            // 更新关联表的名称
            categoryBrandRelationService.updateBrand(brand.getBrandId(), brand.getName());

            // 后续有其他表，也关联此表名称信息，也做相应的修改
        }
    }

}