package com.yuan.SecondsKill.redis;

public abstract class BasePrefix implements KeyPrefix{
    private int expireSeconds;
    private String prefix;

    public BasePrefix(String prefix){
        this(0,prefix);
    }

    public BasePrefix(int expireSeconds,String prefix){
        this.expireSeconds=expireSeconds;
        this.prefix=prefix;
    }

    @Override
    public int expireSeconds() {//默认为0表示永不过期
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String classname=getClass().getSimpleName();
        return classname+":"+prefix;
    }
}
