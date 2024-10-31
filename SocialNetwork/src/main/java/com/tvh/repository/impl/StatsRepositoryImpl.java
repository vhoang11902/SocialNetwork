package com.tvh.repository.impl;

import com.tvh.pojo.Post;
import com.tvh.pojo.User;
import com.tvh.repository.StatsRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author hoangtrinh
 */
@Repository
@Transactional
public class StatsRepositoryImpl implements StatsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Resource(name = "simpleDateFormat")
    private SimpleDateFormat simpleDateFormat;

    public List<Object[]> userStats(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

        Root<User> rUser = q.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        String type = params.get("type");
        if (type != null && !type.isEmpty()) {
            if (type.equals("year")) {
                q.multiselect(b.function("YEAR", Integer.class, rUser.get("createdDate")), b.count(rUser));
                q.groupBy(b.function("YEAR", Integer.class, rUser.get("createdDate")));
            } else if (type.equals("month")) {
                String year = params.get("yearType");
                if (year != null && !year.isEmpty()) {
                    predicates.add(b.equal(b.function("YEAR", Integer.class, rUser.get("createdDate")), Integer.parseInt(year)));
                }
                q.multiselect(b.function("MONTH", Integer.class, rUser.get("createdDate")), b.count(rUser));
                q.groupBy(b.function("MONTH", Integer.class, rUser.get("createdDate")));
            }
        } else {
            predicates.add(b.equal(b.function("YEAR", Integer.class, rUser.get("createdDate")), 2024));
            q.multiselect(b.function("MONTH", Integer.class, rUser.get("createdDate")), b.count(rUser));
            q.groupBy(b.function("MONTH", Integer.class, rUser.get("createdDate")));
        }

        q.where(predicates.toArray(Predicate[]::new));

        Query query = s.createQuery(q);
        return query.getResultList();
    }

    public List<Object[]> postStats(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

        Root<Post> post = q.from(Post.class);

        List<Predicate> predicates = new ArrayList<>();

        String type = params.get("type");
        if (type != null && !type.isEmpty()) {
            if (type.equals("year")) {
                q.multiselect(b.function("YEAR", Integer.class, post.get("createdDate")), b.count(post));
                q.groupBy(b.function("YEAR", Integer.class, post.get("createdDate")));
            } else if (type.equals("month")) {
                String year = params.get("yearType");
                if (year != null && !year.isEmpty()) {
                    predicates.add(b.equal(b.function("YEAR", Integer.class, post.get("createdDate")), Integer.parseInt(year)));
                }
                q.multiselect(b.function("MONTH", Integer.class, post.get("createdDate")), b.count(post));
                q.groupBy(b.function("MONTH", Integer.class, post.get("createdDate")));
            }
        } else {
            predicates.add(b.equal(b.function("YEAR", Integer.class, post.get("createdDate")), 2024));
            q.multiselect(b.function("MONTH", Integer.class, post.get("createdDate")), b.count(post));
            q.groupBy(b.function("MONTH", Integer.class, post.get("createdDate")));
        }

        q.where(predicates.toArray(Predicate[]::new));

        Query query = s.createQuery(q);
        return query.getResultList();
    }
}
