package com.yuan.SecondsKill.Controller;

import com.yuan.SecondsKill.Service.GoodsService;
import com.yuan.SecondsKill.domain.KillsUser;
import com.yuan.SecondsKill.result.Result;
import com.yuan.SecondsKill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller()
@RequestMapping("/goods")
public class UserController {

    @Autowired
    GoodsService goodsService;


    @RequestMapping("/info")
    @ResponseBody
    public Result<KillsUser> info(Model model, KillsUser user){
        return Result.success(user);
    }

}
