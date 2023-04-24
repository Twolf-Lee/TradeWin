package TestApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cs2802.tradewinbackend.utils.OkHttp_Get;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class AlphaNewsTest {
    public static void main(String[] args) throws IOException {
        String api_url="https://www.alphavantage.co/query?function=NEWS_SENTIMENT&tickers=COIN,CRYPTO:BTC,FOREX:USD&time_from=20220410T0130&limit=200&apikey=KITRCC1SJYWEBN7I";
        OkHttp_Get okHttpGet=new OkHttp_Get();
        String str=okHttpGet.run(api_url);
        JSONObject jsonObject= JSON.parseObject(str);

        String values=jsonObject.getString("feed");
        JSONArray valuesArray=JSON.parseArray(values);

        int len=valuesArray.size();
        JSONArray resultArray=new JSONArray();
        for(int i=0;i<len;i++){
            String innerValueStr=valuesArray.getString(i);
            JSONObject innerValueObject=JSON.parseObject(innerValueStr);
            JSONObject eachNew=new JSONObject();
            String summary=innerValueObject.getString("summary");  //content
            String url=innerValueObject.getString("url");  // href
            String title=innerValueObject.getString("title");  // title
            String banner_image=innerValueObject.getString("banner_image");  // avatar
            eachNew.put("href",url);
            eachNew.put("title",title);
            eachNew.put("avatar",banner_image);
            eachNew.put("content",summary);
            resultArray.add(i,eachNew);
        }

        /*System.out.println("content: "+content);
        System.out.println("href: "+href);
        System.out.println("title: "+title);
        System.out.println("image: "+image);
        System.out.println(innerValueObject);*/
        System.out.println(resultArray);
    }
}
