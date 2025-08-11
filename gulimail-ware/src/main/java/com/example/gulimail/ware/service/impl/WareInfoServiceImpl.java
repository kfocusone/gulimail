package com.example.gulimail.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.common.utils.Query;

import com.example.gulimail.ware.dao.WareInfoDao;
import com.example.gulimail.ware.entity.WareInfoEntity;
import com.example.gulimail.ware.service.WareInfoService;
import org.springframework.util.StringUtils;

/**
 * 仓库信息
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-25
 */
@Service
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        LambdaQueryWrapper<WareInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        String key = params.get("key").toString();
        if (StringUtils.hasText(key)) {
            queryWrapper.eq(WareInfoEntity::getId, key)
                    .or().like(WareInfoEntity::getName, key)
                    .or().like(WareInfoEntity::getAddress, key)
                    .or().like(WareInfoEntity::getAreacode, key);
        }

        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}