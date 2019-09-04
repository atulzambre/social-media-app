package com.socialmediaapp.service;

import org.springframework.http.ResponseEntity;

public interface SocialMediaAppService {

    ResponseEntity createUser(int userId, String userName);

    ResponseEntity getAllUsers();

    ResponseEntity createNewPost(int userId, int postID, String postContent);

    ResponseEntity follow(int followerId, int followeeId);

    ResponseEntity unFollow(int followerId, int followeeId);

    ResponseEntity getNewsFeed(int userId);


}
