package TestApi;

import com.cs2802.tradewinbackend.utils.OkHttp_Get;

import java.io.IOException;

public class RollToolsApiTest {
    public static void main(String[] args) throws IOException {
        OkHttp_Get okHttpGet=new OkHttp_Get();
        String run=okHttpGet.run("https://www.mxnzp.com/api/exchange_rate/list?app_id=mffjl2t8nbnojsya&app_secret=VGJBTmVyTmpXb0hCSHBYWHlRalhlQT09");
        System.out.println(run);
    }
}
