package com.cs2802.tradewinbackend.service;

import com.cs2802.tradewinbackend.mapper.MyProfileMapper;
import com.cs2802.tradewinbackend.pojo.TempUser;
import com.cs2802.tradewinbackend.utils.JwtUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MyProfileService {
    @Resource
    private MyProfileMapper myProfileMapper;

    public List<TempUser> findMyProfile(String email) {
        return myProfileMapper.findMyProfileByEmail(email);
    }
}
