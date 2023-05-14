package com.cs2802.tradewinbackend.controller;

import com.cs2802.tradewinbackend.pojo.TempUser;
import com.cs2802.tradewinbackend.service.MyProfileService;
import com.cs2802.tradewinbackend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("myProfile")
@CrossOrigin
public class MyProfileController {

    @Resource
    private MyProfileService myProfileService;

    @GetMapping("/view/information")
    public List<TempUser> getMyProfile(HttpServletRequest request) {
        String token = request.getHeader("token");
        System.out.println("come in");
        System.out.println(token);
        if (token != null) {
            String email = JwtUtil.getEmailByToken(request);
            return myProfileService.findMyProfile(email);
        } else {
            return null;
        }
    }
}
