package com.yuan.SecondsKill.util;


import com.alibaba.druid.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 输入合法性判断
 */
public class ValidatorUtil {
    private static final Pattern mobile_pattern=Pattern.compile("1\\d{10}");
    public static boolean isMobile(String str){
        if(StringUtils.isEmpty(str)){
            return false;
        }
        Matcher m=mobile_pattern.matcher(str);
        return m.matches();

    }

}
