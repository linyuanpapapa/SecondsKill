package com.yuan.SecondsKill.Controller;

import com.sun.tools.corba.se.idl.constExpr.Or;
import com.sun.tools.javac.jvm.Code;
import com.yuan.SecondsKill.Service.GoodsService;
import com.yuan.SecondsKill.Service.OrderService;
import com.yuan.SecondsKill.domain.KillsUser;
import com.yuan.SecondsKill.domain.OrderInfo;
import com.yuan.SecondsKill.result.CodeMsg;
import com.yuan.SecondsKill.result.Result;
import com.yuan.SecondsKill.vo.GoodsVo;
import com.yuan.SecondsKill.vo.OrderdetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller()
@RequestMapping("/order")
public class OrderController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;


    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderdetailVo> detail(Model model, KillsUser user,
                                        @RequestParam("orderId")long orderId) {
        if(user==null) return Result.error(CodeMsg.SESSION_ERROR);
        OrderInfo orderInfo=orderService.getOrderById(orderId);
        if(orderInfo==null) return Result.error(CodeMsg.ORDER_NOT_EXIST);
        Long goodsId = orderInfo.getGoodsId();
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);

        OrderdetailVo vo=new OrderdetailVo();
        vo.setGoods(goodsVo);
        vo.setOrderInfo(orderInfo);
        return Result.success(vo);
    }

}
