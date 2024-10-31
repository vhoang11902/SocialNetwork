/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.repository.impl;

import com.tvh.pojo.Notification;
import com.tvh.pojo.Post;
import com.tvh.pojo.Reaction;
import com.tvh.pojo.User;
import com.tvh.repository.PostRepository;
import com.tvh.repository.ReactionRepository;
import com.tvh.repository.UserReppository;
import java.util.Date;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hoangtrinh
 */
@Repository
@Transactional
public class ReactionRepositoryImpl implements ReactionRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private UserReppository userRepo;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Reaction getReactionById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Query reaction = session.createQuery("FROM Reaction WHERE id=:id");
        reaction.setParameter("id", id);

        return (Reaction) reaction.getSingleResult();
    }

    @Override
    public Reaction addReaction(int postId, String action, Reaction r) {
        Session session = this.factory.getObject().getCurrentSession();
        Notification noti = new Notification();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User u = this.userRepo.getUserByUsername(authentication.getName());
            Post p = this.postRepository.getPostById(postId);
            r.setUser(u);
            r.setPost(p);
            r.setAction(action);
            r.setActive(true);
            session.save(r);

            return r;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean unActiveReaction(int reactionId) {
        Session session = this.factory.getObject().getCurrentSession();
        Reaction reaction = this.getReactionById(reactionId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            reaction.setActive(false);
            session.update(reaction);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Reaction updateReaction(String action, Reaction r) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            r.setAction(action);
            session.update(r);
            return r;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteReaction(int reactionId) {
        Session s = this.factory.getObject().getCurrentSession();
        Reaction reaction = this.getReactionById(reactionId);
        try {
            s.delete(reaction);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        } 
    }

}
