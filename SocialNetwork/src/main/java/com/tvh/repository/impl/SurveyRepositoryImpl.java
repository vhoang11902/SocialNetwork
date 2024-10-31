/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.repository.impl;

import com.tvh.pojo.Post;
import com.tvh.pojo.Survey;
import com.tvh.pojo.SurveyChoice;
import com.tvh.pojo.User;
import com.tvh.repository.PostRepository;
import com.tvh.repository.SurveyRepository;
import com.tvh.repository.UserReppository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hoangtrinh
 */
@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class SurveyRepositoryImpl implements SurveyRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private UserReppository userRepo;
    
        @Autowired
    private Environment env;

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getSurveys(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Survey> s = b.createQuery(Survey.class);

        Root root = s.from(Survey.class);

        s.select(root);

        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {
            System.out.println(params);

            String userId = params.get("userId");
            if (userId != null && !userId.isEmpty()) {
                Join<User, Integer> userIdJoin = root.join("userId"); // Join vào thuộc tính userId
                predicates.add(b.equal(userIdJoin.get("id"), Integer.parseInt(userId)));
            }
        }

        s.where(predicates.toArray(Predicate[]::new))
                .orderBy(b.desc(root.get("createdDate")));
        javax.persistence.Query query = session.createQuery(s);

        String p = params.get("pageNumber");
        if (p != null && !p.isEmpty()) {
            int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
            int startIndex = (Integer.parseInt(p) - 1) * pageSize;
            query.setFirstResult(startIndex);
            query.setMaxResults(pageSize);
        }
//
//        List<Survey> surveys = query.getResultList();
//
//        for (Survey survey : surveys) {
//            Hibernate.initialize(survey.getSurveyChoices());
//        }

        return query.getResultList();
    }
    
    
    @Override
    public Long countSurveys() {
        Session s = this.factory.getObject().getCurrentSession();
        javax.persistence.Query q = s.createQuery("SELECT Count(*) FROM Survey");

        return Long.parseLong(q.getSingleResult().toString());
    }

    
    @Override
    public boolean addOrUpdateSurvey(Survey survey) {
        System.out.println(survey.getSurveyChoices());
        Session s = this.factory.getObject().getCurrentSession();
        
        try {
            if (survey.getId() == null) {
                s.save(survey);
            } else {
                s.update(survey);
            }
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
        
        
        // Set createdDate if it's a new survey
//        
//        if (survey.getId() == null) {
//            survey.setCreatedDate(new Date());
//            s.save(survey);
//        } else {
//            s.update(survey);
//        }

        // Handle SurveyChoice entities
//        if (survey.getSurveyChoices() != null) {
//            for (SurveyChoice choice : survey.getSurveyChoices()) {
//                choice.setSurvey(survey); // Set the relationship
//                if (choice.getId() == null) {
//                    choice.setCreatedDate(new Date());
//                    s.save(choice);
//                } else {
//                    s.update(choice);
//                }
//            }
//        }


//        return survey;
    }
    
    
//    @Override
//    public boolean addOrUpdateRoute(Route r) {
//        Session s = this.factory.getObject().getCurrentSession();
//        
//
//    }
//    
    @Override
    public Survey getSurveyById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Survey WHERE id=:id");
        q.setParameter("id", id);
        return (Survey) q.getSingleResult();
        
    }
}
