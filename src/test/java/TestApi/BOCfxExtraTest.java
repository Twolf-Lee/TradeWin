package TestApi;

import com.cs2802.tradewinbackend.utils.OkHttp_Get;

import java.io.IOException;

public class BOCfxExtraTest {
    public static void main(String[] args) throws IOException {
        String api_url="https://ali-waihui.showapi.com//waihui-transform?Authorization=ebcc48918b4641c0863b277230e119d8";
        OkHttp_Get okHttpGet=new OkHttp_Get();
        String run=okHttpGet.run(api_url);
        System.out.println(run);
    }
}
