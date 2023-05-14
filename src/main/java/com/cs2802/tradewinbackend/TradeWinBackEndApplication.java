package com.cs2802.tradewinbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan("com.cs2802.tradewinbackend.filter")
@MapperScan("com.cs2802.tradewinbackend.mapper")
@SpringBootApplication
public class TradeWinBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradeWinBackEndApplication.class, args);
    }

}
