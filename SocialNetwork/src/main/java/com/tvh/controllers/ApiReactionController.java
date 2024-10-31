/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.controllers;

import com.tvh.pojo.Reaction;
import com.tvh.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class ApiReactionController {

    @Autowired
    public ReactionService reactionService;

    @PostMapping("posts/{postId}/reactions/")
    @CrossOrigin
    public ResponseEntity<Reaction> addReaction(@PathVariable(value = "postId") int postId, @RequestParam(defaultValue = "LIKE") String action) {
        Reaction r = reactionService.addReaction(postId, action); // Truyền giá trị action vào phương thức addReaction
        return new ResponseEntity<>(r, HttpStatus.OK); 
    }
    
    @DeleteMapping("posts/{postId}/reactions/{reactionId}")
    @CrossOrigin
    public ResponseEntity<String> unActiveReaction(@PathVariable(value = "reactionId") int reactionId) {
        try {
            reactionService.unActiveReaction(reactionId); // Truyền giá trị action vào phương thức addReaction
            return ResponseEntity.ok("Reaction delete successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete reaction.");
        }
    }
    
    @DeleteMapping("/reactions/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean deleteReaction(@PathVariable(value = "id") int commentId) {
        return reactionService.deleteReaction(commentId);
    }
    
    @PutMapping("posts/{postId}/reactions/{reactionId}")
    @CrossOrigin
    public ResponseEntity<Reaction> updateReaction(@PathVariable(value = "reactionId") int reactionId,@RequestParam(defaultValue = "LIKE") String action) {
        Reaction r = reactionService.updateReaction(reactionId, action); // Truyền giá trị action vào phương thức addReaction
        return new ResponseEntity<>(r, HttpStatus.OK); 
    }
}
