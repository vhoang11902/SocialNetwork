/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.service.impl;

import com.tvh.pojo.Reaction;
import com.tvh.repository.ReactionRepository;
import com.tvh.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hoangtrinh
 */
@Service
public class ReactionServiceImpl implements ReactionService{

    @Autowired
    private ReactionRepository reactionRepository;
    
    
    
    @Override
    public Reaction addReaction(int postId, String action) {
        Reaction r = new Reaction();
        return this.reactionRepository.addReaction(postId,action,r);
    }

    @Override
    public boolean unActiveReaction(int reactionId) {
        return this.reactionRepository.unActiveReaction(reactionId);
    }
    
    @Override
    public boolean deleteReaction(int reactionId) {
        return this.reactionRepository.deleteReaction(reactionId);
    }

    @Override
    public Reaction updateReaction(int reactionId, String action) {
        Reaction r = this.reactionRepository.getReactionById(reactionId);
        return this.reactionRepository.updateReaction(action,r);
    }

    
    
    
    
}
