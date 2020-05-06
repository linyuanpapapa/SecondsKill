package com.yuan.SecondsKill.Service.impl;

import com.alibaba.druid.util.StringUtils;
import com.yuan.SecondsKill.Service.KillsUserService;
import com.yuan.SecondsKill.dao.KillsUserDao;
import com.yuan.SecondsKill.domain.KillsUser;
import com.yuan.SecondsKill.exception.GlobalException;
import com.yuan.SecondsKill.redis.KillsUserKey;
import com.yuan.SecondsKill.redis.RedisService;
import com.yuan.SecondsKill.redis.UserKey;
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
    public String login(HttpServletResponse response,LoginVo loginVo) {
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
        return token;
    }

    public KillsUser getById(long id){
        //取缓存
        KillsUser user=redisService.get(KillsUserKey.getById,""+id,KillsUser.class);
        if(user!=null){
            return user;
        }
        //缓存为空，查数据库
        user = killsUserDao.selectById(id);
        redisService.set(KillsUserKey.getById,""+id,user);
        return user;
    }

    /**
     * 对象缓存，由token取出对象
     * @param response
     * @param token
     * @return
     */
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

    public boolean updatePassword(String token,long id,String formPass){
        //取User
        KillsUser user=getById(id);
        if(user==null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        KillsUser newUser=new KillsUser();
        newUser.setId(id);
        newUser.setPassword(MD5Util.FormpassToDbpass(formPass,user.getSalt()));
        killsUserDao.update(newUser);
        //处理缓存
        redisService.delete(KillsUserKey.getById,""+id);
        user.setPassword(newUser.getPassword());
        redisService.set(KillsUserKey.token,token,user);
        return true;
    }

    private void addCookie(HttpServletResponse response,String token,KillsUser user){
        //为token加上前缀
        redisService.set(KillsUserKey.token,token,user);
        Cookie cookie=new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(KillsUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
