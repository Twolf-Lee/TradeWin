package com.cs2802.tradewinbackend.service;

import com.cs2802.tradewinbackend.controller.BlogController;
import com.cs2802.tradewinbackend.mapper.BlogMapper;
import com.cs2802.tradewinbackend.pojo.AntBlog;
import com.cs2802.tradewinbackend.pojo.AntEditBlog;
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


    public Map<String, Object> insertBlog(Integer id, String input, String textArea){
        //初始化blog
        Blog blog = new Blog();
        blog.setBlogTitle(input);
        blog.setBlogContent(textArea);
        blog.setUserId(id);
        blog.setBlogCreateTime(LocalDateTime.now());
        //插入blog
        int result = blogMapper.insertBlog(blog);
        Map<String, Object> resultMap = new HashMap<>();
        if (result > 0) {
            resultMap.put("status",205);
        } else {
            resultMap.put("status",405);
        }
        return resultMap;
    }

    public AntEditBlog getBlogInformation(Integer id, String title){
        Blog blogByTitle = blogMapper.selectBlogByTitle(title, id);
        AntEditBlog blog = new AntEditBlog();
        blog.setInput(blogByTitle.getBlogTitle());
        blog.setTextArea(blogByTitle.getBlogContent());
        return blog;
    }

    public Map<String, Object> updateBlog(String blogTitle, String textArea, Integer id) {
        Blog blog = new Blog();
        blog.setBlogTitle(blogTitle);
        blog.setBlogContent(textArea);
        blog.setUserId(id);
        Map<String, Object> resultMap = new HashMap<>();
        int result = blogMapper.updateBlog(blog);
        if (result > 0) {
            resultMap.put("status",206);
        } else {
            resultMap.put("status",406);
        }
        return resultMap;

    }

    public Map<String, Object> deleteBlog(String title, Integer id) {
        Blog b = blogMapper.selectBlogByTitle(title, id);
        Map<String,Object> resultMap = new HashMap<>();
        if (b == null) {
            resultMap.put("status",407);
        } else {
            blogMapper.delteBlog(title,id);
            resultMap.put("status",207);
        }
        return resultMap;
    }

    public List<AntBlog> findAllBlogs() {
        List<AntBlog> antBlogList = new ArrayList<>();
        List<TempBlog> blogList = blogMapper.selectAllBlog();

        for (int i = 0; i < blogList.size(); i++) {
            TempBlog tempBlogs = blogList.get(i);
            antBlogList.add(concatobject(tempBlogs));
        }

        return antBlogList;

    }

    public List<AntBlog> findUserAllBlogs(Integer id) {
        List<AntBlog> antBlogList = new ArrayList<>();
        List<TempBlog> blogList = blogMapper.selectUserAllBlog(id);

        if (blogList != null){
            for (int i = 0; i < blogList.size(); i++) {
                TempBlog tempBlogs = blogList.get(i);
                antBlogList.add(concatobject(tempBlogs));
            }

            return antBlogList;
        } else{
            System.out.println("nothing");
            return null;
        }


    }
    public AntBlog concatobject(TempBlog tempBlog){
        AntBlog antBlog = new AntBlog();
        antBlog.setTitle(tempBlog.getBlogTitle());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = tempBlog.getBlogCreateTime().format(formatter);

        antBlog.setDescription("Author: " + tempBlog.getUsername() + "  "+ formattedDateTime);
        antBlog.setContent(tempBlog.getBlogContent());
        return antBlog;
    }

    //根据邮箱找到用户ID
    public Integer findUserIdByEmail(String email){
        return blogMapper.selectUserIdByEmail(email);
    }
}
