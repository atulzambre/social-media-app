package com.socialmediaapp.model;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class PostModel implements Comparable<PostModel>{
    private String userId;
    private String postId;
    private String postContent;
    private LocalDateTime postCreated;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public void setPostCreated(){
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        }
        catch (Exception e){

        }
        this.postCreated = LocalDateTime.now();
    }


    @Override
    public int compareTo(PostModel o) {
        return o.getPostCreated().compareTo(this.getPostCreated());
    }
}
