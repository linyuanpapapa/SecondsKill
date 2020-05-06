package com.yuan.SecondsKill.redis;

public class orderKey extends BasePrefix{
    private orderKey(String prefix) {
        super(prefix);
    }
    public static orderKey getorderByuserIdAndgoodsId=new orderKey("moug");
}
