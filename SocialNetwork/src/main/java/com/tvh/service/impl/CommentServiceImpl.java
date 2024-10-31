/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.service.impl;

import com.tvh.pojo.Comment;
import com.tvh.pojo.Post;
import com.tvh.pojo.User;
import com.tvh.repository.CommentRepository;
import com.tvh.repository.PostRepository;
import com.tvh.repository.UserReppository;
import com.tvh.service.CommentService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author huu-thanhduong
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepo;
    @Autowired
    private UserReppository userRepo;
    
    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Comment> getComments(int productId) {
        return this.commentRepo.getComments(productId);
    }

    @Override
    public Comment addComment(Map<String, String> params) {
        Comment c = new Comment();
        c.setCreatedDate(new Date());
        String id = params.get("id");
        String postId = params.get("post");
        int postIdValue = Integer.parseInt(postId);
        Post p = this.postRepository.getPostById(postIdValue);
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User u = this.userRepo.getUserByUsername(authentication.getName());

        
        if (id != null) {
            int idValue = Integer.parseInt(id);
            if(commentRepo.getCommentById(idValue) != null){
            Comment comment = this.commentRepo.getCommentById(idValue);
            c.setId(idValue);
            c.setContent(params.get("content"));
            c.setPost(p);
            c.setActive(true);
            c.setUser(comment.getUser());
            return this.commentRepo.updateComment(c);
            }
        }  
        else{
            c.setContent(params.get("content"));
            c.setPost(p);
            c.setActive(true);
            c.setUser(u);
            return this.commentRepo.addComment(c);
        }   
        return null;
    }
    

    @Override
    public boolean unActiveComment(int commentId) {
        return this.commentRepo.unActiveComment(commentId);
    }
    
    @Override
    public boolean deleteComment(int commentId) {
        return this.commentRepo.deleteComment(commentId);
    }
    
}
