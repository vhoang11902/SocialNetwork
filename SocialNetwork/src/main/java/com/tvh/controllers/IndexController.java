/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.controllers;

//import com.tvh.service.CategoryService;
//import com.tvh.service.ProductService;
import com.tvh.pojo.Post;
import com.tvh.service.ConnectionService;
import com.tvh.service.PostService;
import com.tvh.service.StatsService;
import com.tvh.service.UserService;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author admin
 */
@Controller
@ControllerAdvice
@PropertySource("classpath:configs.properties")
@RequestMapping("/admin")
public class IndexController {
//    @Autowired
//    private ProductService productService;

    @Autowired
    private PostService postService;
    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private UserService userService;
    @Autowired
    private StatsService statsService;
    @Autowired
    private Environment env;
    
        
    @RequestMapping("/dashboard")
    public String dashboard(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("userStats", this.statsService.userStats(params));
        model.addAttribute("postStats", this.statsService.postStats(params));
        
        String type = params.get("type");
        if(type != null && !type.isEmpty())
            model.addAttribute("type", type.substring(0, 1).toUpperCase() + type.substring(1));

        return "dashboard";
    }
    
    

}
