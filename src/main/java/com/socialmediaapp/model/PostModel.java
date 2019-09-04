package com.socialmediaapp.model;

import java.time.LocalDateTime;

public class PostModel implements Comparable<PostModel> {
    private int postId;
    private String postContent;
    private LocalDateTime postCreated;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
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

    @Override
    public int compareTo(PostModel o) {
        if (o.getPostCreated().isAfter(this.getPostCreated())) {
            return 1;
        } else if (o.getPostCreated().isBefore(this.getPostCreated())) {
            return -1;
        }
        return 0;
    }
}
