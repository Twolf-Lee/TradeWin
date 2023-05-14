package MyProfile;

import com.cs2802.tradewinbackend.utils.JwtUtil;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

public class MyprofilTest {

    @Test
    public void tokenTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODM5NjI4NTUsImVtYWlsIjoiZWxzaWU5OTA1MDRAZ21haWwuY29tIn0.p6XiQ2QL1__wOXQjsNa8TwXYG7FIv6uVlt17moIYyWY");
        String emailByToken = JwtUtil.getEmailByToken(request);
        System.out.println(emailByToken);
    }
}
