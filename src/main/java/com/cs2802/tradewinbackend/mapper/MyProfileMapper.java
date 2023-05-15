package com.cs2802.tradewinbackend.mapper;

import com.cs2802.tradewinbackend.pojo.TempUser;
import com.cs2802.tradewinbackend.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MyProfileMapper {

    @Select("SELECT username, email, gender, phone_number" +
            " FROM users" +
            "WHERE email = #{email} and is_valid = 1")
    List<TempUser> findMyProfileByEmail(@Param("email") String email);

}
