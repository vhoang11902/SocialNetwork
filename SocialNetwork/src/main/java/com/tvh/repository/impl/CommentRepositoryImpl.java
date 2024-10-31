/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.repository.impl;

import com.tvh.pojo.Comment;
import com.tvh.pojo.Post;
import com.tvh.pojo.User;
import com.tvh.repository.CommentRepository;
import com.tvh.repository.PostRepository;
import com.tvh.repository.UserReppository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.boot.spi.SessionFactoryBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author huu-thanhduong
 */
@Repository
@Transactional
public class CommentRepositoryImpl implements CommentRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private UserReppository userRepo;

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Comment> getComments(int productId) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("From Comment Where post.id=:id");
        q.setParameter("id", productId);

        return q.getResultList();
    }

    @Override
    public Comment addComment(Comment c) {
        Session s = this.factory.getObject().getCurrentSession();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User u = this.userRepo.getUserByUsername(authentication.getName());
        try {
            s.save(c);
            return c;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Comment updateComment(Comment c) {
        Session s = this.factory.getObject().getCurrentSession();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User u = this.userRepo.getUserByUsername(authentication.getName());
        try {
            if (c.getUser().getId() == u.getId()) {
                s.update(c);
            }
            return c;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return null;
        }
    }

//            try {
//            if (p.getId() == null) {
//                s.save(p);
//            } else {
//                s.update(p);
//            }
//
//            return true;
//        } catch (HibernateException ex) {
//            ex.printStackTrace();
//            return false;
//        }
    @Override
    public Comment getCommentById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Query q = session.createQuery("FROM Comment WHERE id=:id");
        q.setParameter("id", id);

        return (Comment) q.getSingleResult();
    }

    @Override
    public boolean unActiveComment(int commentId) {
        Session session = this.factory.getObject().getCurrentSession();
        Comment comment = this.getCommentById(commentId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User u = this.userRepo.getUserByUsername(authentication.getName());
        Post p = this.postRepository.getPostById(comment.getPost().getId());
        System.out.println(p);
        if (u.getId() == comment.getUser().getId() || u.getId() == p.getUserId().getId()) {
            try {
                comment.setActive(false); // Đánh dấu comment là "đã xoá"
                session.update(comment);
                return true;
            } catch (HibernateException ex) {
                ex.printStackTrace();
                return false;
            }
        } else {
            return false;
        }

//        
    }
    
    @Override
    public boolean deleteComment(int commentId) {
        Session s = this.factory.getObject().getCurrentSession();
        Comment comment = this.getCommentById(commentId);
        try {
            s.delete(comment);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        } 
    }
}
