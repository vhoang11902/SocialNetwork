/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.service;

import com.tvh.pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
public interface UserService extends UserDetailsService  {
    List<User> getUsers(Map<String, String> params);
    User getUserByUn(String username);
    User getUserById(Integer id);
    boolean authUser(String username, String password);
    boolean activeUser(Integer id);
    User addUser(Map<String, String> params, MultipartFile avatar,MultipartFile coverImage);
    User addUserAdmin(Map<String, String> params, MultipartFile avatar,MultipartFile coverImage);
    Long countUsers();
}
