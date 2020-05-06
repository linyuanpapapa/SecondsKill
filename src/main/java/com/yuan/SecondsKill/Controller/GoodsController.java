package com.yuan.SecondsKill.Controller;

import com.alibaba.druid.util.StringUtils;
import com.yuan.SecondsKill.Service.GoodsService;
import com.yuan.SecondsKill.Service.KillsUserService;
import com.yuan.SecondsKill.Service.impl.KillsUserServiceImpl;
import com.yuan.SecondsKill.domain.KillsUser;
import com.yuan.SecondsKill.redis.GoodsKey;
import com.yuan.SecondsKill.redis.RedisService;
import com.yuan.SecondsKill.result.Result;
import com.yuan.SecondsKill.vo.GoodsVo;
import com.yuan.SecondsKill.vo.GoodsdetailVo;
import com.yuan.SecondsKill.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller()
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 页面缓存：取缓存、手动渲染模板和结果输出
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "/to_list",produces = "text/html")
    @ResponseBody
    public String toList(HttpServletRequest request, HttpServletResponse response,
                         Model model, KillsUser user){
        //无cookie返回登录界面
        model.addAttribute("user",user);
        //取缓存，如果redis中有相应的缓存，直接取出使用
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList",goodsList);
        //return "goods_list";

        SpringWebContext ctx=new SpringWebContext(request,response,request.getServletContext(),
                request.getLocale(),model.asMap(),applicationContext);
        //手动渲染模板并且存入redis,框架渲染的具体方式也是如此
        html=thymeleafViewResolver.getTemplateEngine().process("goods_list",ctx);
        if(!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsList,"",html);
        }
        //结果返回
        return html;
    }

    @RequestMapping(value = "/to_detail/{goodsId}",produces = "text/html")
    @ResponseBody
    public String detail(HttpServletRequest request, HttpServletResponse response,
                         Model model,KillsUser user,
            @PathVariable("goodsId")long goodsId){
        model.addAttribute("user",user);

        //取缓存，如果redis中有相应的缓存，直接取出使用
        String html = redisService.get(GoodsKey.getGoodsDetail, ""+goodsId, String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }

        GoodsVo goods=goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goods);

        long startAt=goods.getStartDate().getTime();
        long endAt=goods.getEndDate().getTime();
        long now=System.currentTimeMillis();

        int KillStatus=0;//表示还未秒杀
        int remainSeconds=0;

        if(now < startAt){//秒杀未开始
            KillStatus=0;
            remainSeconds=(int)((startAt-now)/1000);
        }else if(now > endAt){//秒杀已结束
            KillStatus=2;
            remainSeconds=-1;
        }else{//秒杀进行中
            KillStatus=1;
            remainSeconds=0;
        }
        model.addAttribute("KillStatus",KillStatus);
        model.addAttribute("remainSeconds",remainSeconds);


        SpringWebContext ctx=new SpringWebContext(request,response,request.getServletContext(),
                request.getLocale(),model.asMap(),applicationContext);
        //手动渲染模板并且存入redis,框架渲染的具体方式也是如此
        html=thymeleafViewResolver.getTemplateEngine().process("goods_detail",ctx);
        if(!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsDetail,""+goodsId,html);
        }
        //结果返回
        return html;
        //return "goods_detail";
    }

    @RequestMapping(value = "/detail2/{goodsId}")
    @ResponseBody
    public Result<GoodsdetailVo> detail2(HttpServletRequest request, HttpServletResponse response,
                                         Model model, KillsUser user,
                                         @PathVariable("goodsId")long goodsId){
        GoodsVo goods=goodsService.getGoodsVoByGoodsId(goodsId);

        long startAt=goods.getStartDate().getTime();
        long endAt=goods.getEndDate().getTime();
        long now=System.currentTimeMillis();

        int KillStatus=0;//表示还未秒杀
        int remainSeconds=0;

        if(now < startAt){//秒杀未开始
            KillStatus=0;
            remainSeconds=(int)((startAt-now)/1000);
        }else if(now > endAt){//秒杀已结束
            KillStatus=2;
            remainSeconds=-1;
        }else{//秒杀进行中
            KillStatus=1;
            remainSeconds=0;
        }

        GoodsdetailVo vo=new GoodsdetailVo();
        vo.setGoods(goods);
        vo.setKillStatus(KillStatus);
        vo.setRemainSeconds(remainSeconds);
        vo.setUser(user);
        return Result.success(vo);
    }

}
