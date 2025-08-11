package com.example.gulimail.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.ware.entity.PurchaseEntity;
import com.example.gulimail.ware.vo.MergeVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author kfocus
 * @email kfocus@gmail.com
 * @date 2025-07-25 11:50:36
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryUnreceivePage(Map<String, Object> params);

    void merge(MergeVo vo);

    void received(List<Long> ids);

}