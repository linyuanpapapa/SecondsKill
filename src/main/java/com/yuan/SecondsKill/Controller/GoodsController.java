package com.yuan.SecondsKill.Controller;

import com.alibaba.druid.util.StringUtils;
import com.yuan.SecondsKill.Service.GoodsService;
import com.yuan.SecondsKill.Service.KillsUserService;
import com.yuan.SecondsKill.Service.impl.KillsUserServiceImpl;
import com.yuan.SecondsKill.domain.KillsUser;
import com.yuan.SecondsKill.result.Result;
import com.yuan.SecondsKill.vo.GoodsVo;
import com.yuan.SecondsKill.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;


@Controller()
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;


    @RequestMapping("/to_list")
    public String toList(Model model,KillsUser user){
        //无cookie返回登录界面
        model.addAttribute("user",user);
        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList",goodsList);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model,KillsUser user,
            @PathVariable("goodsId")long goodsId){
        model.addAttribute("user",user);

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
        return "goods_detail";
    }


}
