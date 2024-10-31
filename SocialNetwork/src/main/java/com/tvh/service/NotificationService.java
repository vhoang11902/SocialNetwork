/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.service;

import com.tvh.pojo.Notification;
import java.util.List;

/**
 *
 * @author hoangtrinh
 */
public interface NotificationService {
    List<Notification> getNotis(int userId);
}
