package com.cs2802.tradewinbackend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.cs2802.tradewinbackend.service.ExchangeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

@RestController
@RequestMapping("dashboard")
@CrossOrigin
public class ExchangeController {
    @Resource
    private ExchangeService exchangeService=new ExchangeService();

    @GetMapping("CNYtoIDR")
    public JSONObject CNYtoIDR() throws IOException {
        String api_url="https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=CNY&to_symbol=IDR&apikey=KITRCC1SJYWEBN7I";
        JSONObject jsonObject=exchangeService.getExchangeService(api_url);
        String data=jsonObject.getString("Time Series FX (Daily)"); //获取到数据（剔除Time Series FX (Daily)）
        JSONObject jsonObject_data=JSON.parseObject(data);  //把data字符串转换成json
        JSONArray keys_arr=new JSONArray();
        //获取key值（日期）
        Iterator<String> keys=jsonObject_data.keySet().iterator();  //创建迭代器，用来存储所有key，这是一个字符串数组
        while (keys.hasNext()){
            String key= keys.next();
            keys_arr.add(key);
        }
        //获取value值（"1. open":"xxx","2. high":"xxx","3. low":"xxx","4. close":"xxx"）
        JSONArray values_arr=new JSONArray();
        Iterator<Object> all_value_pair=jsonObject_data.values().iterator();
        while(all_value_pair.hasNext()){
            Object value_pair=all_value_pair.next();
            values_arr.add(value_pair);
        }
        //获取value中的4. close数据
        JSONObject result=new JSONObject();
        for(int i=0;i<values_arr.size();i++){
            result.put(keys_arr.getString(i),values_arr.getJSONObject(i).getString("4. close"));
        }
        return result;
    }

    @GetMapping("CNYtoFJD")
    public JSONObject CNYtoFJD() throws IOException {
        String api_url="https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=CNY&to_symbol=FJD&apikey=KITRCC1SJYWEBN7I";
        JSONObject jsonObject=exchangeService.getExchangeService(api_url);
        String data=jsonObject.getString("Time Series FX (Daily)"); //获取到数据（剔除Time Series FX (Daily)）
        JSONObject jsonObject_data=JSON.parseObject(data);  //把data字符串转换成json
        JSONArray keys_arr=new JSONArray();
        //获取key值（日期）
        Iterator<String> keys=jsonObject_data.keySet().iterator();  //创建迭代器，用来存储所有key，这是一个字符串数组
        while (keys.hasNext()){
            String key= keys.next();
            keys_arr.add(key);
        }
        //获取value值（"1. open":"xxx","2. high":"xxx","3. low":"xxx","4. close":"xxx"）
        JSONArray values_arr=new JSONArray();
        Iterator<Object> all_value_pair=jsonObject_data.values().iterator();
        while(all_value_pair.hasNext()){
            Object value_pair=all_value_pair.next();
            values_arr.add(value_pair);
        }
        //获取value中的4. close数据
        JSONObject result=new JSONObject();
        for(int i=0;i<values_arr.size();i++){
            result.put(keys_arr.getString(i),values_arr.getJSONObject(i).getString("4. close"));
        }
        return result;
    }

    @GetMapping("IDRtoCNY")
    public JSONObject IDRtoCNY() throws IOException {
        String api_url="https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=IDR&to_symbol=CNY&apikey=KITRCC1SJYWEBN7I";
        JSONObject jsonObject=exchangeService.getExchangeService(api_url);
        String data=jsonObject.getString("Time Series FX (Daily)"); //获取到数据（剔除Time Series FX (Daily)）
        JSONObject jsonObject_data=JSON.parseObject(data);  //把data字符串转换成json
        JSONArray keys_arr=new JSONArray();
        //获取key值（日期）
        Iterator<String> keys=jsonObject_data.keySet().iterator();  //创建迭代器，用来存储所有key，这是一个字符串数组
        while (keys.hasNext()){
            String key= keys.next();
            keys_arr.add(key);
        }
        //获取value值（"1. open":"xxx","2. high":"xxx","3. low":"xxx","4. close":"xxx"）
        JSONArray values_arr=new JSONArray();
        Iterator<Object> all_value_pair=jsonObject_data.values().iterator();
        while(all_value_pair.hasNext()){
            Object value_pair=all_value_pair.next();
            values_arr.add(value_pair);
        }
        //获取value中的4. close数据
        JSONObject result=new JSONObject();
        for(int i=0;i<values_arr.size();i++){
            result.put(keys_arr.getString(i),values_arr.getJSONObject(i).getString("4. close"));
        }
        return result;
    }

