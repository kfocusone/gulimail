package com.example.gulimail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.product.entity.AttrEntity;
import com.example.gulimail.product.vo.AttrRespVo;
import com.example.gulimail.product.vo.AttrVO;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author kfocus
 * @email kfocus@gmail.com
 * @date 2025-07-24 17:34:07
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVO attrVO);

    PageUtils queryBaseAttr(Map<String, Object> params, Long catelogId, String type);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVO attr);

    List<AttrEntity> getRelationAttr(Long attrGroupId);
}