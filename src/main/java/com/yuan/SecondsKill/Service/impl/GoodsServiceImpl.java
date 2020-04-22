package com.yuan.SecondsKill.Service.impl;

import com.yuan.SecondsKill.Service.GoodsService;
import com.yuan.SecondsKill.dao.GoodsDao;
import com.yuan.SecondsKill.domain.Goods;
import com.yuan.SecondsKill.domain.MiaoshaGoods;
import com.yuan.SecondsKill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsDao goodsDao;

    @Override
    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    @Override
    public void reduceStock(GoodsVo goodsVo) {
        MiaoshaGoods g=new MiaoshaGoods();
        g.setGoodsId(goodsVo.getId());
        g.setStockCount(goodsVo.getStockCount()-1);
        goodsDao.reduceStock(g);
    }
}
