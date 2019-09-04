package com.socialmediaapp.controller;

import com.socialmediaapp.model.CreatePostRequestModel;
import com.socialmediaapp.model.FollowUnFollowRequestModel;
import com.socialmediaapp.model.UserModel;
import com.socialmediaapp.service.SocialMediaAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SocialMediaAppController {

    @Autowired
    private SocialMediaAppService socialMediaAppService;

    @GetMapping("getAllUsers")
    public ResponseEntity getAllUsers() {
        return socialMediaAppService.getAllUsers();
    }

    @PostMapping("createUser")
    public ResponseEntity createUser(@RequestBody UserModel userRequest) {
        return socialMediaAppService.createUser(userRequest.getUserID(), userRequest.getUserName());
    }

    @PostMapping(value = "createNewPost",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createNewPost(@RequestBody CreatePostRequestModel postRequestModel) {
        return socialMediaAppService.createNewPost(postRequestModel.getUserID(),postRequestModel.getPostID(),postRequestModel.getPostContent());
    }

    @GetMapping("getNewsFeed")
    public ResponseEntity getNewsFeed(@RequestParam("userID") int userID) {
        return socialMediaAppService.getNewsFeed(userID);
    }

    @PostMapping(value="follow",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity follow(@RequestBody FollowUnFollowRequestModel followRequestModel) {
        return socialMediaAppService.follow(followRequestModel.getFollowerId(), followRequestModel.getFolloweeId());
    }

    @PostMapping(value="unfollow",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity unFollow(@RequestBody FollowUnFollowRequestModel unFollowRequestModel) {
        return socialMediaAppService.unFollow(unFollowRequestModel.getFollowerId(), unFollowRequestModel.getFolloweeId());
    }

}
