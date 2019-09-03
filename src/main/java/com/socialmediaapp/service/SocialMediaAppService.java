package com.socialmediaapp.service;

import org.springframework.http.ResponseEntity;

import javax.xml.ws.Response;

public interface SocialMediaAppService {

    public ResponseEntity createUser(int userID, String userName);

    public ResponseEntity getAllUsers();

    public ResponseEntity createNewPost(int userID, String postContent);

    public ResponseEntity follow(int followerId, int followeeId);

    public ResponseEntity unFollow(int followerId, int followeeId);

    public ResponseEntity getNewsFeed(int userID);


}
