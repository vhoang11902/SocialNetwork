/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.repository;

import com.tvh.pojo.Reaction;

/**
 *
 * @author hoangtrinh
 */
public interface ReactionRepository {
    Reaction getReactionById(int id);
    Reaction addReaction(int postId, String action, Reaction r);
    boolean unActiveReaction(int reactionId);
    boolean deleteReaction(int reactionId);
    Reaction updateReaction(String action, Reaction r);
    
}
