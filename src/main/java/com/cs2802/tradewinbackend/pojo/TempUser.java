package com.cs2802.tradewinbackend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TempUser implements Serializable {
    private String username;
    private String email;
    private String gender;
    private String phoneNumber;

}