    @GetMapping("CNYtoIDRtitle")
    public JSONObject getTitleCNYtoIDR() throws IOException {
        String api_url="https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=CNY&to_symbol=IDR&apikey=KITRCC1SJYWEBN7I";
        JSONObject jsonObject=exchangeService.getExchangeService(api_url);
        String data=jsonObject.getString("Time Series FX (Daily)"); //获取到数据（剔除Time Series FX (Daily)）
        JSONObject jsonObject_data=JSON.parseObject(data);  //把data字符串转换成json
        JSONArray keys_arr=new JSONArray();
        //获取key值（日期）
        Iterator<String> keys=jsonObject_data.keySet().iterator();  //创建迭代器，用来存储所有key，这是一个字符串数组
        while (keys.hasNext()){
            String key= keys.next();
            keys_arr.add(key);
        }
        //获取value值（"1. open":"xxx","2. high":"xxx","3. low":"xxx","4. close":"xxx"）
        JSONArray values_arr=new JSONArray();
        Iterator<Object> all_value_pair=jsonObject_data.values().iterator();
        while(all_value_pair.hasNext()){
            Object value_pair=all_value_pair.next();
            values_arr.add(value_pair);
        }
        //获取value中的4. close数据
        JSONObject message=new JSONObject();
        for(int i=0;i<values_arr.size();i++){
            message.put(keys_arr.getString(i),values_arr.getJSONObject(i).getString("4. close"));
        }
        //获取当天日期的前一天日期，按照标准格式
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        date=calendar.getTime();
        String yesterday=dateFormat.format(date);
        while(message.getString(yesterday)==null){
            calendar.add(Calendar.DAY_OF_MONTH,-1);
            date=calendar.getTime();
            yesterday=dateFormat.format(date);
        }
        String yesterday_price=message.getString(yesterday);

        JSONObject result=new JSONObject();
        result.put("title","CNY->IDR");
        result.put(yesterday,yesterday_price);
        return result;
    }

    @GetMapping("CNYtoFJDtitle")
    public JSONObject getTitleCNYtoFJD() throws IOException {
        String api_url="https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=CNY&to_symbol=FJD&apikey=KITRCC1SJYWEBN7I";
        JSONObject jsonObject=exchangeService.getExchangeService(api_url);
        String data=jsonObject.getString("Time Series FX (Daily)"); //获取到数据（剔除Time Series FX (Daily)）
        JSONObject jsonObject_data=JSON.parseObject(data);  //把data字符串转换成json
        JSONArray keys_arr=new JSONArray();
        //获取key值（日期）
        Iterator<String> keys=jsonObject_data.keySet().iterator();  //创建迭代器，用来存储所有key，这是一个字符串数组
        while (keys.hasNext()){
            String key= keys.next();
            keys_arr.add(key);
        }
        //获取value值（"1. open":"xxx","2. high":"xxx","3. low":"xxx","4. close":"xxx"）
        JSONArray values_arr=new JSONArray();
        Iterator<Object> all_value_pair=jsonObject_data.values().iterator();
        while(all_value_pair.hasNext()){
            Object value_pair=all_value_pair.next();
            values_arr.add(value_pair);
        }
        //获取value中的4. close数据
        JSONObject message=new JSONObject();
        for(int i=0;i<values_arr.size();i++){
            message.put(keys_arr.getString(i),values_arr.getJSONObject(i).getString("4. close"));
        }

        //获取当天日期的前一天日期，按照标准格式
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        date=calendar.getTime();
        String yesterday=dateFormat.format(date);
        while(message.getString(yesterday)==null){
            calendar.add(Calendar.DAY_OF_MONTH,-1);
            date=calendar.getTime();
            yesterday=dateFormat.format(date);
        }
        String yesterday_price=message.getString(yesterday);

        JSONObject result=new JSONObject();
        result.put("title","CNY->FJD");
        result.put(yesterday,yesterday_price);
        return result;
    }

