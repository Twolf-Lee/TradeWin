package TestApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cs2802.tradewinbackend.utils.OkHttp_Get;

import java.io.IOException;
import java.util.Iterator;

public class test1 {
    public static void main(String[] args) throws IOException {
        String[] from_symbol={"CNY","IDR","FJD"};  // CNY 人民币 IDR 印度尼西亚 FJD 斐济
        String[] to_symbol={"CNY","IDR","FJD"};
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(j==i){
                    continue;
                }
                OkHttp_Get okHttpApi = new OkHttp_Get();
                String run=okHttpApi.run("https://www.alphavantage.co/query?function=FX_DAILY&from_symbol="+from_symbol[i]+"&to_symbol="+to_symbol[j]+"&apikey=KITRCC1SJYWEBN7I");
                //String run=okHttpApi.run("https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=EUR&to_symbol=USD&apikey=KITRCC1SJYWEBN7I");
                //System.out.println("Test the daily FX prices(from EUR to USD):");
                //System.out.println(run);
                JSONObject jsonObject= JSON.parseObject(run);
                //System.out.println(jsonObject);

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
                JSONArray result=new JSONArray();
                for(int item=0;item<values_arr.size();item++){
                    JSONObject close_json=new JSONObject();
                    close_json.put("date",keys_arr.getString(item));
                    close_json.put("close_price",values_arr.getJSONObject(item).getString("4. close"));
                    result.add(item,close_json);
                }
                /*TreeMap<String, Object> sortedMap = new TreeMap<>(close_json);
                JSONObject sortedCloseJson = new JSONObject(sortedMap);
                String sorted = JSON.toJSONString(sortedCloseJson);*/
                System.out.println(result);
                }
            }

        }
}
