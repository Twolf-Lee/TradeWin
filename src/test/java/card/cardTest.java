package card;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cs2802.tradewinbackend.utils.OkHttp_Get;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class cardTest {
    public static void main(String[] args) throws IOException {

        String api_url = "https://data.binance.com/api/v3/klines?symbol=BTCUSDT&interval=1d";
        OkHttp_Get okHttpGet=new OkHttp_Get();
        String run=okHttpGet.run(api_url);
        JSONArray jsonArray= JSON.parseArray(run);
        //System.out.println(jsonArray);

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
        //System.out.println(today_price);
        //System.out.println(yesterday_price);

       /* NumberFormat nt = NumberFormat.getPercentInstance();  // 除法百分数设置
        nt.setMinimumFractionDigits(2);
        String calculation=nt.format((today_price-yesterday_price)/yesterday_price);*/

        Double calculation=(today_price-yesterday_price)/yesterday_price;
        calculation=calculation*100;
        BigDecimal bd=new BigDecimal(calculation);
        Double cal_format=bd.setScale(2, RoundingMode.DOWN).doubleValue();
        System.out.println(cal_format);
        JSONObject one_pair_number=new JSONObject();
        one_pair_number.put("title","BTCtoUSDT");
        one_pair_number.put("rate",cal_format);
        System.out.println(one_pair_number);
    }
}
