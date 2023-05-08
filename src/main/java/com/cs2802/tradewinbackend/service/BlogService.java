package com.cs2802.tradewinbackend.service;

import com.cs2802.tradewinbackend.controller.BlogController;
import com.cs2802.tradewinbackend.mapper.BlogMapper;
import com.cs2802.tradewinbackend.pojo.Blog;
import com.cs2802.tradewinbackend.pojo.BlogUserName;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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

    public List<BlogUserName> searchBlog(int startpage) {
        PageHelper.startPage(startpage, 5);
//        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
  /*      long total = pageInfo.getTotal();
        int pageNum = pageInfo.getPageNum();
        int pageSize = pageInfo.getPageSize();
        int pages = pageInfo.getPages();
        boolean hasNextPage = pageInfo.isHasNextPage();
        boolean hasPreviousPage = pageInfo.isHasPreviousPage();*/

        return blogMapper.selectAllBlog();
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

    public List<BlogUserName> searchBlogWithoutStartPage() {
//        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
  /*      long total = pageInfo.getTotal();
        int pageNum = pageInfo.getPageNum();
        int pageSize = pageInfo.getPageSize();
        int pages = pageInfo.getPages();
        boolean hasNextPage = pageInfo.isHasNextPage();
        boolean hasPreviousPage = pageInfo.isHasPreviousPage();*/
        return blogMapper.selectAllBlog();
    }
}
