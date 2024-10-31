/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tvh.service;

import com.tvh.pojo.Comment;
import com.tvh.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author huu-thanhduong
 */
public interface CommentService {
    List<Comment> getComments(int productId);
    Comment addComment(Map<String, String> params);
    boolean unActiveComment(int commentId);
    boolean deleteComment(int commentId);

}
