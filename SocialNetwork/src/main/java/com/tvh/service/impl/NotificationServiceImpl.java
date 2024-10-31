/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.service.impl;

import com.tvh.pojo.Notification;
import com.tvh.repository.NotificationRepository;
import com.tvh.service.NotificationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author hoangtrinh
 */
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private NotificationRepository notiRepository;

    @Override
    public List<Notification> getNotis(int userId) {
        return this.notiRepository.getNotis(userId);
    }
    
}
