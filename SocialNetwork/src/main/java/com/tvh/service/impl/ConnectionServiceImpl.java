/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.service.impl;

import com.tvh.pojo.UserConnection;
import com.tvh.repository.ConnectionRepository;
import com.tvh.service.ConnectionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hoangtrinh
 */
@Service
public class ConnectionServiceImpl implements ConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;

    @Override
    public List<UserConnection> getConnection(int userId) {
        return this.connectionRepository.getConnection(userId);
    }
    
    @Override
    public List<UserConnection> getConnections(int userId) {
        return this.connectionRepository.getConnections(userId);
    }

    @Override
    public boolean addConnection(int connectionId) {
        return this.connectionRepository.addConnection(connectionId);
    }

    @Override
    public boolean acceptConnection(int id) {
        return this.connectionRepository.acceptConnection(id);
    }

    @Override
    public List<UserConnection> getRequestConnectionReceipt() {
        return this.connectionRepository.getRequestConnectionReceipt();
    }
    
    @Override
    public List<UserConnection> getRequestConnectionSend() {
        return this.connectionRepository.getRequestConnectionSend();
    }

    @Override
    public List<UserConnection> getAcceptConnections() {
        return this.connectionRepository.getAcceptConnections();
    }

    @Override
    public boolean delConnection(int id) {
        return this.connectionRepository.delConnection(id);
    }
    
    @Override
    public boolean deleteConnection(int id) {
        return this.connectionRepository.deleteConnection(id);
    }

}
