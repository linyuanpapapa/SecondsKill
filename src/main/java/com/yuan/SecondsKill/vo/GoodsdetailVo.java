package com.yuan.SecondsKill.vo;

import com.yuan.SecondsKill.domain.KillsUser;

public class GoodsdetailVo {
    private GoodsVo goods;
    private int KillStatus;
    private int remainSeconds;
    private KillsUser user;

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    public int getKillStatus() {
        return KillStatus;
    }

    public void setKillStatus(int killStatus) {
        KillStatus = killStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public KillsUser getUser() {
        return user;
    }

    public void setUser(KillsUser user) {
        this.user = user;
    }
}
