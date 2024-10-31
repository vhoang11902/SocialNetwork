/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author hoangtrinh
 */
@Entity
@Table(name = "post")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "post_content")
    private String postContent;

    @Column(name = "post_audiance")
    private String postAudiance;

    @Column(name = "post_status")
    private String postStatus;

    @Column(name = "comment_status", columnDefinition = "TINYINT DEFAULT 1")
    private boolean commentStatus;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(targetEntity = User.class)
    private User userId;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "postId", fetch = FetchType.EAGER)
    private Set<Image> images;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "post",fetch = FetchType.EAGER)
    private Set<Reaction> reactions;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "post",fetch = FetchType.EAGER)
    private Set<Comment> comments;

    /**
     * @return the id
     */
    public List<Comment> getActiveComments() {
        List<Comment> activeComments = new ArrayList<>();
        if (comments != null) {
            for (Comment comment : comments) {
                if (comment.isActive()) {
                    activeComments.add(comment);
                }
            }
        }
        return activeComments;
    }

    public List<Reaction> getActiveReactions() {
        List<Reaction> activeReactions = new ArrayList<>();
        if (reactions != null) {
            for (Reaction reaction : reactions) {
                if (reaction.isActive()) {
                    activeReactions.add(reaction);
                }
            }
        }
        return activeReactions;
    }

    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the postContent
     */
    public String getPostContent() {
        return postContent;
    }

    /**
     * @param postContent the postContent to set
     */
    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    /**
     * @return the postAudiance
     */
    public String getPostAudiance() {
        return postAudiance;
    }

    /**
     * @param postAudiance the postAudiance to set
     */
    public void setPostAudiance(String postAudiance) {
        this.postAudiance = postAudiance;
    }

    /**
     * @return the postStatus
     */
    public String getPostStatus() {
        return postStatus;
    }

    /**
     * @param postStatus the postStatus to set
     */
    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the userId
     */
    public User getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(User userId) {
        this.userId = userId;
    }

    /**
     * @return the images
     */
    public Set<Image> getImages() {
        return images;
    }

    /**
     * @param images the images to set
     */
    public void setImages(Set<Image> images) {
        this.images = images;
    }

    /**
     * @return the comments
     */
    public Set<Comment> getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    /**
     * @return the commentStatus
     */
    public boolean isCommentStatus() {
        return commentStatus;
    }

    /**
     * @param commentStatus the commentStatus to set
     */
    public void setCommentStatus(boolean commentStatus) {
        this.commentStatus = commentStatus;
    }

    /**
     * @return the reactions
     */
    public Set<Reaction> getReactions() {
        return reactions;
    }

    /**
     * @param reactions the reactions to set
     */
    public void setReactions(Set<Reaction> reactions) {
        this.reactions = reactions;
    }

}
