package com.yuan.SecondsKill.Controller;

import com.yuan.SecondsKill.Service.impl.UserServiceImpl;
import com.yuan.SecondsKill.domain.User;
import com.yuan.SecondsKill.redis.RedisService;
import com.yuan.SecondsKill.redis.UserKey;
import com.yuan.SecondsKill.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/demo")
public class SampleController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name","yuan");
        return "Hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public String dbGet(){
        return userService.getById(1).toString();
    }

    //测试事务
    @RequestMapping("/db/tx")
    @ResponseBody
    public boolean dbtx(){
        return userService.tx();
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet(){
        User user=redisService.get(UserKey.getById,""+1,User.class);
        return Result.success(user);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet(){
        User user=new User();
        user.setId(5);
        user.setName("zizi");
        redisService.set(UserKey.getById,""+1,user);//UserKey:id1
        return Result.success(true);
    }
}
