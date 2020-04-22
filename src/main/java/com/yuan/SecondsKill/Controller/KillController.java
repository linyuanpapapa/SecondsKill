package com.yuan.SecondsKill.Controller;

import com.yuan.SecondsKill.Service.GoodsService;
import com.yuan.SecondsKill.Service.MiaoshaService;
import com.yuan.SecondsKill.Service.OrderService;
import com.yuan.SecondsKill.domain.KillsUser;
import com.yuan.SecondsKill.domain.OrderInfo;
import com.yuan.SecondsKill.result.CodeMsg;
import com.yuan.SecondsKill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller()
@RequestMapping("/miaosha")
public class KillController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;


    @RequestMapping("/do_miaosha")
    public String toList(Model model, KillsUser user,
                         @RequestParam("goodsId")long goodsId){
        model.addAttribute("user",user);
        if(user==null){
            return "login";
        }
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stockCount = goodsVo.getStockCount();
        //库存不足
        if(stockCount<=0){
            model.addAttribute("errormsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_fail";
        }
        //判断是否已经秒杀到了
        OrderInfo order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order!=null){
            model.addAttribute("errormsg",CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }
        //减库存，下单，写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goodsVo);
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("goods",goodsVo);
        return "order_detail";
    }




}
