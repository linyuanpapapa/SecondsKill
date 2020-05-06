package com.yuan.SecondsKill.exception;

import com.yuan.SecondsKill.result.CodeMsg;
import com.yuan.SecondsKill.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 全局异常控制器
 * 拦截异常，在页面上显示错误信息
 */
/**
 * 增强的Controller注解，使用这个Controller，可以实现以下功能：
 * 全局异常处理、全局数据绑定、全局数据预处理。
 * （SpringMVC提供，在SpringBoot中可以直接使用）
 */

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    //如果只使用此注解，只能在当前Controller中处理异常，当配合ControllerAdvice一起使用的时候，就可以摆脱这种限制了
    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request,Exception e){
        e.printStackTrace();
        if(e instanceof GlobalException){
            GlobalException ge=(GlobalException)e;
            return Result.error(ge.getCm());
        }
        if(e instanceof BindException){
            BindException ex=(BindException)e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg=error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else{
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
