package com.yuan.SecondsKill.Service.impl;


import com.yuan.SecondsKill.Service.OrderService;
import com.yuan.SecondsKill.dao.OrderDao;
import com.yuan.SecondsKill.domain.KillsUser;
import com.yuan.SecondsKill.domain.MiaoshaOrder;
import com.yuan.SecondsKill.domain.OrderInfo;
import com.yuan.SecondsKill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;

    @Override
    public OrderInfo getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
        return orderDao.getMiaoshaOrderByUserIdGoodsId(userId,goodsId);
    }

    //生成订单详情和秒杀订单
    @Transactional
    public OrderInfo createOrder(KillsUser user, GoodsVo goodsVo) {
        OrderInfo orderInfo=new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsPrice(goodsVo.getGoodsPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        long orderId=orderDao.insert(orderInfo);

        MiaoshaOrder miaoshaOrder=new MiaoshaOrder();
        miaoshaOrder.setUserId(user.getId());
        miaoshaOrder.setGoodsId(goodsVo.getId());
        miaoshaOrder.setOrderId(orderId);

        orderDao.insertMiaoshaOrder(miaoshaOrder);
        return orderInfo;
    }
}
