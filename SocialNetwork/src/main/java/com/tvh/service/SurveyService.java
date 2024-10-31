/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.service;

import com.tvh.pojo.Post;
import com.tvh.pojo.Survey;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author hoangtrinh
 */
public interface SurveyService {
//    boolean addOrUpdateSurvey(Survey survey);

    List<Post> getSurveys(Map<String, String> params);
    Survey getSurveyById(int id);
    Long countSurveys();
//    Survey addOrUpdateSurvey(Map<String, String> params, MultipartFile[] images, List<String> choices);
    boolean addOrUpdateSurvey(Survey survey);
}
