package com.cs2802.tradewinbackend.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@WebFilter(urlPatterns = "/view/*",filterName = "LoginFilter")
public class LoginFilter implements Filter {

//    @Resource
//    private StringRedisTemplate redisTemplate;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //校验用户登录状态
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //Filter过滤器跨域处理
        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", origin);
        // 设置允许的请求方法
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        // 设置允许的请求头
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization, token");
        // 设置响应的Content-Type头部信息
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setHeader("Access-Control-Max-Age","3600");
        response.setHeader("Access-Control-Allow-Credentials","true");

        //获取token令牌
        String token = request.getHeader("token");
        System.out.println(token);

        token = token == null? "": token;
        //查询redis中的剩余时间,并且看下这个时间是否大于0
//        Long expire = redisTemplate.getExpire(token);
        Long expire = redisTemplate.getExpire("token", TimeUnit.MINUTES);
        System.out.println(expire);
        if (expire > 0) {
            //重置时间
            redisTemplate.expire("token",5, TimeUnit.MINUTES);
            //放行
            filterChain.doFilter(servletRequest,servletResponse);

        }else {
            Map<String, Object> map = new HashMap<>();
            map.put("400","Not sign in");
            String string = JSONObject.toJSONString(map);
            response.setContentType("json/text;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write(string);
        }
    }




}
