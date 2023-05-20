package com.cs2802.tradewinbackend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.cs2802.tradewinbackend.service.ExchangeService;
import com.cs2802.tradewinbackend.utils.HttpUtils;
import com.cs2802.tradewinbackend.utils.OkHttp_Get;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("dashboard")
@CrossOrigin
public class ExchangeController {
    @Resource
    private ExchangeService exchangeService = new ExchangeService();

    // 3.Chart 接口
    // 1) 25个国家货币兑换Chart
    @GetMapping("currency-daily-price-chart")
    public JSONObject getExchangeList_Daily_Price() throws IOException {
        // Optimize part
        // Get the date of the day and format the date
        Date date=new Date();
        System.out.println(date);
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String today=dateFormat.format(date);

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-300);
        date=calendar.getTime();  // *
        String earliest_day=dateFormat.format(date);
        // Optimize end

        JSONObject result=new JSONObject();
        String[] from_list=new String[]{"CNY","IDR","THB","PHP","MYR","INR","AED","SAR","BRL","MOP","ZAR","TRY","KRW","TWD"};
        String[] to_list=new String[]{"CNY","IDR","THB","PHP","MYR","INR","AED","SAR","BRL","MOP","ZAR","TRY","KRW","TWD"};
        for(int i=0;i<5;i++) {
            for (int j = 0; j<5; j++) {
                if (j != i) {
                    String api_url = "https://api.apilayer.com/currency_data/timeframe?&source=" + from_list[i] + "&currencies=" + to_list[j] + "&start_date="+earliest_day+"&end_date="+today;
                    OkHttp_Get okHttpGet = new OkHttp_Get();
                    String run = okHttpGet.run(api_url, "apikey", "xvhJYlbcBPOOfYbJsyliEougAxTcqigr");

                    JSONObject jsonObject=JSON.parseObject(run);
                    JSONObject date_value=jsonObject.getJSONObject("quotes");
                    JSONObject one_result=new JSONObject();
                    Iterator<String> keys= date_value.keySet().iterator();
                    while (keys.hasNext()){
                        String one_key=keys.next();
                        JSONObject price_pair=date_value.getJSONObject(one_key);
                        String price=price_pair.getString(from_list[i]+to_list[j]);
                        one_result.put(one_key,price);
                    }
                    result.put(from_list[i]+" to "+to_list[j],one_result);
                }
            }
        }
        return result;
    }

    // 2) BTC to USDT
    @GetMapping("BTCtoUSDT")
    public JSONObject getKline() throws IOException {
        String api_url = "https://data.binance.com/api/v3/klines?symbol=BTCUSDT&interval=1d";
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

    // 3) 货币缩写列表
    @GetMapping("currency-name")
    public JSONArray getCurrencyName(){
        String[] name_array=new String[]{"CNY","IDR","THB","PHP","MYR","INR","AED","SAR","BRL","MOP","ZAR","TRY","KRW","TWD"};
        JSONArray result=new JSONArray();
        for(int i=0;i<5;i++){
            JSONObject name_value=new JSONObject();
            name_value.put("id",Integer.toString(i+1));
            name_value.put("label",name_array[i]);
            result.add(i,name_value);
        }
        return result;
    }

    // 4) 加密货币缩写列表
    @GetMapping("crypto-name")
    public JSONArray getCryptoName(){
        String[] name_array=new String[]{"BTC","ETH","USDT","BNB","DOGE"};
        JSONArray result=new JSONArray();
        for(int i=0;i<5;i++){
            JSONObject name_value=new JSONObject();
            name_value.put("id",Integer.toString(i+1));
            name_value.put("label",name_array[i]);
            result.add(i,name_value);
        }
        return result;
    }

    // 5) 加密货币删减列表
    @GetMapping("crypto-name-3")
    public JSONArray getCryptoNameThree(){
        String[] name_array=new String[]{"BTC","USDT","ETH"};
        JSONArray result=new JSONArray();
        for(int i=0;i<3;i++){
            JSONObject name_value=new JSONObject();
            name_value.put("id",Integer.toString(i+1));
            name_value.put("label",name_array[i]);
            result.add(i,name_value);
        }
        return result;
    }
    // 6) AUD->CNY
    @GetMapping("AUDtoCNY")
    public JSONObject getAUDtoCNY() throws IOException {
        // Optimize part
        // 获取当天日期，并格式化日期
        Date date=new Date();
        System.out.println(date);
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String today=dateFormat.format(date);

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-300);
        date=calendar.getTime();
        String earliest_day=dateFormat.format(date);
        // Optimize end

        String api_url = "https://api.apilayer.com/currency_data/timeframe?&source=AUD&currencies=CNY&start_date="+earliest_day+"&end_date="+today;
        OkHttp_Get okHttpGet = new OkHttp_Get();
        String run = okHttpGet.run(api_url, "apikey", "xvhJYlbcBPOOfYbJsyliEougAxTcqigr");

        JSONObject jsonObject=JSON.parseObject(run);
        JSONObject date_value=jsonObject.getJSONObject("quotes");
        JSONObject one_result=new JSONObject();
        Iterator<String> keys= date_value.keySet().iterator();
        while (keys.hasNext()){
            String one_key=keys.next();
            JSONObject price_pair=date_value.getJSONObject(one_key);
            String price=price_pair.getString("AUDCNY");
            one_result.put(one_key,price);
        }
        return one_result;
    }

    // 2.左侧 List 接口
    // 1) 外汇
    @GetMapping("currency-price-list")
    public JSONArray getExchangeList() throws IOException {
        JSONArray result=new JSONArray();
        String[] from_list=new String[]{"CNY","IDR","THB","PHP","MYR","INR","AED","SAR","BRL","MOP","ZAR","TRY","KRW","TWD"};
        String[] to_list=new String[]{"CNY","IDR","THB","PHP","MYR","INR","AED","SAR","BRL","MOP","ZAR","TRY","KRW","TWD"};
        int index=0;
        for(int i=0;i<5;i++) {
            for (int j = 0; j<5; j++) {
                if (j != i) {
                    //String api_url = "https://api.apilayer.com/currency_data/timeframe?&source=" + from_list[i] + "&currencies=" + to_list[j] ;
                    String api_url="https://api.apilayer.com/currency_data/convert?from="+from_list[i]+"&to="+to_list[j]+"&amount=100";
                    OkHttp_Get okHttpGet = new OkHttp_Get();
                    String run = okHttpGet.run(api_url, "apikey", "xvhJYlbcBPOOfYbJsyliEougAxTcqigr");
                    JSONObject jsonObject=JSON.parseObject(run);

                    // 可以封装
                    JSONObject info=jsonObject.getJSONObject("info");
                    String price=info.getString("quote");
                    String title=from_list[i]+"to"+to_list[j];
                    JSONObject one_result=new JSONObject();
                    one_result.put("symbol",title);
                    one_result.put("price",price);
                    result.add(index,one_result);
                    }
                }
            }
        return result;
    }

    // 2) 加密货币
    @GetMapping("getPrice")
    public JSONArray getPrice() throws IOException {
        String api_url = "https://data.binance.com/api/v3/ticker/price";
        JSONArray jsonArray = exchangeService.getExchangeServiceArray(api_url);
        return jsonArray;
    }

    // 3) list中的 AUD -> CNY
    @GetMapping("AUDtoCNYtitle")
    public JSONArray getAUDtoCNYtitle() throws IOException {
        JSONArray result=new JSONArray();
        String api_url="https://api.apilayer.com/currency_data/convert?from=AUD&to=CNY&amount=100";
        OkHttp_Get okHttpGet = new OkHttp_Get();
        String run = okHttpGet.run(api_url, "apikey", "xvhJYlbcBPOOfYbJsyliEougAxTcqigr");
        JSONObject jsonObject=JSON.parseObject(run);

        JSONObject info=jsonObject.getJSONObject("info");
        String price=info.getString("quote");
        String title="AUDtoCNY";
        JSONObject one_result=new JSONObject();
        one_result.put("symbol",title);
        one_result.put("price",price);
        result.add(0,one_result);
        return result;
    }

    // 4. Card   （最新价格 - 最早价格）/ 最早价格
    // 1) BTC -> USDT
    @GetMapping("cardBTCtoUSDT")
    public JSONArray getRateBTCtoUSDT() throws IOException {
        String api_url = "https://data.binance.com/api/v3/klines?symbol=BTCUSDT&interval=1d";
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
        one_pair_number.put("title","BTCtoUSDT");
        one_pair_number.put("rate",cal_format);
        result.add(0,one_pair_number);
        return result;
    }

    // 2) AUD -> CNY
    @GetMapping("cardAUDtoCNY")
    public JSONArray getRateAUDtoCNY() throws IOException {
        // Optimize part
        // 获取当天日期，并格式化日期
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String today=dateFormat.format(date);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-300);
        date=calendar.getTime();
        String earliest_day=dateFormat.format(date);
        // Optimize end

        String api_url = "https://api.apilayer.com/currency_data/timeframe?&source=AUD&currencies=CNY&start_date="+earliest_day+"&end_date="+today;
        //String api_url = "https://api.apilayer.com/currency_data/timeframe?&source=AUD&currencies=CNY&start_date=2022-07-08&end_date=2023-05-04";
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
            String price=price_pair.getString("AUDCNY");
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
        double first_day_price=Double.valueOf(first_day_price_str);
        double second_day_price=Double.valueOf(second_day_price_str);
        double calculation=(first_day_price-second_day_price)/second_day_price;

        calculation=calculation*100;
        BigDecimal bd=new BigDecimal(calculation);
        double cal_format=bd.setScale(2, RoundingMode.DOWN).doubleValue();
        JSONObject one_pair_number=new JSONObject();
        one_pair_number.put("title","AUDtoCNY");
        one_pair_number.put("rate",cal_format);
        result.add(0,one_pair_number);
        return result;
    }

    // 6. Calculator
    // 1) 外汇 转 外汇 的计算器
    @PostMapping("calculator")
    public JSONObject doCalculator(@RequestBody Map<String, Object> map){
        String from=(String)map.get("option1");  // currency
        String to=(String)map.get("option2");   // currency
        String money=map.get("value").toString();

        String host = "https://ali-waihui.showapi.com";
        String path = "/waihui-transform";
        String method = "GET";
        String appcode = "ebcc48918b4641c0863b277230e119d8";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("fromCode", from);
        querys.put("money", money);
        querys.put("toCode", to);

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //System.out.println("***"+response.getEntity().getContent());
            //获取response的body
            HttpEntity entity = response.getEntity();
            String responsebody= EntityUtils.toString(entity);
            //System.out.println(responsebody);
            String[] lines = responsebody.split("\n");
            Pattern pattern = Pattern.compile("\"showapi_res_body\":\\s*(\\{.*?\\})");
            Matcher matcher = pattern.matcher(lines[5]);
            if (matcher.find()) {
                String output = matcher.group(1);
                //System.out.println(result);
                JSONObject jsonObject= JSON.parseObject(output);
                String money_value=jsonObject.getString("money");
                JSONObject result=new JSONObject();
                result.put("money",money_value);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 2) 加密 转 外汇 的计算器
    @PostMapping("calculator-crypto-currency")
    public JSONObject calculatorCryptoCurrency(@RequestBody Map<String, Object> map) throws IOException {
        String from=(String)map.get("option1");  // crypto
        String to=(String)map.get("option2");   // currency
        double money_value=Double.parseDouble(map.get("value").toString());

        System.out.println(from);
        System.out.println(to);
        System.out.println(money_value);

        // 例子  1 ETH (传参) = 1807 USD (默认)
        OkHttp_Get okHttpGet=new OkHttp_Get();
        String run=okHttpGet.run("https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol="+from,"X-CMC_PRO_API_KEY","4c2d5cbd-6aaa-4025-af79-f23305a89e7e"); //4c2d5cbd-6aaa-4025-af79-f23305a89e7e
        JSONObject new_api_object= JSON.parseObject(run);
        String price=new_api_object.getJSONObject("data").getJSONObject(from).getJSONObject("quote").getJSONObject("USD").getString("price");
        System.out.println(price);

        String host = "https://ali-waihui.showapi.com";
        String path = "/waihui-transform";
        String method = "GET";
        String appcode = "ebcc48918b4641c0863b277230e119d8";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("fromCode", "USD");
        querys.put("money", price);
        querys.put("toCode", to);

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //System.out.println("***"+response.getEntity().getContent());
            //获取response的body
            HttpEntity entity = response.getEntity();
            String responsebody= EntityUtils.toString(entity);
            //System.out.println(responsebody);
            String[] lines = responsebody.split("\n");
            Pattern pattern = Pattern.compile("\"showapi_res_body\":\\s*(\\{.*?\\})");
            Matcher matcher = pattern.matcher(lines[5]);
            if (matcher.find()) {
                String output = matcher.group(1);

                JSONObject jsonObject= JSON.parseObject(output);
                String usd_currency=jsonObject.getString("money");

                Double usd_currency_value=Double.valueOf(usd_currency);
                System.out.println(usd_currency_value);

                Double res=usd_currency_value*money_value;
                String result_str=String.valueOf(res);
                System.out.println(result_str);

                JSONObject resultObject=new JSONObject();
                resultObject.put("money",result_str);
                return resultObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*@PostMapping("calculator-currency-crypto")
    public JSONObject calculatorCurrencyCrypto(@RequestBody Map<String, String> map){
        String value=map.get("option");
        String[] parts=value.split("to");
        String from=parts[0]; // currency
        String to=parts[1];  // crypto
        String money=map.get("valuecal");

        String host = "https://ali-waihui.showapi.com";
        String path = "/waihui-transform";
        String method = "GET";
        String appcode = "ebcc48918b4641c0863b277230e119d8";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("fromCode", from);
        querys.put("money", money);
        querys.put("toCode", "USD");

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //System.out.println("***"+response.getEntity().getContent());
            //获取response的body
            HttpEntity entity = response.getEntity();
            String responsebody= EntityUtils.toString(entity);
            //System.out.println(responsebody);
            String[] lines = responsebody.split("\n");
            Pattern pattern = Pattern.compile("\"showapi_res_body\":\\s*(\\{.*?\\})");
            Matcher matcher = pattern.matcher(lines[5]);
            if (matcher.find()) {
                String output = matcher.group(1);
                //System.out.println(result);
                JSONObject jsonObject= JSON.parseObject(output);
                String currency_USD=jsonObject.getString("money");
                double currency_USD_value=Double.valueOf(currency_USD);


                // 例子  1 ETH (传参) = 1807 USD (默认)
                OkHttp_Get okHttpGet=new OkHttp_Get();
                String run=okHttpGet.run("https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol="+to,"X-CMC_PRO_API_KEY","4c2d5cbd-6aaa-4025-af79-f23305a89e7e"); //4c2d5cbd-6aaa-4025-af79-f23305a89e7e
                JSONObject new_api_object= JSON.parseObject(run);
                String price=new_api_object.getJSONObject("data").getJSONObject(to).getJSONObject("quote").getJSONObject("USD").getString("price");
                double price_value=Double.valueOf(price);
                double res=currency_USD_value*(1/price_value);
                String result_str=String.valueOf(res);
                JSONObject resultObject=new JSONObject();
                resultObject.put("money",result_str);
                return resultObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/


   /* @GetMapping("CNY-IDR-FJD")
    public JSONObject getCNY_IDR_FJDList() throws IOException {
        List<String> api_list = new ArrayList<String>();  // 创建一个存放api url的列表
        JSONObject result = new JSONObject();  // 拼接所有的apiObject

        String cny_idr = "https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=CNY&to_symbol=IDR&apikey=KITRCC1SJYWEBN7I";
        String cny_fjd = "https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=CNY&to_symbol=FJD&apikey=KITRCC1SJYWEBN7I";
        String idr_cny = "https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=IDR&to_symbol=CNY&apikey=KITRCC1SJYWEBN7I";
        api_list.add(cny_idr);
        api_list.add(cny_fjd);
        api_list.add(idr_cny);

        for (int url_index = 0; url_index < api_list.size(); url_index++) {
            JSONObject jsonObject = exchangeService.getExchangeService(api_list.get(url_index));
            String data = jsonObject.getString("Time Series FX (Daily)"); //获取到数据（剔除Time Series FX (Daily)）
            JSONObject jsonObject_data = JSON.parseObject(data);  //把data字符串转换成json
            JSONArray keys_arr = new JSONArray();
            //获取key值（日期）
            Iterator<String> keys = jsonObject_data.keySet().iterator();  //创建迭代器，用来存储所有key，这是一个字符串数组
            while (keys.hasNext()) {
                String key = keys.next();
                keys_arr.add(key);
            }
            //获取value值（"1. open":"xxx","2. high":"xxx","3. low":"xxx","4. close":"xxx"）
            JSONArray values_arr = new JSONArray();
            Iterator<Object> all_value_pair = jsonObject_data.values().iterator();
            while (all_value_pair.hasNext()) {
                Object value_pair = all_value_pair.next();
                values_arr.add(value_pair);
            }
            //获取value中的4. close数据
            JSONObject oneApiObject = new JSONObject();

            for (int i = 0; i < values_arr.size(); i++) {
                oneApiObject.put(keys_arr.getString(i), values_arr.getJSONObject(i).getString("4. close"));
                //用正则表达式来获取货币缩写字段
                String regex = "from_symbol=(\\w+)&to_symbol=(\\w+)";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(api_list.get(url_index));
                if (matcher.find()) {
                    String fromSymbol = matcher.group(1);
                    String toSymbol = matcher.group(2);
                    String combine = fromSymbol + " to " + toSymbol;
                    result.put(combine, oneApiObject);  // 拼接apiObject
                } else {
                    // 字段匹配失败
                    System.out.println("No match found.");
                }
            }
        }
        return result;
    }*/

    @GetMapping("CNYtoIDR")
    public JSONObject CNYtoIDR() throws IOException {
        String api_url = "https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=CNY&to_symbol=IDR&apikey=KITRCC1SJYWEBN7I";
        JSONObject jsonObject = exchangeService.getExchangeService(api_url);
        String data = jsonObject.getString("Time Series FX (Daily)"); //获取到数据（剔除Time Series FX (Daily)）
        JSONObject jsonObject_data = JSON.parseObject(data);  //把data字符串转换成json
        JSONArray keys_arr = new JSONArray();
        //获取key值（日期）
        Iterator<String> keys = jsonObject_data.keySet().iterator();  //创建迭代器，用来存储所有key，这是一个字符串数组
        while (keys.hasNext()) {
            String key = keys.next();
            keys_arr.add(key);
        }
        //获取value值（"1. open":"xxx","2. high":"xxx","3. low":"xxx","4. close":"xxx"）
        JSONArray values_arr = new JSONArray();
        Iterator<Object> all_value_pair = jsonObject_data.values().iterator();
        while (all_value_pair.hasNext()) {
            Object value_pair = all_value_pair.next();
            values_arr.add(value_pair);
        }
        //获取value中的4. close数据
        JSONObject result = new JSONObject();
        for (int i = 0; i < values_arr.size(); i++) {
            result.put(keys_arr.getString(i), values_arr.getJSONObject(i).getString("4. close"));
        }
        return result;
    }

    @GetMapping("CNYtoFJD")
    public JSONObject CNYtoFJD() throws IOException {
        String api_url = "https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=CNY&to_symbol=FJD&apikey=KITRCC1SJYWEBN7I";
        JSONObject jsonObject = exchangeService.getExchangeService(api_url);
        String data = jsonObject.getString("Time Series FX (Daily)"); //获取到数据（剔除Time Series FX (Daily)）
        JSONObject jsonObject_data = JSON.parseObject(data);  //把data字符串转换成json
        JSONArray keys_arr = new JSONArray();
        //获取key值（日期）
        Iterator<String> keys = jsonObject_data.keySet().iterator();  //创建迭代器，用来存储所有key，这是一个字符串数组
        while (keys.hasNext()) {
            String key = keys.next();
            keys_arr.add(key);
        }
        //获取value值（"1. open":"xxx","2. high":"xxx","3. low":"xxx","4. close":"xxx"）
        JSONArray values_arr = new JSONArray();
        Iterator<Object> all_value_pair = jsonObject_data.values().iterator();
        while (all_value_pair.hasNext()) {
            Object value_pair = all_value_pair.next();
            values_arr.add(value_pair);
        }
        //获取value中的4. close数据
        JSONObject result = new JSONObject();
        for (int i = 0; i < values_arr.size(); i++) {
            result.put(keys_arr.getString(i), values_arr.getJSONObject(i).getString("4. close"));
        }
        return result;
    }

    @GetMapping("IDRtoCNY")
    public JSONObject IDRtoCNY() throws IOException {
        String api_url = "https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=IDR&to_symbol=CNY&apikey=KITRCC1SJYWEBN7I";
        JSONObject jsonObject = exchangeService.getExchangeService(api_url);
        String data = jsonObject.getString("Time Series FX (Daily)"); //获取到数据（剔除Time Series FX (Daily)）
        JSONObject jsonObject_data = JSON.parseObject(data);  //把data字符串转换成json
        JSONArray keys_arr = new JSONArray();
        //获取key值（日期）
        Iterator<String> keys = jsonObject_data.keySet().iterator();  //创建迭代器，用来存储所有key，这是一个字符串数组
        while (keys.hasNext()) {
            String key = keys.next();
            keys_arr.add(key);
        }
        //获取value值（"1. open":"xxx","2. high":"xxx","3. low":"xxx","4. close":"xxx"）
        JSONArray values_arr = new JSONArray();
        Iterator<Object> all_value_pair = jsonObject_data.values().iterator();
        while (all_value_pair.hasNext()) {
            Object value_pair = all_value_pair.next();
            values_arr.add(value_pair);
        }
        //获取value中的4. close数据
        JSONObject result = new JSONObject();
        for (int i = 0; i < values_arr.size(); i++) {
            result.put(keys_arr.getString(i), values_arr.getJSONObject(i).getString("4. close"));
        }
        return result;
    }

    @GetMapping("CNYtoIDRtitle")
    public JSONObject getTitleCNYtoIDR() throws IOException {
        String api_url = "https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=CNY&to_symbol=IDR&apikey=KITRCC1SJYWEBN7I";
        JSONObject jsonObject = exchangeService.getExchangeService(api_url);
        String data = jsonObject.getString("Time Series FX (Daily)"); //获取到数据（剔除Time Series FX (Daily)）
        JSONObject jsonObject_data = JSON.parseObject(data);  //把data字符串转换成json
        JSONArray keys_arr = new JSONArray();
        //获取key值（日期）
        Iterator<String> keys = jsonObject_data.keySet().iterator();  //创建迭代器，用来存储所有key，这是一个字符串数组
        while (keys.hasNext()) {
            String key = keys.next();
            keys_arr.add(key);
        }
        //获取value值（"1. open":"xxx","2. high":"xxx","3. low":"xxx","4. close":"xxx"）
        JSONArray values_arr = new JSONArray();
        Iterator<Object> all_value_pair = jsonObject_data.values().iterator();
        while (all_value_pair.hasNext()) {
            Object value_pair = all_value_pair.next();
            values_arr.add(value_pair);
        }
        //获取value中的4. close数据
        JSONObject message = new JSONObject();
        for (int i = 0; i < values_arr.size(); i++) {
            message.put(keys_arr.getString(i), values_arr.getJSONObject(i).getString("4. close"));
        }
        //获取当天日期的前一天日期，按照标准格式
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        String yesterday = dateFormat.format(date);
        while (message.getString(yesterday) == null) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            date = calendar.getTime();
            yesterday = dateFormat.format(date);
        }
        String yesterday_price = message.getString(yesterday);

        JSONObject result = new JSONObject();
        result.put("title", "CNY->IDR");
        result.put(yesterday, yesterday_price);
        return result;
    }

    @GetMapping("CNYtoFJDtitle")
    public JSONObject getTitleCNYtoFJD() throws IOException {
        String api_url = "https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=CNY&to_symbol=FJD&apikey=KITRCC1SJYWEBN7I";
        JSONObject jsonObject = exchangeService.getExchangeService(api_url);
        String data = jsonObject.getString("Time Series FX (Daily)"); //获取到数据（剔除Time Series FX (Daily)）
        JSONObject jsonObject_data = JSON.parseObject(data);  //把data字符串转换成json
        JSONArray keys_arr = new JSONArray();
        //获取key值（日期）
        Iterator<String> keys = jsonObject_data.keySet().iterator();  //创建迭代器，用来存储所有key，这是一个字符串数组
        while (keys.hasNext()) {
            String key = keys.next();
            keys_arr.add(key);
        }
        //获取value值（"1. open":"xxx","2. high":"xxx","3. low":"xxx","4. close":"xxx"）
        JSONArray values_arr = new JSONArray();
        Iterator<Object> all_value_pair = jsonObject_data.values().iterator();
        while (all_value_pair.hasNext()) {
            Object value_pair = all_value_pair.next();
            values_arr.add(value_pair);
        }
        //获取value中的4. close数据
        JSONObject message = new JSONObject();
        for (int i = 0; i < values_arr.size(); i++) {
            message.put(keys_arr.getString(i), values_arr.getJSONObject(i).getString("4. close"));
        }

        //获取当天日期的前一天日期，按照标准格式
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        String yesterday = dateFormat.format(date);
        while (message.getString(yesterday) == null) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            date = calendar.getTime();
            yesterday = dateFormat.format(date);
        }
        String yesterday_price = message.getString(yesterday);

        JSONObject result = new JSONObject();
        result.put("title", "CNY->FJD");
        result.put(yesterday, yesterday_price);
        return result;
    }

    @GetMapping("IDRtoCNYtitle")
    public JSONObject getTitleIDRtoCNY() throws IOException {
        String api_url = "https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=IDR&to_symbol=CNY&apikey=KITRCC1SJYWEBN7I";
        JSONObject jsonObject = exchangeService.getExchangeService(api_url);
        String data = jsonObject.getString("Time Series FX (Daily)"); //获取到数据（剔除Time Series FX (Daily)）
        JSONObject jsonObject_data = JSON.parseObject(data);  //把data字符串转换成json
        JSONArray keys_arr = new JSONArray();
        //获取key值（日期）
        Iterator<String> keys = jsonObject_data.keySet().iterator();  //创建迭代器，用来存储所有key，这是一个字符串数组
        while (keys.hasNext()) {
            String key = keys.next();
            keys_arr.add(key);
        }
        //获取value值（"1. open":"xxx","2. high":"xxx","3. low":"xxx","4. close":"xxx"）
        JSONArray values_arr = new JSONArray();
        Iterator<Object> all_value_pair = jsonObject_data.values().iterator();
        while (all_value_pair.hasNext()) {
            Object value_pair = all_value_pair.next();
            values_arr.add(value_pair);
        }
        //获取value中的4. close数据
        JSONObject message = new JSONObject();
        for (int i = 0; i < values_arr.size(); i++) {
            message.put(keys_arr.getString(i), values_arr.getJSONObject(i).getString("4. close"));
        }

        //获取当天日期的前一天日期，按照标准格式
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        String yesterday = dateFormat.format(date);
        while (message.getString(yesterday) == null) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            date = calendar.getTime();
            yesterday = dateFormat.format(date);
        }
        String yesterday_price = message.getString(yesterday);

        JSONObject result = new JSONObject();
        result.put("title", "IDR->CNY");
        result.put(yesterday, yesterday_price);
        return result;
    }

    @GetMapping("currencyList")
    public JSONArray getPriceAli() throws Exception {
        JSONArray jsonArray=new JSONArray();
        String[] from_list=new String[]{"CNY","IDR","THB","PHP","MYR","INR","AED","SAR","BRL","MOP","ZAR","TRY","KRW","TWD"};
        String[] to_list=new String[]{"CNY","IDR","THB","PHP","MYR","INR","AED","SAR","BRL","MOP","ZAR","TRY","KRW","TWD"};
        int index=0;
        for(int i=0;i<from_list.length;i++){
            for (int j=0;j<to_list.length;j++){
                if(j!=i){
                    String host = "https://ali-waihui.showapi.com";
                    String path = "/waihui-transform";
                    String method = "GET";
                    String appcode = "ebcc48918b4641c0863b277230e119d8";
                    Map<String, String> headers = new HashMap<String, String>();
                    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
                    headers.put("Authorization", "APPCODE " + appcode);
                    Map<String, String> querys = new HashMap<String, String>();
                    querys.put("fromCode", from_list[i]);
                    querys.put("money", "100");
                    querys.put("toCode", to_list[j]);

                    try {
                        /**
                         * 重要提示如下:
                         * HttpUtils请从
                         * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
                         * 下载
                         *
                         * 相应的依赖请参照
                         * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
                         */
                        HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
                        //System.out.println("***"+response.getEntity().getContent());
                        //获取response的body
                        HttpEntity entity = response.getEntity();
                        String responsebody=EntityUtils.toString(entity);
                        String[] lines = responsebody.split("\n");
                        Pattern pattern = Pattern.compile("\"showapi_res_body\":\\s*(\\{.*?\\})");
                        Matcher matcher = pattern.matcher(lines[5]);

                        if (matcher.find()) {
                            String value = matcher.group(1);
                            System.out.println(value);
                            JSONObject jsonObject= JSON.parseObject(value);
                            String rate=jsonObject.getString("rate");
                            JSONObject one=new JSONObject();
                            one.put(from_list[i]+to_list[j],rate);
                            jsonArray.add(index,one);
                        }
                        index++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return jsonArray;
    }
}
