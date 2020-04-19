package com.yuan.SecondsKill.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 工具类：
 * 把用户在前台输入的密码进行两次MD5加密
 * 然后使用加密后的密码存入数据库
 * 客户端：Pass=MD5(明文，固定Salt)
 * 服务端：Pass=MD5(用户输入，随机Salt)
 */
public class MD5Util{
    public static final String salt="1a2b3c4d";

    public static String md5(String str){
        return DigestUtils.md5Hex(str);
    }
    //第一次加密
    public static String inputpassToFormpass(String str){
        //自定义加密规则，固定salt
        String string=" "+salt.charAt(0)+salt.charAt(1)+str+salt.charAt(4)+salt.charAt(5);
        return md5(string);
    }
    //第二次加密
    public static String FormpassToDbpass(String str,String salt){
        //自定义salt
        String string=" "+salt.charAt(0)+salt.charAt(1)+str+salt.charAt(4)+salt.charAt(5);
        return md5(string);
    }
    public static String inputpassToDbpass(String str,String salt){
        String formPass=inputpassToFormpass(str);
        return FormpassToDbpass(formPass,salt);
    }

    /*public static void main(String[] args) {
        System.out.println(inputpassToDbpass("123456","1a2b3c4d"));
    }*/
}
