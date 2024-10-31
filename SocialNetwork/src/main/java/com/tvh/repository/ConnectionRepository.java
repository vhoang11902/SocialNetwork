/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.repository;

import com.tvh.pojo.User;
import com.tvh.pojo.UserConnection;
import java.util.List;


/**
 *
 * @author hoangtrinh
 */
public interface ConnectionRepository {
    List<UserConnection> getConnection(int userId);
    
    List<UserConnection> getRequestConnectionReceipt();
    List<UserConnection> getRequestConnectionSend();
    List<UserConnection> getAcceptConnections();
    List<UserConnection> getConnections(int userId);
    UserConnection getConnectionById(int id);
    boolean addConnection(int connectionId);
    boolean acceptConnection(int id);
    boolean delConnection(int id);
    boolean deleteConnection(int id);
    
}
