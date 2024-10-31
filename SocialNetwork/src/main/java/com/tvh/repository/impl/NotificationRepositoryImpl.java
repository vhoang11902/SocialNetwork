/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.repository.impl;

import com.tvh.pojo.Notification;
import com.tvh.repository.NotificationRepository;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

/**
 *
 * @author hoangtrinh
 */
public class NotificationRepositoryImpl implements NotificationRepository{

    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public List<Notification> getNotis(int userId) {
        Session session = this.factory.getObject().getCurrentSession();
        javax.persistence.Query query = session.createQuery("FROM Notification WHERE user.id = :userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
}
