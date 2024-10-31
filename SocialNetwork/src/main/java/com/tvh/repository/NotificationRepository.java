/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.repository;

import com.tvh.pojo.Notification;
import java.util.List;

/**
 *
 * @author hoangtrinh
 */
public interface NotificationRepository {
    List<Notification> getNotis(int userId);
}
