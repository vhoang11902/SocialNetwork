/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.repository.impl;

import com.tvh.pojo.Post;
import com.tvh.pojo.User;
import com.tvh.repository.UserReppository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class UserReppositoryImpl implements UserReppository {

    @Autowired
    private Environment env;
    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private BCryptPasswordEncoder passEncoder;

    @Override
    public List<User> getUsers(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<User> u = b.createQuery(User.class);

        Root root = u.from(User.class);

        u.select(root);

        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {
            String username = params.get("username");
            if (username != null && !username.isEmpty()) {
                predicates.add(b.equal(root.get("username"), username));
            }
        }
        u.where(predicates.toArray(Predicate[]::new))
                .orderBy(b.asc(root.get("id")));
        javax.persistence.Query query = session.createQuery(u);

        if (params != null) {
            String p = params.get("page");
            if (p != null && !p.isEmpty()) {
                int page = Integer.parseInt(p);
                int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));

                query.setMaxResults(pageSize);
                query.setFirstResult((page - 1) * pageSize);
            }
        }

        return query.getResultList();
    }

    @Override
    public User getUserByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM User WHERE username=:un");
        q.setParameter("un", username);

        return (User) q.getSingleResult();
    }

    @Override
    public User getUserById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM User WHERE id=:id");
        q.setParameter("id", id);

        return (User) q.getSingleResult();
    }

    @Override
    public boolean authUser(String username, String password) {
        User u = this.getUserByUsername(username);
        return u != null && u.getActive() && this.passEncoder.matches(password, u.getPassword());
    }

    @Override
    public Long countUsers() {
        Session s = this.factory.getObject().getCurrentSession();
        javax.persistence.Query q = s.createQuery("SELECT Count(*) FROM User");

        return Long.parseLong(q.getSingleResult().toString());
    }

    @Override
    public User addUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        s.save(u);
        return u;
    }

    @Override
    public boolean activeUser(Integer id) {
        Session s = this.factory.getObject().getCurrentSession();
        User u = this.getUserById(id);
        u.setActive(Boolean.TRUE);
        s.update(u);
        return true;
    }
}
