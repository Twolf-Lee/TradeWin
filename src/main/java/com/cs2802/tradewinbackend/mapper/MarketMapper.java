package com.cs2802.tradewinbackend.mapper;

import com.cs2802.tradewinbackend.pojo.Market;
import org.apache.ibatis.annotations.Select;
import org.yaml.snakeyaml.error.Mark;

import java.util.List;

public interface MarketMapper {
    @Select("select id,buy,sell,currency from market")
    List<Market> selectMarket();
}