    @GetMapping("IDRtoCNYtitle")
    public JSONObject getTitleIDRtoCNY() throws IOException {
        String api_url="https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=IDR&to_symbol=CNY&apikey=KITRCC1SJYWEBN7I";
        JSONObject jsonObject=exchangeService.getExchangeService(api_url);
        String data=jsonObject.getString("Time Series FX (Daily)"); //获取到数据（剔除Time Series FX (Daily)）
        JSONObject jsonObject_data=JSON.parseObject(data);  //把data字符串转换成json
        JSONArray keys_arr=new JSONArray();
        //获取key值（日期）
        Iterator<String> keys=jsonObject_data.keySet().iterator();  //创建迭代器，用来存储所有key，这是一个字符串数组
        while (keys.hasNext()){
            String key= keys.next();
            keys_arr.add(key);
        }
        //获取value值（"1. open":"xxx","2. high":"xxx","3. low":"xxx","4. close":"xxx"）
        JSONArray values_arr=new JSONArray();
        Iterator<Object> all_value_pair=jsonObject_data.values().iterator();
        while(all_value_pair.hasNext()){
            Object value_pair=all_value_pair.next();
            values_arr.add(value_pair);
        }
        //获取value中的4. close数据
        JSONObject message=new JSONObject();
        for(int i=0;i<values_arr.size();i++){
            message.put(keys_arr.getString(i),values_arr.getJSONObject(i).getString("4. close"));
        }

        //获取当天日期的前一天日期，按照标准格式
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        date=calendar.getTime();
        String yesterday=dateFormat.format(date);
        while(message.getString(yesterday)==null){
            calendar.add(Calendar.DAY_OF_MONTH,-1);
            date=calendar.getTime();
            yesterday=dateFormat.format(date);
        }
        String yesterday_price=message.getString(yesterday);

        JSONObject result=new JSONObject();
        result.put("title","IDR->CNY");
        result.put(yesterday,yesterday_price);
        return result;
    }

    // 通过Binance Api 获取货币最新价格
    @GetMapping("getPrice")
    public JSONArray getPrice() throws IOException {
        String api_url="https://data.binance.com/api/v3/ticker/price";
        JSONArray jsonArray=exchangeService.getExchangeServiceArray(api_url);
        return jsonArray;
    }

    @GetMapping("BTCtoUSDT")
    public JSONArray getKline() throws IOException {
        String api_url="https://data.binance.com/api/v3/klines?symbol=BTCUSDT&interval=1d";
        JSONArray jsonArray=exchangeService.getExchangeServiceArray(api_url);

        JSONArray result=new JSONArray();

        int len=jsonArray.size();
        for(int i=0;i<len;i++){
            JSONArray oneDay=jsonArray.getJSONArray(i);
            long timestamp=oneDay.getLong(0);
            String close_price=oneDay.getString(4);

            SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
            Date date=new Date(timestamp);
            String formatedDate=dateFormat.format(date);

            JSONObject temp=new JSONObject();
            temp.put("date",formatedDate);
            temp.put("price",close_price);
            result.add(i,temp);
        }
        return result;
    }
}
