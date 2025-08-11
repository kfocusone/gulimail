package com.example.gulimail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.product.entity.SpuInfoEntity;
import com.example.gulimail.product.vo.spuVo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author kfocus
 * @email kfocus@gmail.com
 * @date 2025-07-24 17:34:07
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo vo);
}