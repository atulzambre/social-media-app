package com.socialmediaapp.model;

import javax.validation.constraints.Pattern;
import java.util.*;
import java.util.stream.Collectors;

public class UserModel {

    @Pattern(regexp = "^[1-9][0-9]*$")
    private String userId;
    @Pattern(regexp = "^(?=\\s*\\S).*$")
    private String userName;
    private Map<String, PostModel> postModelMap = new HashMap<>();
    private Set<PostModel> posts = new TreeSet<>();
    private Set<String> followees = new HashSet<>();


    public Set<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(Set<PostModel> posts) {
        this.posts = posts;
    }

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


    public Set<String> getFollowees() {
        return followees;
    }

    public void setFollowees(Set<String> followees) {
        this.followees = followees;
    }

    public Set<PostModel> getTopPosts(int numberOfPosts) {
        int size = posts.size();
        if (size <= numberOfPosts) {
            return posts;
        } else {
            return posts.stream().limit(numberOfPosts).collect(Collectors.toSet());
        }

    }
}
