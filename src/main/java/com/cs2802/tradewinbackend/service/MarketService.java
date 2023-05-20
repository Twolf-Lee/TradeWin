package com.cs2802.tradewinbackend.service;

import com.cs2802.tradewinbackend.mapper.MarketMapper;
import com.cs2802.tradewinbackend.pojo.Market;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MarketService {
    @Resource
    private MarketMapper marketMapper;

    public List<Market> getMarketData(){
        List<Market> markets=marketMapper.selectMarket();
        return markets;
    }
}
