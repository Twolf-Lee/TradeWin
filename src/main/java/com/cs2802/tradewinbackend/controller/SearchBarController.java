package com.cs2802.tradewinbackend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cs2802.tradewinbackend.service.ExchangeService;
import com.cs2802.tradewinbackend.utils.OkHttp_Get;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("dashboard")
@CrossOrigin
public class SearchBarController {
    @Resource
    private ExchangeService exchangeService = new ExchangeService();

    @PostMapping("searchchart")
    public JSONObject searchChart(@RequestBody Map<String,String> map) throws IOException {
        String value=map.get("option");
        String[] parts=value.split("to");
        String from=parts[0];
        String to=parts[1];

        Date date=new Date();
        System.out.println(date);
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String today=dateFormat.format(date);

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-300);
        date=calendar.getTime();  //
        String earliest_day=dateFormat.format(date);

        String api_url = "https://api.apilayer.com/currency_data/timeframe?&source=" + from + "&currencies=" + to + "&start_date="+earliest_day+"&end_date="+today;
        OkHttp_Get okHttpGet = new OkHttp_Get();

        String run = okHttpGet.run(api_url, "apikey", "xvhJYlbcBPOOfYbJsyliEougAxTcqigr");
        JSONObject jsonObject= JSON.parseObject(run);
        JSONObject date_value=jsonObject.getJSONObject("quotes");

        JSONObject one_result=new JSONObject();
        Iterator<String> keys= date_value.keySet().iterator();
        while (keys.hasNext()){
            String one_key=keys.next();
            JSONObject price_pair=date_value.getJSONObject(one_key);
            String price=price_pair.getString(from+to);
            one_result.put(one_key,price);
        }
        return one_result;
    }

    @PostMapping("searchcard")
    public JSONArray searchCard(@RequestBody Map<String,String> map) throws IOException {
        String value=map.get("option");
        String[] parts=value.split("to");
        String from=parts[0];
        String to=parts[1];

        // Optimize part
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String today=dateFormat.format(date);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-300);
        date=calendar.getTime();
        String earliest_day=dateFormat.format(date);
        // Optimize end

        String api_url = "https://api.apilayer.com/currency_data/timeframe?&source="+from+"&currencies="+to+"&start_date="+earliest_day+"&end_date="+today;
        OkHttp_Get okHttpGet = new OkHttp_Get();
        String run = okHttpGet.run(api_url, "apikey", "xvhJYlbcBPOOfYbJsyliEougAxTcqigr");

        JSONArray result=new JSONArray();

        JSONObject jsonObject=JSON.parseObject(run);
        JSONObject date_value=jsonObject.getJSONObject("quotes");
        JSONObject date_price=new JSONObject();
        Iterator<String> keys= date_value.keySet().iterator();
        while (keys.hasNext()){
            String one_key=keys.next();
            JSONObject price_pair=date_value.getJSONObject(one_key);
            String price=price_pair.getString(from+to);
            date_price.put(one_key,price);
        }

        calendar.add(Calendar.DAY_OF_MONTH, +300);
        date=calendar.getTime();
        calendar.setTime(date);
        String first_day=dateFormat.format(date);   // 有数据的最新一天
        while (date_price.getString(first_day)==null){
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            date = calendar.getTime();
            first_day = dateFormat.format(date);
        }
        System.out.println(first_day);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        String second_day = dateFormat.format(date);  // 有数据的最新一天的前一天
        while (date_price.getString(second_day) == null) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            date = calendar.getTime();
            second_day = dateFormat.format(date);
        }
        System.out.println(second_day);
        String first_day_price_str=date_price.getString(first_day);
        String second_day_price_str=date_price.getString(second_day);
        System.out.println(first_day_price_str);
        System.out.println(second_day_price_str);
        Double first_day_price=Double.valueOf(first_day_price_str);
        Double second_day_price=Double.valueOf(second_day_price_str);
        Double calculation=(first_day_price-second_day_price)/second_day_price;

        calculation=calculation*100;
        BigDecimal bd=new BigDecimal(calculation);
        Double cal_format=bd.setScale(2, RoundingMode.DOWN).doubleValue();
        JSONObject one_pair_number=new JSONObject();
        one_pair_number.put("title",from+"to"+to);
        one_pair_number.put("rate",cal_format);
        result.add(0,one_pair_number);
        return result;
    }

    @PostMapping("searchchartcrypto")
    public JSONObject searchChartCrypto(@RequestBody Map<String,String> map) throws IOException {
        String value=map.get("option");
        String[] parts=value.split("to");
        String from=parts[0];
        String to=parts[1];

        String api_url = "https://data.binance.com/api/v3/klines?symbol="+from+to+"&interval=1d";
        JSONArray jsonArray = exchangeService.getExchangeServiceArray(api_url);

        JSONObject result = new JSONObject();

        int len = jsonArray.size();
        for (int i = 0; i < len; i++) {
            JSONArray oneDay = jsonArray.getJSONArray(i);
            long timestamp = oneDay.getLong(6);
            String close_price = oneDay.getString(4);

            SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
            Date date = new Date(timestamp);
            String formatedDate = dateFormat.format(date);
            result.put(formatedDate, close_price);
        }
        return result;
    }

    @PostMapping("searchcardcrypto")
    public JSONArray searchCardCrypto(@RequestBody Map<String,String> map) throws IOException {
        String value=map.get("option");
        String[] parts=value.split("to");
        String from=parts[0];
        String to=parts[1];

        String api_url = "https://data.binance.com/api/v3/klines?symbol="+from+to+"&interval=1d";
        OkHttp_Get okHttpGet=new OkHttp_Get();
        String run=okHttpGet.run(api_url);
        JSONArray jsonArray= JSON.parseArray(run);
        JSONArray result=new JSONArray();

        List<Long> timestamp_list=new ArrayList<Long>();
        JSONObject timestamp_price_all=new JSONObject();
        int len=jsonArray.size();
        for (int i=0;i<len;i++){
            JSONArray oneDay = jsonArray.getJSONArray(i);
            long timestamp = oneDay.getLong(6);      // 获取收盘时间
            String close_price = oneDay.getString(4);  // 获取收盘价
            timestamp_list.add(timestamp);  // 将每次的时间存进list

            timestamp_price_all.put(Long.toString(timestamp),close_price);  // 将单个 时间-价格 对存进JSONArray
            //System.out.println(jsonObject);
        }

        int timestamp_len=timestamp_list.size();
        for(int i=0;i<timestamp_len;i++){
            Collections.sort(timestamp_list);  // 将timestamp_list 进行排序
        }
        long today_timestamp=timestamp_list.get(timestamp_len-1);  // 最新日期
        long yesterday_timestamp=timestamp_list.get(timestamp_len-2);  // 最新日期的前一天
        String today_price_str=timestamp_price_all.getString(Long.toString(today_timestamp));
        String yesterday_price_str=timestamp_price_all.getString(Long.toString(yesterday_timestamp));
        Double today_price=Double.valueOf(today_price_str);
        Double yesterday_price=Double.valueOf(yesterday_price_str);

        Double calculation=(today_price-yesterday_price)/yesterday_price;
        calculation=calculation*100;
        BigDecimal bd=new BigDecimal(calculation);
        Double cal_format=bd.setScale(2, RoundingMode.DOWN).doubleValue();

        JSONObject one_pair_number=new JSONObject();
        one_pair_number.put("title",from+to);
        one_pair_number.put("rate",cal_format);
        result.add(0,one_pair_number);
        return result;
    }
}




