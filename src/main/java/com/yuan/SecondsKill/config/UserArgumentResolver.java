package com.yuan.SecondsKill.config;

import com.alibaba.druid.util.StringUtils;
import com.yuan.SecondsKill.Service.KillsUserService;
import com.yuan.SecondsKill.Service.impl.KillsUserServiceImpl;
import com.yuan.SecondsKill.domain.KillsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对Controller中的执行方法的参数进行注入
 * 与实现了WebMvcConfigurerAdapter接口的类进行配合
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    KillsUserService killsUserService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz== KillsUser.class;
    }
    //如果上面的方法判断为真，才会执行下面的方法
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
              ModelAndViewContainer modelAndViewContainer,
              NativeWebRequest nativeWebRequest,
              WebDataBinderFactory webDataBinderFactory) throws Exception {
        //获取Cookie和Session
        HttpServletRequest request=nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response=nativeWebRequest.getNativeResponse(HttpServletResponse.class);

        String paramToken = request.getParameter(KillsUserServiceImpl.COOKIE_NAME_TOKEN);
        String cookieToken=getCookieValue(request,KillsUserServiceImpl.COOKIE_NAME_TOKEN);

        if(StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken)){
            return null;
        }
        String token=StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        return killsUserService.getByToken(response,token);
    }

    private String getCookieValue(HttpServletRequest request, String cookieNameToken) {
        Cookie[] cookies = request.getCookies();
        if(cookies==null||cookies.length==0) return null;
        for(Cookie cookie:cookies){
            if(cookie.getName().equals(cookieNameToken)){
                return cookie.getValue();
            }
        }
        return null;
    }
}
