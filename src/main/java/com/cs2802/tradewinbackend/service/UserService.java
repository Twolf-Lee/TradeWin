package com.cs2802.tradewinbackend.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.cs2802.tradewinbackend.mapper.UserMapper;
import com.cs2802.tradewinbackend.pojo.User;
import com.cs2802.tradewinbackend.utils.JwtUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private MailService mailService;
    @Resource
    private RedisTemplate redisTemplate;
    /*注册账号*/
    @Transactional
    public Map<String,Object> createAccount(User user){
        //雪花算法生成确认码
        String confirmCode = IdUtil.getSnowflake(1,1).nextIdStr();
        //salt
        String salt = RandomUtil.randomString(6);
        //加密密码：原始密码+salt
        String md5Pwd = SecureUtil.md5(user.getPassword() + salt);
        //激活的失效时间
        LocalDateTime ldt = LocalDateTime.now().plusMinutes(1);
        //初始化账号信息
        user.setEmail(user.getEmail());
        user.setUsername(user.getUsername());
        user.setSalt(salt);
        user.setPassword(md5Pwd);
        user.setConfirmCode(confirmCode);
        user.setActivationTime(ldt);
        user.setIsValid((byte) 0);
        user.setGender(user.getGender());
        user.setPhoneNumber(user.getPhoneNumber());
        //新增账号
        int result = userMapper.insertUser(user);
        Map<String, Object> resultMap = new HashMap<>();
        if (result > 0){
            //发送邮件
            String activateUrl = "http://localhost:8080/user/activate_account?confirmCode=" +confirmCode;
            mailService.senMailForActivateAccount(activateUrl,user.getEmail());
            resultMap.put("status",200);
        }else {
            resultMap.put("status",400);
        }
        return resultMap;
    }


    public Map<String, Object> loginAccount(String email, String password){
        Map<String, Object> resultMap = new HashMap<>();
        //根据邮箱查询用户
        List<User> userList = userMapper.selectUserByEmail(email);
        //查询不到结果，返回：该账户不存在或未激活
        if (userList == null || userList.isEmpty()){
            resultMap.put("code",400);
            resultMap.put("messgae","The account is not exist.");
            return resultMap;
        }
        //查询到多个用户，返回：账号异常，请联系管理员
        if (userList.size() > 1){
            resultMap.put("code",400);
            resultMap.put("messgae","The account has some problem.");
            return resultMap;
        }
        //查询到一个用户，进行密码比对
        User u = userList.get(0);
        String md5pwd = SecureUtil.md5(password + u.getSalt());
        if (!u.getPassword().equals(md5pwd)){
            resultMap.put("code",400);
            resultMap.put("messgae","Password is wrong");
            return resultMap;
        }
        //登录成功
        String token = JwtUtil.sign(email,password);
        //存到Redis数据库
        redisTemplate.opsForValue().set("token",String.valueOf(u),Duration.ofMinutes(5));
            resultMap.put("code", "200");
            resultMap.put("message","Login Successgully");
            resultMap.put("token", token);
            return resultMap;
    }

    public Map<String, Object> activateAccount(String confirmCode) {
        Map<String, Object> resultMap = new HashMap<>();
        //根据确认码查询用户
        User user = userMapper.selectUserByCinfirmCode(confirmCode);
        //判断激活时间是否超时
        Boolean after = LocalDateTime.now().isAfter(user.getActivationTime());
        if (after){
            resultMap.put("code",400);
            resultMap.put("messgae","The link has been expired, please register again.");
            return resultMap;
        }
        //根据确认码查询用户，并且将状态值改为1
        int result = userMapper.updateUserByConfirmCode(confirmCode);
        if (result > 0) {
            resultMap.put("code",200);
            resultMap.put("messgae","Activate successfully.");
        }else {
            resultMap.put("code",400);
            resultMap.put("messgae","Activate failed.");
        }
        return resultMap;
    }
}
