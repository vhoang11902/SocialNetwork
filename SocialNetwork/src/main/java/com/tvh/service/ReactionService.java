/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.service;

import com.tvh.pojo.Reaction;

/**
 *
 * @author hoangtrinh
 */
public interface ReactionService {
    Reaction addReaction(int postId, String action);
    boolean unActiveReaction(int reactionId);
    Reaction updateReaction(int reactionId, String action);
    boolean deleteReaction(int reactionId);
}
