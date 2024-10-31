/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.repository;

import com.tvh.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public interface UserReppository {
    List<User> getUsers(Map<String, String> params);
    User getUserByUsername(String username);
    User getUserById(int id);
    boolean authUser(String username, String password);
    boolean activeUser(Integer id);
    User addUser(User user);
    Long countUsers();
}
