package com.yuan.SecondsKill.Controller;

import com.alibaba.druid.util.StringUtils;
import com.yuan.SecondsKill.Service.KillsUserService;
import com.yuan.SecondsKill.Service.impl.KillsUserServiceImpl;
import com.yuan.SecondsKill.domain.KillsUser;
import com.yuan.SecondsKill.result.Result;
import com.yuan.SecondsKill.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller()
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    KillsUserService killsUserService;


    @RequestMapping("/to_list")
    public String toList(Model model,KillsUser user){
        //无cookie返回登录界面
        model.addAttribute("user",user);
        return "goods_list";
    }



}
