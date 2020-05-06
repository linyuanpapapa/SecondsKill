package com.yuan.SecondsKill.Controller;

import com.yuan.SecondsKill.Service.GoodsService;
import com.yuan.SecondsKill.Service.MiaoshaService;
import com.yuan.SecondsKill.Service.OrderService;
import com.yuan.SecondsKill.domain.KillsUser;
import com.yuan.SecondsKill.domain.MiaoshaOrder;
import com.yuan.SecondsKill.domain.OrderInfo;
import com.yuan.SecondsKill.result.CodeMsg;
import com.yuan.SecondsKill.result.Result;
import com.yuan.SecondsKill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller()
@RequestMapping("/miaosha")
public class KillController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;
    /**
     * 防止超卖（库存小于0）
     * 1、sql语句加上stock_count>0的判断（防止库存变成负数）
     * 2、数据库中的秒杀订单表中对用户id uid和商品id gid加上唯一索引（防止用户重复购买）
     */
    /**
     * 秒杀接口优化
     * 思路：减少数据库访问
     * 1、系统初始化，把商品库存数量加载到Redis
     * 2、收到请求，Redis预减库存，若库存不足，直接返回，否则进入3
     * 3、请求入队，立即返回排队中
     * 4、请求出队，生成订单，减少库存
     * 5、客户端轮询，是否秒杀成功
     */
    /**
     * 优化前：QPS：834.9
     *        5000*10
     * 优化1.0：加上页面缓存、URL缓存、对象缓存后
     *       QPS：840.5
     *       0 error :684.4
     * 优化2.0：页面静态化 常用技术AngularJs、Vue.js
     *       QPS:1853.5
     * 优化3.0：静态资源优化 Tengine：JS/CSS压缩，减少流量 多个JS/CSS组合，减少连接数 CDN就近访问
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    /* 旧秒杀
    @RequestMapping("/do_miaosha")
    public String miaosha(Model model, KillsUser user,
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
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order!=null){
            model.addAttribute("errormsg",CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }
        //减库存，下单，写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goodsVo);
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("goods",goodsVo);
        return "order_detail";
    }*/
    //注意GET和POST的区别 幂等性
    @RequestMapping(value = "/do_miaosha",method = RequestMethod.POST)
    @ResponseBody
    public Result<OrderInfo> miaosha(Model model, KillsUser user,
                                 @RequestParam("goodsId")long goodsId){
        model.addAttribute("user",user);
        if(user==null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stockCount = goodsVo.getStockCount();
        //库存不足
        if(stockCount<=0){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否已经秒杀到了,这里可以不去数据库查找是否有订单
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order!=null){
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        //减库存，下单，写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goodsVo);
        return Result.success(orderInfo);
    }



}
