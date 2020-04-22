package com.yuan.SecondsKill.Service;


import com.yuan.SecondsKill.domain.KillsUser;
import com.yuan.SecondsKill.domain.OrderInfo;
import com.yuan.SecondsKill.vo.GoodsVo;

public interface MiaoshaService {
    OrderInfo miaosha(KillsUser user, GoodsVo goodsVo);
}
