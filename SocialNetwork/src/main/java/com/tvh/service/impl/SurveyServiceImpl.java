/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.service.impl;

import com.cloudinary.utils.ObjectUtils;
import com.tvh.pojo.Image;
import com.tvh.pojo.Post;
import com.tvh.pojo.Survey;
import com.tvh.pojo.SurveyChoice;
import com.tvh.pojo.User;
import com.tvh.repository.ReactionRepository;
import com.tvh.repository.SurveyRepository;
import com.tvh.repository.UserReppository;
import com.tvh.service.SurveyService;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author hoangtrinh
 */
@Service
public class SurveyServiceImpl implements SurveyService{
    
    @Autowired
    private SurveyRepository surveyRepo;
    
    @Autowired
    private LocalSessionFactoryBean factory;
//    @Override
//    public boolean addOrUpdateSurvey(Survey survey) {
//        return this.surveyRepo.addOrUpdateSurvey(survey);
//    }
        @Autowired
    private UserReppository userRepo;
    
        
    @Override
    public List<Post> getSurveys(Map<String, String> params) {
        return this.surveyRepo.getSurveys(params);
    }

    @Override
    public Long countSurveys() {
        return this.surveyRepo.countSurveys();
    }
    
    @Override
    public Survey getSurveyById(int id) {
        return this.surveyRepo.getSurveyById(id);
    }
    
//    @Override
//    @Transactional
//    public Survey addOrUpdateSurvey(Map<String, String> params, MultipartFile[] images, List<String> choices) {
//
//        
//
//        Survey survey = new Survey();
//
//        if(params.get("id")!=null){
//            int id = Integer.parseInt(params.get("id"));
//            survey.setId(id);
//        }
//        survey.setContent(params.get("content"));
//        survey.setActive(true);
//        survey.setCreatedDate(new Date());
//        Session s = this.factory.getObject().getCurrentSession();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = this.userRepo.getUserByUsername(authentication.getName());
//        this.surveyRepo.addOrUpdateSurvey(survey);
//        if(choices != null) {
//            for (String choiceContent : choices) {
//                SurveyChoice choice = new SurveyChoice();
//                choice.setContent(choiceContent);
//                choice.setUser(user);
//                choice.setSurvey(survey);
//                s.save(choice);
//            }
//        }
//        return survey;
//    }
    
    @Override
    public boolean addOrUpdateSurvey(Survey survey) {
        return this.surveyRepo.addOrUpdateSurvey(survey);
        
    }
}
