package com.yuan.SecondsKill.redis;

public class KillsUserKey extends BasePrefix{
    private static final Integer TOKEN_EXPIRESECONDS=172800;
    private KillsUserKey(int expireSeconds,String prefix) {
        super(expireSeconds,prefix);
    }
    public static KillsUserKey token=new KillsUserKey(TOKEN_EXPIRESECONDS,"tk");
    public static KillsUserKey getById=new KillsUserKey(0,"id");
}
