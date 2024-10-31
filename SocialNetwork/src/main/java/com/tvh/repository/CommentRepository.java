/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tvh.repository;

import com.tvh.pojo.Comment;
import com.tvh.pojo.User;
import java.util.List;

/**
 *
 * @author huu-thanhduong
 */
public interface CommentRepository {
    List<Comment> getComments(int productId);
    Comment getCommentById(int id);
    Comment addComment(Comment c);
    Comment updateComment(Comment c);
    boolean unActiveComment(int commentId);
    boolean deleteComment(int commentId);
}
