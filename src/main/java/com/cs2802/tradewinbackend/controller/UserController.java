package com.cs2802.tradewinbackend.controller;

import com.cs2802.tradewinbackend.pojo.User;
import com.cs2802.tradewinbackend.service.MailService;
import com.cs2802.tradewinbackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;


@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private MailService mailService;

    /*注册账号*/
    @PostMapping("create")
    @CrossOrigin
    public Map<String, Object> createAccount(User user){

        return userService.createAccount(user);
    }

    //登陆账号
    @PostMapping("login")
    public Map<String, Object> loginAccount(User user){

        return userService.loginAccount(user);
    }


    @GetMapping("activate_account")
    public Map<String, Object> activateAccount(String confirmCode){

        return userService.activateAccount(confirmCode);
    }


}
