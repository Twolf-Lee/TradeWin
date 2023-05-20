package com.cs2802.tradewinbackend.controller;

import com.cs2802.tradewinbackend.pojo.User;
import com.cs2802.tradewinbackend.service.MailService;
import com.cs2802.tradewinbackend.service.UserService;
import com.cs2802.tradewinbackend.utils.JwtUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private MailService mailService;
    @Resource
    private RedisTemplate redisTemplate;
//    @Resource
//    private StringRedisTemplate redisTemplate;

    /*注册账号*/
    @PostMapping("create")
    @CrossOrigin
    public Map<String, Object> createAccount(User user){

        return userService.createAccount(user);
    }

    //登陆账号
    @PostMapping("login")
    public Map<String, Object> loginAccount(@RequestParam("email") String email,
                                            @RequestParam("password") String password){
        return userService.loginAccount(email, password);
    }

    @PostMapping("loginByPhoneNumber")
    public Map<String, Object> loginAccountByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber,
                                            @RequestParam("password") String password){
        return userService.loginAccountByPhoneNumber(phoneNumber, password);
    }

    @GetMapping("activate_account")
    public Map<String, Object> activateAccount(String confirmCode){

        return userService.activateAccount(confirmCode);
    }
    //从token中获取登陆用户信息
    @GetMapping("getUserOfLogin")
    public Map<String, Object> getUserOfLogin(HttpServletRequest request) throws UnsupportedEncodingException{
        //获取header中的参数
        String token = request.getHeader("token");

        Object user = redisTemplate.opsForValue().get(token);
        Map<String,Object> map = new HashMap<>();
        //加一个判断user是否为null
        if (user != null) {
            map.put("user",user);
        }
        return map;
    }

    //注销
    @GetMapping("logout")
    public Map<String, Object> logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        Boolean delete = redisTemplate.delete(token);
        Map<String, Object> map = new HashMap<>();
        map.put("200","Log Out");
        return map;
    }

    //获取登录有效剩余时间

}



