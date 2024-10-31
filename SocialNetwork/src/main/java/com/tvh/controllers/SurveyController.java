/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.controllers;

import com.tvh.pojo.Survey;
import com.tvh.pojo.SurveyChoice;
import com.tvh.service.ConnectionService;
import com.tvh.service.PostService;
import com.tvh.service.SurveyService;
import com.tvh.service.UserService;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author hoangtrinh
 */
@Controller
@ControllerAdvice
@PropertySource("classpath:configs.properties")
@RequestMapping("/admin")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;
    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private UserService userService;
    @Autowired
    private Environment env;

    @GetMapping("/listSurveys")
    public String showListSurveys(Model model, @RequestParam Map<String, String> params) {     
        model.addAttribute("surveys", this.surveyService.getSurveys(params));

        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        long count = this.surveyService.countSurveys();
        model.addAttribute("counter", Math.ceil(count * 1.0 / pageSize));
        return "listSurveys"; 
    }
    
//    @RequestMapping("/listPosts")
//    public String index(Model model, @RequestParam Map<String, String> params) {
//        model.addAttribute("posts", this.postService.getPosts(params));
//
//        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
//        long count = this.postService.countPosts();
//        model.addAttribute("counter", Math.ceil(count * 1.0 / pageSize));
//
//        return "listPosts";
//    }
    
    
    @GetMapping("/handleSurvey")
    public String showAddSurveyForm(Model model) {
        model.addAttribute("survey", new Survey());
        return "handleSurvey"; // Tên của trang JSP để hiển thị form
    }
//    
    @GetMapping("/handleSurvey/{id}")
    public String update(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("survey", this.surveyService.getSurveyById(id));
        return "handleSurvey";
    }

    @PostMapping("/surveys")
    public String addSurvey(@ModelAttribute("survey") @Valid Survey survey,
            BindingResult rs, Model model, @RequestParam("surveyChoices[]") List<String> surveyChoices) {
        System.out.println(surveyChoices);
        if (!rs.hasErrors()) {
            if (this.surveyService.addOrUpdateSurvey(survey)) {
                return "redirect:/admin/listSurveys";
            }
        }
        model.addAttribute("survey", survey); // Thêm lại đối tượng survey vào model để hiển thị lại form với các lỗi
        return "addSurvey";
    }

//
//    @PostMapping(path = "/surveys",
//            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
//            produces = {MediaType.APPLICATION_JSON_VALUE})
//    @ResponseStatus(HttpStatus.CREATED)
//    public String addSurvey(@RequestParam Map<String, String> params,
//                            @RequestParam("choices") List<String> choices,
//                            @RequestPart(required = false) MultipartFile[] images) {
//        Survey survey = this.surveyService.addOrUpdateSurvey(params, images, choices);
//        return "listPosts";
//    }

}
