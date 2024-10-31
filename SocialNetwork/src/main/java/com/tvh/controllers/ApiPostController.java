/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.controllers;

import com.tvh.pojo.Comment;
import com.tvh.pojo.Image;
import com.tvh.pojo.Post;
import com.tvh.pojo.UserConnection;
import com.tvh.service.CommentService;
import com.tvh.service.PostService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author hoangtrinh
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiPostController {

    @Autowired
    public PostService postService;
    @Autowired
    public CommentService commentService;

    
    @PostMapping(path = "/posts/",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public ResponseEntity<Post> upLoadPost(
            @RequestParam Map<String, String> params,
            @RequestPart(required = false)  MultipartFile[] images) {
        Post post = this.postService.upLoadPost(params, images);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }
    
    
    @DeleteMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable(value = "id") int id) {
        this.postService.deletePost(id);
    }
    
//    @GetMapping(path = "/user/{userId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
//    @CrossOrigin
//    public ResponseEntity<List<Post>> listPostUser(@PathVariable(value = "userId") int userId) {
//        List<Post> posts = postService.getUserPosts(userId);
//        return new ResponseEntity<>(posts, HttpStatus.OK);
//    }
    
    @RequestMapping("/posts")
    @CrossOrigin
    public ResponseEntity<List<Post>> list(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.postService.getPosts(params), HttpStatus.OK);
    }
    
//    @GetMapping(path = "/posts")
//    @CrossOrigin
//    public ResponseEntity<List<Post>> listPostUsers(
//        @RequestParam(defaultValue = "1") int pageNumber
//    ) {
//        List<Post> posts = postService.getPosts(pageNumber);
//        return new ResponseEntity<>(posts, HttpStatus.OK);
//    }
//    
    
}
