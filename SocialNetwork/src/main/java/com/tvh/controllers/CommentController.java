/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.controllers;

import com.tvh.service.ConnectionService;
import com.tvh.service.PostService;
import com.tvh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author hoangtrinh
 */
@Controller
@ControllerAdvice
@PropertySource("classpath:configs.properties")
@RequestMapping("/admin")
public class CommentController {
    @Autowired
    private PostService postService;
    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private UserService userService;
    @Autowired
    private Environment env;
    
    @RequestMapping("/listPosts/{id}/comments")
    public String comments(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("p", this.postService.getPostById(id));
        return "listComments";
    }
}
