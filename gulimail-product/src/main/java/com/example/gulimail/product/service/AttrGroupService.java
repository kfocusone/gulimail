package com.example.gulimail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.product.entity.AttrGroupEntity;
import com.example.gulimail.product.vo.AttrGroupVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author kfocus
 * @email kfocus@gmail.com
 * @date 2025-07-24 17:34:07
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long catelogId);

    List<AttrGroupVo> selectByCatId(Long catelogId);
}