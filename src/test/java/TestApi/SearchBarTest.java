package TestApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cs2802.tradewinbackend.utils.HttpUtils;
import com.cs2802.tradewinbackend.utils.OkHttp_Get;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchBarTest {
    public static void main(String[] args) throws IOException {
        // 假设前端搜索框传递过来的数据有： 货币1的缩写, 货币2的缩写
        String currency1="CNY";
        String currency2="AED";

        // 后端要根据传递的变量从货币列表里获取值
        String[] currency_list=new String[]{"CNY","IDR","THB","PHP","MYR","INR","AED","SAR","BRL","MOP","ZAR","TRY","KRW","TWD"};

        // 如果传递过来的两个字符串在 货币列表里 则生成API的url
        if(Arrays.asList(currency_list).contains(currency1) && Arrays.asList(currency_list).contains(currency2)){
            String api_url="https://api.apilayer.com/currency_data/timeframe?&source=CNY&currencies=IDR&start_date=2022-05-01&end_date=2023-04-20";
            OkHttp_Get okHttpGet=new OkHttp_Get();
            String run=okHttpGet.run(api_url,"apikey", "xvhJYlbcBPOOfYbJsyliEougAxTcqigr");
            System.out.println(run);
        }
    }
}
