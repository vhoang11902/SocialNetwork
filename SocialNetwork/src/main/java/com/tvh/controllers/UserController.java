/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tvh.pojo.Survey;
import com.tvh.pojo.User;
import com.tvh.service.ConnectionService;
import com.tvh.service.PostService;
import com.tvh.service.UserService;
import com.tvh.service.impl.UserServiceImpl;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
@Controller
public class UserController {
        @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private PostService postService;
    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private UserService userService;
    @Autowired
    private Environment env;
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    
    @RequestMapping("/admin/listUsers")
    public String listUsers(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("users", this.userService.getUsers(params));
        
        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        long count = this.userService.countUsers();
        model.addAttribute("counter", Math.ceil(count * 1.0 / pageSize));
        return "listUsers";
    }
    
        @GetMapping("/admin/handleUser")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "handleUser"; // Tên của trang JSP để hiển thị form
    }
}
