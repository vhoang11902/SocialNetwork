/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.service;

import com.tvh.pojo.UserConnection;
import com.tvh.repository.ConnectionRepository;
import java.util.List;

/**
 *
 * @author hoangtrinh
 */
public interface ConnectionService {
    List<UserConnection> getConnection(int user_id);
    List<UserConnection> getConnections(int userId);
    List<UserConnection> getRequestConnectionReceipt();
    List<UserConnection> getRequestConnectionSend();
    List<UserConnection> getAcceptConnections();
    boolean addConnection(int connectionId);
    boolean acceptConnection(int id);
    boolean delConnection(int id);
    boolean deleteConnection(int id);
}
