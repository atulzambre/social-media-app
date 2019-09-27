package com.socialmediaapp.service;

import org.springframework.http.ResponseEntity;

public interface SocialMediaAppService {

    ResponseEntity createNewPost(String userId, String postID, String postContent);

    ResponseEntity follow(String followerId, String followeeId);

    ResponseEntity unFollow(String followerId, String followeeId);

    ResponseEntity getNewsFeed(String userId);


}
