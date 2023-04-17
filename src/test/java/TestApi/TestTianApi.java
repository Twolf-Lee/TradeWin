package TestApi;

import com.alibaba.fastjson.JSON;
import com.cs2802.tradewinbackend.utils.OkHttp_Get;

import java.io.IOException;

public class TestTianApi {
    //测试天行数据api （国际新闻）
    public static void main(String[] args) throws IOException {
        /*String tianapi_data = "";
        try {
            URL url = new URL( "https://apis.tianapi.com/generalnews/index");
            HttpURLConnection  conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            OutputStream outputStream = conn.getOutputStream();
            String content = "key=13512351fbda3dbbd5172fb1c8674296";
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
            InputStream inputStream = conn.getInputStream();
            byte[] data = new byte[1024];
            StringBuilder tianapi = new StringBuilder();
            while (inputStream.read(data) != -1) {
                String t = new String(data);
                tianapi.append(t);
            }
            tianapi_data = tianapi.toString();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(tianapi_data);*/
        OkHttp_Get api=new OkHttp_Get();
        String run=api.run("https://apis.tianapi.com/caijing/index?key=13512351fbda3dbbd5172fb1c8674296&num=10");
        JSON json=JSON.parseObject(run);
        System.out.println(json);
    }
}
