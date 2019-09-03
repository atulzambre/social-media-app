package com.socialmediaapp.model;

import java.util.List;

public class UserModel {
    private int userID;
    private String userName;
    private List<PostModel> posts;
    private List<FollowModel> followee;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public List<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(List<PostModel> posts) {
        this.posts = posts;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<FollowModel> getFollowee() {
        return followee;
    }

    public void setFollowee(List<FollowModel> followee) {
        this.followee = followee;
    }
}
