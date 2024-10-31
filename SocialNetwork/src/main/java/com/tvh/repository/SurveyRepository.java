/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.repository;

import com.tvh.pojo.Post;
import com.tvh.pojo.Survey;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hoangtrinh
 */
public interface SurveyRepository {
    boolean addOrUpdateSurvey(Survey survey);
    List<Post> getSurveys(Map<String, String> params);
    Long countSurveys();
    Survey getSurveyById(int id);
}
