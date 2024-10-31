/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.repository;

import com.tvh.pojo.Post;
import com.tvh.pojo.User;
import com.tvh.pojo.UserConnection;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author hoangtrinh
 */
public interface PostRepository {
    List<Post> getPosts(Map<String, String> params);
    Post upLoadPost(Post post);
    Post getPostById(int id);
    Long countPosts();
    boolean deletePost(int id);
//    Post addOrUpdatePost(Post p);
//    List<Post> getUserPosts(int userId);
//    List<Post> getPosts(int pageNumber);
//    boolean deletePost(Post post);
//    boolean updatePost(Post post);
//    Post findById(Post post);
}
