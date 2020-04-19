package com.yuan.SecondsKill.Service.impl;

import com.alibaba.druid.util.StringUtils;
import com.yuan.SecondsKill.Service.KillsUserService;
import com.yuan.SecondsKill.dao.KillsUserDao;
import com.yuan.SecondsKill.domain.KillsUser;
import com.yuan.SecondsKill.exception.GlobalException;
import com.yuan.SecondsKill.redis.KillsUserKey;
import com.yuan.SecondsKill.redis.RedisService;
import com.yuan.SecondsKill.result.CodeMsg;
import com.yuan.SecondsKill.util.MD5Util;
import com.yuan.SecondsKill.util.UUIDUtil;
import com.yuan.SecondsKill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class KillsUserServiceImpl implements KillsUserService {
    public static final String COOKIE_NAME_TOKEN="token";

    @Autowired
    KillsUserDao killsUserDao;

    @Autowired
    RedisService redisService;

    @Override
    public boolean login(HttpServletResponse response,LoginVo loginVo) {
        if(loginVo==null) throw new GlobalException(CodeMsg.SERVER_ERROR);
        String id=loginVo.getMobile();
        String formPass=loginVo.getPassword();
        //判断用户是否存在
        KillsUser user=killsUserDao.selectById(Long.parseLong(id));
        if(user==null) throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        String DbPass=user.getPassword();
        String saltDb=user.getSalt();

        //把获取的经过一次MD5加密的密码再加密一次与数据库中的密码进行比较
        String calPass = MD5Util.FormpassToDbpass(formPass, saltDb);
        if(!calPass.equals(DbPass)) throw new GlobalException(CodeMsg.PASSWORD_ERROR);

        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response,token,user);
        return true;
    }

    @Override
    public KillsUser getByToken(HttpServletResponse response,String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        KillsUser user=redisService.get(KillsUserKey.token,token,KillsUser.class);
        //延长有效期
        if(user!=null)  addCookie(response,token,user);
        return user;
    }

    private void addCookie(HttpServletResponse response,String token,KillsUser user){
        redisService.set(KillsUserKey.token,token,user);
        Cookie cookie=new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(KillsUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
