/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.controllers;

import com.tvh.pojo.UserConnection;
import com.tvh.service.ConnectionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hoangtrinh
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiUserConnectionController {

    @Autowired
    public ConnectionService connectionService;

    
    @GetMapping(path = "/connections/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<List<UserConnection>> listUserConnections(@PathVariable(value = "userId") int userId) {
        List<UserConnection> userConnections = connectionService.getConnection(userId);
        return new ResponseEntity<>(userConnections, HttpStatus.OK);
    }
    
    
    @GetMapping(path = "/reqReceipt/", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<List<UserConnection>> listRequestConnectionReceipt() {
        List<UserConnection> userConnections = connectionService.getRequestConnectionReceipt();
        return new ResponseEntity<>(userConnections, HttpStatus.OK);
    }
    
    
    @GetMapping(path = "/reqSend/", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<List<UserConnection>> listRequestConnectionSend() {
        List<UserConnection> userConnections = connectionService.getRequestConnectionSend();
        return new ResponseEntity<>(userConnections, HttpStatus.OK);
    }
    
    
    @GetMapping(path = "/actConnect/", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<List<UserConnection>> listAcceptConnections() {
        List<UserConnection> userConnections = connectionService.getAcceptConnections();
        return new ResponseEntity<>(userConnections, HttpStatus.OK);
    }

    
    @PostMapping("/connections/{connectionId}")
    @CrossOrigin
    public ResponseEntity<String> addConnection(@PathVariable(value = "connectionId") int connectionId) {
        try {
            connectionService.addConnection(connectionId);
            return ResponseEntity.ok("Connection request sent successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send connection request.");
        }
    }
    
    @PutMapping("/connections/{id}/accept")
    @CrossOrigin
    public ResponseEntity<String> acceptConnection(@PathVariable(value = "id") int id) {
        try {
            connectionService.acceptConnection(id);
            return ResponseEntity.ok("Connection request sent successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send connection request.");
        }
    }
    
    
    @PutMapping("/connections/{id}/delete")
    @CrossOrigin
    public ResponseEntity<String> delConnection(@PathVariable(value = "id") int id) {
        try {
            connectionService.delConnection(id);
            return ResponseEntity.ok("Connection request sent delete.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send delete request.");
        }
    }
    
    
    @DeleteMapping("/connections/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin
    public void deleteConnection(@PathVariable(value = "id") int id) {
        this.connectionService.deleteConnection(id);
    }
}
