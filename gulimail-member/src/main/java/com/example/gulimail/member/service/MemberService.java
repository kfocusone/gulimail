package com.example.gulimail.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimail.common.utils.PageUtils;
import com.example.gulimail.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author kfocus
 * @email kfocus@gmail.com
 * @date 2025-07-25 11:26:51
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}