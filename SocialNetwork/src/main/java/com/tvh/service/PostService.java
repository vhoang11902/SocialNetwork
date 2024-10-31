/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.service;

import com.tvh.pojo.Post;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author hoangtrinh
 */
public interface PostService {
    List<Post> getPosts(Map<String, String> params);
    Long countPosts();
    Post upLoadPost(Map<String, String> params, MultipartFile[] images);
    boolean deletePost(int id);
    Post getPostById(int id);
//    Post addOrUpdatePost(Post p);
//    List<Post> getUserPosts(int userId);
//    List<Post> getPosts(int pageNumber);
    
}
