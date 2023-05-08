package com.cs2802.tradewinbackend.mapper;

import com.cs2802.tradewinbackend.pojo.Blog;
import com.cs2802.tradewinbackend.pojo.BlogUserName;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

public interface BlogMapper {

    //新增blog
    @Insert("INSERT INTO blog (blog_title, blog_content, blog_create_time, user_id)" +
            "VALUES (#{blogTitle},#{blogContent},#{blogCreateTime},#{userId});")
    int insertBlog(Blog blog);

    //删除blog
    @Delete("Delete from blog where blog_id = #{blogId};")
    int delteBlog(@Param("blogId") Integer blogId);

    //更新blog
    @Update("Update blog set blog_title = #{blogTitle}, blog_content = #{blogContent}, " +
            "blog_create_time = #{blogCreateTime} where blog_id = #{blogId}")
    int updateBlog(Blog blog);

    //查询blog by id
    @Select("select blog_title, blog_content, blog_create_time from blog where blog_id = #{blogId}")
    Blog selectBlogByBlogId(@Param("blogId") Integer blogId);



    //查询blog by title
    @Select("SELECT b.blog_title, b.blog_content, b.blog_create_time, u.user_name " +
            "FROM blog AS b " +
            "JOIN user AS u ON b.user_id = u.user_id " +
            "WHERE b.blog_title LIKE CONCAT('%', #{blogTitle}, '%') AND b.user_id = #{userId}")
    List<Blog> selectBlogsByTitleAndUserId(@Param("blogTitle") String blogTitle, @Param("userId") Integer userId);


    //返回所有blog
    @Select("SELECT b.blog_id, b.blog_title, b.blog_content, b.blog_create_time, u.user_name " +
            "FROM blog AS b " +
            "JOIN users AS u ON b.user_id = u.user_id " +
            "ORDER BY b.blog_create_time DESC")
    @Results(id = "blogVoResultMap", value = {
            @Result(property = "blogId", column = "blog_id", javaType = Integer.class),
            @Result(property = "blogTitle", column = "blog_title", javaType = String.class),
            @Result(property = "blogContent", column = "blog_content", javaType = String.class),
            @Result(property = "blogCreateTime", column = "blog_create_time", javaType = LocalDateTime.class),
            @Result(property = "userName", column = "user_name", javaType = String.class)
    })
    List<BlogUserName> selectAllBlog();




}
