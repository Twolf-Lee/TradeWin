package com.cs2802.tradewinbackend.controller;

import com.cs2802.tradewinbackend.pojo.AntBlog;
import com.cs2802.tradewinbackend.pojo.Blog;
import com.cs2802.tradewinbackend.service.BlogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("blog")
@CrossOrigin
public class BlogController {

    @Resource
    private BlogService blogService;

/*    @GetMapping("findBlogs")
    public List<AntBlog> findAllBlogs(@RequestParam(required = true) int startpage, ModelMap model) {
        List<AntBlog> blogs = blogService.searchBlog(startpage);
        PageInfo<AntBlog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("blogs", blogs);
        model.addAttribute("pageInfo", pageInfo);
        return blogs;
    }*/

    @PostMapping("insertBlog")
    public Map<String,Object> insertBlog(Blog blog){
        return blogService.insertBlog(blog);
    }

    @PostMapping("updateBlog")
    public Map<String,Object> updateBlog(Blog blog){
        return blogService.updateBlog(blog);
    }

    @PostMapping("deleteBlog")
    public Map<String,Object> deleteBlog(Integer blogId){
        return blogService.deleteBlog(blogId);
    }

    @GetMapping("findAllBlogs")
    public List<AntBlog> findAllBlogsWithoutStartPage() {
        List<AntBlog> blogs = blogService.findAllBlogs();
        return blogs;
    }


}
