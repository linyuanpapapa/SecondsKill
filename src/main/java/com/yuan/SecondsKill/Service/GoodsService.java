package com.yuan.SecondsKill.Service;

import com.yuan.SecondsKill.vo.GoodsVo;

import java.util.List;

public interface GoodsService {
    public List<GoodsVo> listGoodsVo();

    GoodsVo getGoodsVoByGoodsId(long goodsId);

    void reduceStock(GoodsVo goodsVo);
}
