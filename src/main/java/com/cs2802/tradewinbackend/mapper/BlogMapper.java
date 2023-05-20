package com.cs2802.tradewinbackend.mapper;

import com.cs2802.tradewinbackend.pojo.AntBlog;
import com.cs2802.tradewinbackend.pojo.Blog;
import com.cs2802.tradewinbackend.pojo.TempBlog;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

public interface BlogMapper {

    //新增blog
    @Insert("INSERT INTO blog (blog_title, blog_content, blog_create_time, user_id)" +
            "VALUES (#{blogTitle},#{blogContent},#{blogCreateTime},#{userId});")
    int insertBlog(Blog blog);

    //删除blog
    @Delete("Delete from blog where blog_title = #{blog_title} and user_id = #{id};")
    int delteBlog(@Param("blog_title") String blogTitle, @Param("id") Integer id);

    //更新blog
    @Update("Update blog set blog_content = #{blogContent}" +
            " where blog_title = #{blogTitle} and user_id = #{userId}")
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

    //通过Title 查询 blog内容
    @Select("Select blog_Title, blog_content" +
            " From blog" +
            " where blog_title = #{blogTitle} and user_id = #{id}")
    Blog selectBlogByTitle(@Param("blogTitle") String blogTitle, @Param("id") Integer id);

    //返回所有blog
//  @Select("SELECT b.blog_id, b.blog_title, b.blog_content, b.blog_create_time, username " +
//            "FROM blog AS b " +
//            "JOIN users AS u ON b.user_id = #{id} " +
//            "ORDER BY b.blog_create_time DESC")
//    @Results(id = "blogVoResultMap", value = {
//            @Result(property = "blogId", column = "blog_id", javaType = Integer.class),
//            @Result(property = "blogTitle", column = "blog_title", javaType = String.class),
//            @Result(property = "blogContent", column = "blog_content", javaType = String.class),
//            @Result(property = "blogCreateTime", column = "blog_create_time", javaType = LocalDateTime.class),
//            @Result(property = "username", column = "username", javaType = String.class)
//    })
//    List<TempBlog> selectAllBlog(@Param("id") Integer id);

    @Select("SELECT b.blog_id, b.blog_title, b.blog_content, b.blog_create_time, username " +
            "FROM blog AS b " +
            "JOIN users AS u ON b.user_id = u.id " +
            "ORDER BY b.blog_create_time DESC")
    @Results(id = "blogVoResultMap", value = {
            @Result(property = "blogId", column = "blog_id", javaType = Integer.class),
            @Result(property = "blogTitle", column = "blog_title", javaType = String.class),
            @Result(property = "blogContent", column = "blog_content", javaType = String.class),
            @Result(property = "blogCreateTime", column = "blog_create_time", javaType = LocalDateTime.class),
            @Result(property = "username", column = "username", javaType = String.class)
    })
    List<TempBlog> selectAllBlog();

  //通过邮箱查询用户ID
  @Select("Select id from users where email = #{email} and is_valid = 1")
    Integer selectUserIdByEmail(@Param("email") String email);

    //查询某个用户的所有blog
    @Select("SELECT blog_id,blog_title,blog_content,blog_create_time,username" +
            " FROM blog" +
            " JOIN users ON user_id = id" +
            " WHERE user_id = #{id}" +
            " ORDER BY blog_create_time DESC;")
    @Results(id = "blogResultMap", value = {
            @Result(property = "blogId", column = "blog_id", javaType = Integer.class),
            @Result(property = "blogTitle", column = "blog_title", javaType = String.class),
            @Result(property = "blogContent", column = "blog_content", javaType = String.class),
            @Result(property = "blogCreateTime", column = "blog_create_time", javaType = LocalDateTime.class),
            @Result(property = "username", column = "username", javaType = String.class)
    })
    List<TempBlog> selectUserAllBlog(@Param("id") Integer id);

}
