package com.cs2802.tradewinbackend.service;

import com.cs2802.tradewinbackend.controller.BlogController;
import com.cs2802.tradewinbackend.mapper.BlogMapper;
import com.cs2802.tradewinbackend.pojo.AntBlog;
import com.cs2802.tradewinbackend.pojo.Blog;
import com.cs2802.tradewinbackend.pojo.TempBlog;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogService {
    @Resource
    private BlogMapper blogMapper;

    public Blog getBlog(Integer id) {
        return blogMapper.selectBlogByBlogId(id);
    }


    public Map<String, Object> insertBlog(Blog blog){
        //初始化blog
        blog.setBlogTitle(blog.getBlogTitle());
        blog.setBlogContent(blog.getBlogContent());
        blog.setUserId(blog.getUserId());
        blog.setBlogCreateTime(LocalDateTime.now());
        //插入blog
        int result = blogMapper.insertBlog(blog);
        Map<String, Object> resultMap = new HashMap<>();
        if (result > 0) {
            resultMap.put("code",200);
            resultMap.put("message","Insert blog successfully.");
        } else {
            resultMap.put("code",400);
            resultMap.put("message","Insert blog failed.");
        }
        return resultMap;
    }

    public Map<String, Object> updateBlog(Blog blog){
        Blog b = blogMapper.selectBlogByBlogId(blog.getBlogId());
        System.out.println(b);
        System.out.println(blog);
        Map<String,Object> resultMap = new HashMap<>();
        if(b == null) {
            resultMap.put("code",400);
            resultMap.put("message","The blog is not found.");
        }else {
            blog.setBlogTitle(blog.getBlogTitle());
            blog.setBlogContent(blog.getBlogContent());
            blog.setBlogCreateTime(LocalDateTime.now());
            int i = blogMapper.updateBlog(blog);
            resultMap.put("code",200);
            resultMap.put("message","Update sucessfully.");
        }
        return resultMap;
    }

    public Map<String, Object> deleteBlog(Integer blogId) {
        Blog b = blogMapper.selectBlogByBlogId(blogId);
        Map<String,Object> resultMap = new HashMap<>();
        if (b == null) {
            resultMap.put("code",400);
            resultMap.put("message","The blog is not found.");
        } else {
            blogMapper.delteBlog(blogId);
            resultMap.put("code",200);
            resultMap.put("message","delete sucessfully.");
        }
        return resultMap;
    }

    public List<AntBlog> findAllBlogs() {
        List<AntBlog> antBlogList = new ArrayList<>();
        for (TempBlog tempBlogs:
             blogMapper.selectAllBlog()) {
                antBlogList.add(concatobject(tempBlogs));
        }

        return antBlogList;

    }

    public AntBlog concatobject(TempBlog tempBlog){
        AntBlog antBlog = new AntBlog();
        antBlog.setTitle(tempBlog.getBlogTitle());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = tempBlog.getBlogCreateTime().format(formatter);

        antBlog.setDescription("Author: " + tempBlog.getUserName() + "  "+ formattedDateTime);
        antBlog.setContent(tempBlog.getBlogContent());
        return antBlog;
    }
}
