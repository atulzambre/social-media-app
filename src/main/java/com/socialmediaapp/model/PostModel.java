package com.socialmediaapp.model;

import java.time.LocalDateTime;

public class PostModel {
    private int postID;
    private String postContent;
    private LocalDateTime postCreated;

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
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

    public void setPostCreated(LocalDateTime postCreated) {
        this.postCreated = postCreated;
    }
}
