package com.socialmediaapp.model;

import java.time.LocalDateTime;

public class PostModel {
    private String postId;
    private String postContent;
    private LocalDateTime postCreated;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public LocalDateTime getPostCreated() {
        return postCreated;
    }

    public void setPostCreated() {
        this.postCreated = LocalDateTime.now();
    }

}
