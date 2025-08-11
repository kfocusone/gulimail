package com.example.gulimail.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.gulimail.common.constant.ProductEnum;
import com.example.gulimail.common.utils.R;
import com.example.gulimail.product.dao.AttrAttrgroupRelationDao;
import com.example.gulimail.product.dao.AttrGroupDao;
import com.example.gulimail.product.dao.CategoryDao;
import com.example.gulimail.product.entity.AttrAttrgroupRelationEntity;
import com.example.gulimail.product.entity.AttrGroupEntity;
import com.example.gulimail.product.entity.CategoryEntity;
import com.example.gulimail.product.service.CategoryService;
import com.example.gulimail.product.vo.AttrRespVo;
import com.example.gulimail.product.vo.AttrVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.common.utils.Query;

import com.example.gulimail.product.dao.AttrDao;
import com.example.gulimail.product.entity.AttrEntity;
import com.example.gulimail.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 商品属性
 *
 * @author kfocus kfocus@gmail.com
 * @since 1.0.0 2025-07-24
 */
@Service
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationDao relationDao;

    @Autowired
    private AttrGroupDao attrGroupDao;

    @Resource // 默认与名字匹配
    private CategoryDao categoryDao;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttr(AttrVO attrVO) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVO, attrEntity);
        this.save(attrEntity);

        Long attrGroupId = attrVO.getAttrGroupId();
        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        relationEntity.setAttrGroupId(attrGroupId);
        relationEntity.setAttrId(attrEntity.getAttrId());
        relationDao.insert(relationEntity);

    }

    @Override
    public PageUtils queryBaseAttr(Map<String, Object> params, Long catelogId, String type) {
        LambdaQueryWrapper<AttrEntity> queryWrapper = new LambdaQueryWrapper<>();

        // 引入枚举类型，既方便阅读，又方便使用
        queryWrapper.eq(AttrEntity::getAttrType, "base".equalsIgnoreCase(type)? ProductEnum.ATTR_TYPE_BASE.getCode() : ProductEnum.ATTR_TYPR_SALE.getCode());
        if (catelogId != 0) {
            queryWrapper.eq(AttrEntity::getCatelogId, catelogId);

        }
        String key = (String) params.get("key");
        if (StringUtils.hasText(key)) {
            queryWrapper.and( wrapper -> {
                wrapper.eq(AttrEntity::getAttrId, key)
                        .or()
                        .like(AttrEntity::getAttrName, key);
            });
        }

        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper);

        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> collect = records.stream().map((attrEntity) -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);


            // 有可能没有传回 groupid 就自己查
            AttrAttrgroupRelationEntity relationEntity = relationDao.selectOne(
                    new LambdaQueryWrapper<AttrAttrgroupRelationEntity>().eq(AttrAttrgroupRelationEntity::getAttrId, attrEntity.getAttrId()));
            if (relationEntity != null) {
                String attrGroupName = attrGroupDao.selectById(relationEntity.getAttrGroupId()).getAttrGroupName();
                attrRespVo.setAttrGroupName(attrGroupName);
            }
            String cateName = categoryDao.selectNameById(attrEntity.getCatelogId());
            attrRespVo.setCatelogName(cateName);

            return attrRespVo;
        }).collect(Collectors.toList());

        pageUtils.setList(collect);
        return pageUtils;

    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrRespVo respVo = new AttrRespVo();
        AttrEntity attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity, respVo);

        // 设置分组信息
        AttrAttrgroupRelationEntity relationEntity = relationDao.selectOne(
                new LambdaQueryWrapper<AttrAttrgroupRelationEntity>().eq(AttrAttrgroupRelationEntity::getAttrId, attrId));

        if (relationEntity != null) {
            respVo.setAttrGroupId(relationEntity.getAttrGroupId());
            AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
            if (attrGroupEntity != null) {
                respVo.setAttrGroupName(attrGroupEntity.getAttrGroupName());
            }
        }
        // 通过 catelogid 查路径
        Long catelogId = attrEntity.getCatelogId();
        Long[] catelogPath = categoryService.findCatelogPath(attrEntity.getCatelogId());
        respVo.setCatelogPath(catelogPath);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        if (categoryEntity != null) {
            respVo.setCatelogName(categoryEntity.getName());
        }


        return respVo;
    }

    @Transactional
    @Override
    public void updateAttr(AttrVO attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.updateById(attrEntity);

        // 保存或更新 关联组信息 如果没有，就保存， 如果有，就修改 这需要自己先创建
        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        relationEntity.setAttrId(attr.getAttrId());
        relationEntity.setAttrGroupId(attr.getAttrGroupId());

        // relationService.saveOeUpdate(relationEntity);
        Long count = relationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
        if (count > 0) {
            relationDao.update(relationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
        }


    }

    /**
     * 根据分组 id 查找关联的所有属性
     * @param attrGroupId
     * @return
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrGroupId) {
        LambdaQueryWrapper<AttrAttrgroupRelationEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AttrAttrgroupRelationEntity::getAttrGroupId, attrGroupId);
        List<AttrAttrgroupRelationEntity> relationEntityList = relationDao.selectList(queryWrapper);
        List<Long> attrIds = relationEntityList.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());

        return this.listByIds(attrIds);
    }

}