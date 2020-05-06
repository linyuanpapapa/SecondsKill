package com.yuan.SecondsKill.Service;

import com.yuan.SecondsKill.domain.KillsUser;
import com.yuan.SecondsKill.domain.MiaoshaOrder;
import com.yuan.SecondsKill.domain.OrderInfo;
import com.yuan.SecondsKill.vo.GoodsVo;

import java.util.List;

public interface OrderService {
    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId , long goodsId);

    OrderInfo createOrder(KillsUser user, GoodsVo goodsVo);

    OrderInfo getOrderById(long orderId);
}
