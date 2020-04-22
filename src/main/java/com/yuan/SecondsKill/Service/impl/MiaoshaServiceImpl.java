package com.yuan.SecondsKill.Service.impl;

import com.yuan.SecondsKill.Service.GoodsService;
import com.yuan.SecondsKill.Service.MiaoshaService;
import com.yuan.SecondsKill.Service.OrderService;
import com.yuan.SecondsKill.domain.KillsUser;
import com.yuan.SecondsKill.domain.OrderInfo;
import com.yuan.SecondsKill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaServiceImpl implements MiaoshaService {
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo miaosha(KillsUser user, GoodsVo goodsVo) {
        //减库存，下单，写入秒杀订单
        goodsService.reduceStock(goodsVo);
        return orderService.createOrder(user,goodsVo);
    }
}
