package com.socialmediaapp.model;

import java.time.LocalDateTime;

public class PostModel implements Comparable<PostModel>{
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

    @Override
    public int compareTo(PostModel o) {
        if(o.getPostCreated().isAfter(this.getPostCreated())){
            return 1;
        }
        else if(o.getPostCreated().isBefore(this.getPostCreated())){
            return -1;
        }
        return 0;
    }
}
