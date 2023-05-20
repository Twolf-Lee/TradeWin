package com.cs2802.tradewinbackend.controller;

import com.cs2802.tradewinbackend.pojo.AntBlog;
import com.cs2802.tradewinbackend.pojo.AntEditBlog;
import com.cs2802.tradewinbackend.pojo.Blog;
import com.cs2802.tradewinbackend.service.BlogService;
import com.cs2802.tradewinbackend.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

//    @PostMapping("insertBlog")
//    public Map<String,Object> insertBlog(Blog blog){
//        return blogService.insertBlog(blog);
//    }

    @PostMapping("view/insertBlog")
    public Map<String,Object> insertBlog(HttpServletRequest request,
                                         @RequestBody Map<String,String> map){

        String input=map.get("input");
        String textArea=map.get("textArea");
        String token = request.getHeader("token");
        if (token != null) {
            String email = JwtUtil.getEmailByToken(request);
            Integer userId = blogService.findUserIdByEmail(email);
//            Blog blog = new Blog();
//            blog.setUserId(userId);
            return blogService.insertBlog(userId, input, textArea);
        } else {
            System.out.println("something wrong when insert");
            return null;
        }
    }

    //通过博客title获取博客的信息
    @PostMapping("view/getBlogInformation")
    public AntEditBlog getBlogInformation(HttpServletRequest request,
                                          @RequestBody Map<String,String> map){
        String title = map.get("option");
        String token = request.getHeader("token");
        if (token != null) {
            String email = JwtUtil.getEmailByToken(request);
            Integer userId = blogService.findUserIdByEmail(email);
            return blogService.getBlogInformation(userId,title);
        } else {
            System.out.println("something wrong when get information of Blog");
            return null;
        }
    }

    /*需要讨论下，通过获取什么键值来进行更新
    用Title，token
    * */
    @PostMapping("view/updateBlog")
    public Map<String,Object> updateBlog(HttpServletRequest request,
                                         @RequestBody Map<String,String> map){
        String title =map.get("input");
        String content =map.get("textArea");
        String token = request.getHeader("token");
        if (token != null) {
            String email = JwtUtil.getEmailByToken(request);
            Integer userId = blogService.findUserIdByEmail(email);
            return blogService.updateBlog(title,content,userId);
        } else {
            System.out.println("something wrong when insert");
            return null;
        }
    }


//    @PostMapping("deleteBlog")
//    public Map<String,Object> deleteBlog(Integer blogId){
//        return blogService.deleteBlog(blogId);
//    }

    /*需要讨论下前端传什么参数，用来识别
    * Title*/
    @PostMapping("view/deleteBlog")
    public Map<String,Object> deleteBlog(HttpServletRequest request,
                                         @RequestBody Map<String,String> map){
        String title =map.get("title");
        String token = request.getHeader("token");
        if (token != null) {
            String email = JwtUtil.getEmailByToken(request);
            Integer userId = blogService.findUserIdByEmail(email);
            return blogService.deleteBlog(title,userId);
        } else {
            System.out.println("something wrong when delete");
            return null;
        }
    }


    @GetMapping("view/findAllBlogs")
    public List<AntBlog> findAllBlogs(HttpServletRequest request) {
        String token = request.getHeader("token");

        if (token != null) {
           List<AntBlog> blogs = blogService.findAllBlogs();
           return blogs;
        } else {
            System.out.println("something wrong when find all blogs");
            return null;
        }

    }

    @GetMapping("view/findUserAllBlogs")
    public List<AntBlog> findUserAllBlogs(HttpServletRequest request) {
        String token = request.getHeader("token");

        if (token != null) {
            String email = JwtUtil.getEmailByToken(request);
            Integer id = blogService.findUserIdByEmail(email);
            List<AntBlog> blogs = blogService.findUserAllBlogs(id);
            return blogs;
        } else {
            System.out.println("something wrong when find user's blogs");
            return null;
        }

    }

}
