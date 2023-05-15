package com.cs2802.tradewinbackend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private Integer id;
    private String username;
    private String email;
    private String password;
    private String salt;
    private String confirmCode;
    private LocalDateTime activationTime;
    private Byte isValid;
    private String gender;
    private String phoneNumber;

}