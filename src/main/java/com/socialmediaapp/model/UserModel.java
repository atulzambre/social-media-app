package com.socialmediaapp.model;

import java.util.*;

public class UserModel {
    private String userId;
    private String userName;
    private Map<String, PostModel> postModelMap=new HashMap<>();
    private List<PostModel> posts=new ArrayList<>();
    private Set<String> followees=new HashSet<>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Map<String, PostModel> getPostModelMap() {
        return postModelMap;
    }

    public void setPostModelMap(Map<String, PostModel> postModelMap) {
        this.postModelMap = postModelMap;
    }

    public List<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(List<PostModel> posts) {
        this.posts = posts;
    }

    public Set<String> getFollowees() {
        return followees;
    }

    public void setFollowees(Set<String> followees) {
        this.followees = followees;
    }
    public List<PostModel> getTopPosts(int numberOfPosts) {
        int size = posts.size();
        if(size<=20){
            return posts;
        }
        else{
            return posts.subList(size - 21, size);
        }

    }
}
