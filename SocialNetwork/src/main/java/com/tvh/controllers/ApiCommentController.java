/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.controllers;

import com.tvh.pojo.Comment;
import com.tvh.pojo.User;
import com.tvh.service.CommentService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hoangtrinh
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiCommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/posts/{postId}/comments/")
    @CrossOrigin
    public ResponseEntity<List<Comment>> listComments(@PathVariable(value = "postId") int postId) {
        return new ResponseEntity<>(this.commentService.getComments(postId), HttpStatus.OK);
    }

    @PostMapping(path = "/comments/", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<Comment> addComment(@RequestBody Map<String, String> params) {
        Comment c = this.commentService.addComment(params);
        return new ResponseEntity<>(c, HttpStatus.CREATED);
    }

    @PutMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean unActiveComment(@PathVariable(value = "id") int commentId) {
        return commentService.unActiveComment(commentId);
    }
    
    @DeleteMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean deleteComment(@PathVariable(value = "id") int commentId) {
        return commentService.deleteComment(commentId);
    }
    
//    @GetMapping("/products/{productId}/comments/")
//    @CrossOrigin
//    public ResponseEntity<List<Comment>> listComments(@PathVariable(value = "productId") int id) {
//        return new ResponseEntity<>(this.commentService.getComments(id), HttpStatus.OK);
//    }
//    
//    @PostMapping(path="/comments/", produces = MediaType.APPLICATION_JSON_VALUE)
//    @CrossOrigin
//    public ResponseEntity<Comment> addComment(@RequestBody Comment comment) {
//        Comment c = this.commentService.addComment(comment);
//        
//        return new ResponseEntity<>(c, HttpStatus.CREATED);
//    }
}
