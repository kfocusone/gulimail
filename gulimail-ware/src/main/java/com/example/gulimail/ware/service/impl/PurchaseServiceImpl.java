package com.example.gulimail.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.gulimail.common.constant.WareConstant;
import com.example.gulimail.ware.entity.PurchaseDetailEntity;
import com.example.gulimail.ware.service.PurchaseDetailService;
import com.example.gulimail.ware.vo.MergeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.common.utils.Query;

import com.example.gulimail.ware.dao.PurchaseDao;
import com.example.gulimail.ware.entity.PurchaseEntity;
import com.example.gulimail.ware.service.PurchaseService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 采购信息
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-25
 */
@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    private PurchaseDetailService purchaseDetailService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryUnreceivePage(Map<String, Object> params) {
        LambdaQueryWrapper<PurchaseEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PurchaseEntity::getStatus, 1).or().eq(PurchaseEntity::getStatus, 0);

        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                queryWrapper);

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void merge(MergeVo vo) {
        Long purchaseId = vo.getPurchaseId();
        if (purchaseId == null) {
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setStatus(WareConstant.PurchaseEnum.CREATED.getCode());
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setUpdateTime(new Date());
            this.save(purchaseEntity);
            purchaseId = purchaseEntity.getId();
        }



        List<Long> items = vo.getItems();

        Long finalPurchaseId = purchaseId;
        List<PurchaseDetailEntity> detailEntities = items.stream().map(item -> {
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            purchaseDetailEntity.setId(item);
            purchaseDetailEntity.setPurchaseId(finalPurchaseId);
            purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());
            return purchaseDetailEntity;
        }).toList();

        purchaseDetailService.updateBatchById(detailEntities);


    }

    @Transactional
    @Override
    public void received(List<Long> ids) {
        // 1. 需要修改采购单中的状态，将其改为 已分配
        List<PurchaseEntity> list = ids.stream().map(id -> {
            PurchaseEntity byId = this.getById(id);
            return byId;
        }).filter(item -> {
            return item.getStatus() == WareConstant.PurchaseEnum.CREATED.getCode() || item.getStatus() == WareConstant.PurchaseEnum.ASSIGNED.getCode();
        }).peek(item -> {
            item.setStatus(WareConstant.PurchaseEnum.RECEIVED.getCode());
            item.setUpdateTime(new Date());
        }).toList();

        // 2. 改变订单状态
        this.updateBatchById(list);

        // 3. 改变采购项的状态
        list.forEach(item -> {
            List<PurchaseDetailEntity> entities = purchaseDetailService.listDetailByPurchased(item.getId());
//            for (PurchaseDetailEntity entity : entities) {
//                entity.setStatus(WareConstant.PurchaseDetailStatusEnum.BUYING.getCode());
//            }
            entities.forEach(entity -> {
                entity.setStatus(WareConstant.PurchaseDetailStatusEnum.BUYING.getCode());
            });
            purchaseDetailService.updateBatchById(entities);
        });


    }

}