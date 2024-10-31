/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.repository.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tvh.pojo.Comment;
import com.tvh.pojo.Image;
import com.tvh.pojo.Post;
import com.tvh.pojo.Reaction;
import com.tvh.pojo.User;
import com.tvh.repository.PostRepository;
import com.tvh.repository.UserReppository;
import com.tvh.service.impl.UserServiceImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author hoangtrinh
 */
@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class PostRepositoryImpl implements PostRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private UserReppository userRepo;

    @Autowired
    private Cloudinary cloudinary;
    
    @Autowired
    private Environment env;

    @Override
    public List<Post> getPosts(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Post> q = b.createQuery(Post.class);

        Root root = q.from(Post.class);

        q.select(root);

        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {
            System.out.println(params);

            String userId = params.get("userId");
            if (userId != null && !userId.isEmpty()) {
                Join<User, Integer> userIdJoin = root.join("userId"); // Join vào thuộc tính userId
                predicates.add(b.equal(userIdJoin.get("id"), Integer.parseInt(userId)));
            }
        }

        q.where(predicates.toArray(Predicate[]::new))
                .orderBy(b.desc(root.get("createdDate")));
        javax.persistence.Query query = session.createQuery(q);

        String p = params.get("pageNumber");
        if (p != null && !p.isEmpty()) {
            int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
            int startIndex = (Integer.parseInt(p) - 1) * pageSize;
            query.setFirstResult(startIndex);
            query.setMaxResults(pageSize);
        }

        List<Post> posts = query.getResultList();

        for (Post post : posts) {
            Hibernate.initialize(post.getActiveReactions());
            Hibernate.initialize(post.getActiveComments());
            Hibernate.initialize(post.getImages());
        }

        return posts;
    }

    @Override
    @Transactional
    public Post upLoadPost(Post post) {
        Session s = this.factory.getObject().getCurrentSession();
        s.save(post);
        return post;
    }

//    @Override
//    public Post addOrUpdatePost(Post p) {
//        Session s = this.factory.getObject().getCurrentSession();
//            if (p.getId() == null) {
//                s.save(p);
//            } else {
//                s.update(p);
//            }
//            return p;
//    }
    @Override
    public Post getPostById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Post WHERE id=:id");
        q.setParameter("id", id);
//        Post p = (Post) q.uniqueResult(); // Sử dụng uniqueResult thay vì getSingleResult
//        if (p != null) {
//            Hibernate.initialize(p.getActiveReactions());
//            Hibernate.initialize(p.getActiveComments());
//            Hibernate.initialize(p.getImages());
//        }
        return (Post) q.getSingleResult();
        
    }

    @Override
    public Long countPosts() {
        Session s = this.factory.getObject().getCurrentSession();
        javax.persistence.Query q = s.createQuery("SELECT Count(*) FROM Post");

        return Long.parseLong(q.getSingleResult().toString());
    }

    @Override
    public boolean deletePost(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Post post = this.getPostById(id);

        try {
            s.delete(post);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }
//    @Override
//    public List<Post> getUserPosts(int userId) {
//        Session session = this.factory.getObject().getCurrentSession();
//        javax.persistence.Query query = session.createQuery("FROM Post WHERE userId.id = :userId");
//        query.setParameter("userId", userId);
//        query.setParameter("userId", userId);
//        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
//
//        // Tính chỉ số bắt đầu của dữ liệu cần trả về
//        int startIndex = (1 - 1) * pageSize;
//        query.setFirstResult(startIndex);
//        query.setMaxResults(pageSize);
//
//        List<Post> posts = query.getResultList();
//        for (Post post : posts) {
//            Hibernate.initialize(post.getActiveComments());
//            Hibernate.initialize(post.getImages());
//        }
//        return posts;
//    }
//
//    @Override
//    public List<Post> getPosts(int pageNumber) {
//        Session session = this.factory.getObject().getCurrentSession();
//        Query q = session.createQuery("FROM Post p ORDER BY p.createdDate DESC");
//        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
//        int startIndex = (pageNumber - 1) * pageSize;
//        q.setFirstResult(startIndex);
//        q.setMaxResults(pageSize);
//
//        List<Post> posts = q.getResultList();
//
//        for (Post post : posts) {
//            Hibernate.initialize(post.getActiveReactions());
//            Hibernate.initialize(post.getActiveComments());
//            Hibernate.initialize(post.getImages());
//        }
//        return posts;
//    }
}
