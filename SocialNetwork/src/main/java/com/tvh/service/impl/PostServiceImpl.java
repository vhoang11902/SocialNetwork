/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tvh.pojo.Image;
import com.tvh.pojo.Post;
import com.tvh.pojo.User;
import com.tvh.repository.PostRepository;
import com.tvh.repository.UserReppository;
import com.tvh.service.PostService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author hoangtrinh
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserReppository userRepo;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<Post> getPosts(Map<String, String> params) {
        return this.postRepository.getPosts(params);
    }

    @Override
    public Long countPosts() {
        return this.postRepository.countPosts();
    }

    @Override
    @Transactional
    public Post upLoadPost(Map<String, String> params, MultipartFile[] images) {

        Post post = new Post();
        post.setPostContent(params.get("postContent"));
        post.setPostAudiance(params.get("postAudiance"));
        post.setPostStatus(params.get("postStatus"));
        post.setCommentStatus(Boolean.parseBoolean(params.get("commentStatus")));
        post.setCreatedDate(new Date());
        post.setComments(null);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userRepo.getUserByUsername(authentication.getName());
        post.setUserId(user);

// Lưu post vào cơ sở dữ liệu
        this.postRepository.upLoadPost(post);
        Session s = this.factory.getObject().getCurrentSession();
        // Lưu ảnh vào cơ sở dữ liệu
        if (images != null && images.length > 0) {
            for (MultipartFile image : images) {
                try {
                    // Upload ảnh lên Cloudinary hoặc bất kỳ dịch vụ lưu trữ ảnh nào khác
                    Map<String, Object> uploadResult = this.cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                    String imageUrl = uploadResult.get("secure_url").toString();

                    // Tạo entity Image và lưu vào cơ sở dữ liệu
                    Image imageEntity = new Image();
                    imageEntity.setPostId(post);
                    imageEntity.setImageUrl(imageUrl);
                    s.save(imageEntity);
                } catch (IOException ex) {
                    Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return post;
    }
    
    @Override
    public boolean deletePost(int id) {
         return this.postRepository.deletePost(id);
    }
    @Override
    public Post getPostById(int id) {
        return this.postRepository.getPostById(id);
    }

//    @Override
//    public List<Post> getUserPosts(int userId) {
//        return this.postRepository.getUserPosts(userId);
//    }
//    @Override
//    public List<Post> getPosts(int pageNumber) {
//        return this.postRepository.getPosts(pageNumber);
//    }
}
