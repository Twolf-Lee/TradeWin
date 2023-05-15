package com.cs2802.tradewinbackend.mapper;

import com.cs2802.tradewinbackend.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {

    /*新增用户*/
    @Insert("Insert into users (username, email, password, salt, confirm_code, activation_time, is_valid, gender, phone_number) " +
            "Values(#{username}, #{email}, #{password}, #{salt}, #{confirmCode}, #{activationTime}, #{isValid}, #{gender}, #{phoneNumber})")
    int insertUser(User user);

    /*根据确认码查询用户*/
    @Select("select email, activation_time from users where confirm_code = #{confirmCode} and is_valid = 0")
    User selectUserByCinfirmCode(@Param("confirmCode") String confirmCode);

    /*根据确认码查询用户并修改状态值为1*/
    @Update("update users set is_valid = 1 where confirm_code = #{confirmCode}")
    int updateUserByConfirmCode(@Param("confirmCode") String confirmCode);

    /*根据邮箱查询用户*/
    @Select("select email, password, salt from users where email = #{email} and is_valid = 1 ")
    List<User> selectUserByEmail(@Param("email") String email);


}