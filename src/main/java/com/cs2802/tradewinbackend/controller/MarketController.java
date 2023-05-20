package com.cs2802.tradewinbackend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cs2802.tradewinbackend.pojo.Market;
import com.cs2802.tradewinbackend.service.MarketService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("dashboard")
@CrossOrigin
public class MarketController {
    @Resource
    private MarketService marketService=new MarketService();

    @PostMapping("market")
    public JSONArray createMarket(@RequestBody Map<String,String> map){
        String value=map.get("option");
        String[] parts=value.split("to");
        String from=parts[0];
        String to=parts[1];

        JSONArray result=new JSONArray();
        String[] titles=new String[]{"Common Wealth bank","Nab","Anz","HSBC"};
        String[] urls=new String[]{"https://www.commbank.com.au/international/foreign-exchange-rates.html?ei=hp-prodnav_INT-FXrates",
                "https://www.nab.com.au/personal/international-banking/foreign-exchange-rates",
                "https://www.anz.com.au/personal/travel-international/currency-converter/",
                "https://www.hsbc.com.au/calculators/real-time-exchange-rates/"};

        List<Market> markets=marketService.getMarketData();

        int len=markets.size();
        int count=0;
        for (int i=0;i<len;i++){
            Market market=markets.get(i);
            if(market.getCurrency().equals(from+to)){
                String buy=market.getBuy();
                String sell=market.getSell();

                JSONObject one_result=new JSONObject();
                one_result.put("title",titles[count]);
                one_result.put("url",urls[count]);
                one_result.put("description","BuyPrice: "+buy+" , "+"SellPrice: "+sell);
                result.add(one_result);
                count++;
            }
        }
        return result;
    }
}
