package com.socialmediaapp.model;

import javax.validation.constraints.Pattern;

public class CreatePostRequestModel {

    @Pattern(regexp = "^[1-9][0-9]*$")
    private String userId;

    @Pattern(regexp = "^[1-9][0-9]*$")
    private String postId;

    @Pattern(regexp = "^(?=\\s*\\S).*$")
    private String postContent;

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
}
