package com.yuan.SecondsKill.Controller;

import com.alibaba.druid.util.StringUtils;
import com.sun.tools.javac.jvm.Code;
import com.yuan.SecondsKill.Service.KillsUserService;
import com.yuan.SecondsKill.result.CodeMsg;
import com.yuan.SecondsKill.result.Result;
import com.yuan.SecondsKill.util.ValidatorUtil;
import com.yuan.SecondsKill.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller()
@RequestMapping("/login")
public class LoginController{

    @Autowired
    KillsUserService killsUserService;

    private static Logger log= LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo){
        log.info(loginVo.toString());
        /*//参数校验
        String mobile=loginVo.getMobile();
        String password=loginVo.getPassword();

        if(StringUtils.isEmpty(mobile)){
            return Result.error(CodeMsg.MOBILE_EMPTY);
        }
        if(!ValidatorUtil.isMobile(mobile)){
            return Result.error(CodeMsg.MOBILE_ERROR);
        }
        if(StringUtils.isEmpty(password)){
            return Result.error(CodeMsg.PASSWORD_EMPTY);
        }*/
        //登录
        killsUserService.login(response,loginVo);
        return Result.success(true);
    }
}
