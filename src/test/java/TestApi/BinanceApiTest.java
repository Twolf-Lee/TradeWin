package TestApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cs2802.tradewinbackend.utils.OkHttp_Get;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BinanceApiTest {
    public static void main(String[] args) throws IOException {
        OkHttp_Get okHttpGet=new OkHttp_Get();
        //String str=okHttpGet.run("https://data.binance.com/api/v3/ticker/price?symbol=CNYIDR");
        String str=okHttpGet.run("https://data.binance.com/api/v3/klines?symbol=BTCUSDT&interval=1d");
        JSONArray jsonArray= JSON.parseArray(str);

        int len=jsonArray.size();
        for(int i=0;i<len;i++){
            JSONArray oneDay=jsonArray.getJSONArray(i);
            long timestamp=oneDay.getLong(0);
            String close_price=oneDay.getString(4);

            SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
            Date date=new Date(timestamp);
            String calendal=dateFormat.format(date);
            System.out.println(calendal);
            System.out.println(close_price);
        }
    }
}
