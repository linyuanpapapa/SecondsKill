package com.yuan.SecondsKill.redis;

/**
 * key值的前缀，防止不同种类的key值名字相同导致覆盖
 */
public interface KeyPrefix {
    public int expireSeconds();
    public String getPrefix();
}
