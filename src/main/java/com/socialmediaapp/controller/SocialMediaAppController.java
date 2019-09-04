package com.socialmediaapp.controller;

import com.socialmediaapp.model.CreatePostRequestModel;
import com.socialmediaapp.model.FollowUnFollowRequestModel;
import com.socialmediaapp.model.UserModel;
import com.socialmediaapp.service.SocialMediaAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * SocialMediaApp designed to Create Users, Retrieve All Users, Follow Users, Unfollow Users
 * and get the top 20 recent feeds from users and there following users.
 * SocialMediaController is the entry point to access the Http Methods.
 * Used Lambda/Stream API , Builder Design Pattern, Factory Design Pattern, Dependency Injection etc.
 * Returning the updated User Models with the successful response to better understand the working of the API.
 *
 * @author atulzambre
 */
@RestController
class SocialMediaAppController {

    //Injected the dependency of SocialMediaAppServiceImpl
    @Autowired
    private SocialMediaAppService socialMediaAppService;


    // getAllUsers() will return the Users from the Collection else throw exception
    @GetMapping("getAllUsers")
    public ResponseEntity getAllUsers() {
        return socialMediaAppService.getAllUsers();
    }

    //createUser() will return the successfully created User else throw exception
    @PostMapping("createUser")
    public ResponseEntity createUser(@RequestBody UserModel userRequest) {
        return socialMediaAppService.createUser(userRequest.getUserId(), userRequest.getUserName());
    }

    //createNewPost() will return the successfully created posts else throw exception
    @PostMapping(value = "createNewPost", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createNewPost(@RequestBody CreatePostRequestModel postRequestModel) {
        return socialMediaAppService.createNewPost(postRequestModel.getUserId(), postRequestModel.getPostId(), postRequestModel.getPostContent());
    }

    //getNewsFeed() will return the to 20 recent post made by user or their followee else throw exception
    @GetMapping("getNewsFeed")
    public ResponseEntity getNewsFeed(@RequestParam("userId") int userId) {
        return socialMediaAppService.getNewsFeed(userId);
    }

    //follow() will follow the user and return the user else throw exception
    @PostMapping(value = "follow", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity follow(@RequestBody FollowUnFollowRequestModel followRequestModel) {
        return socialMediaAppService.follow(followRequestModel.getFollowerId(), followRequestModel.getFolloweeId());
    }

    //unfollow() will unfollow the user and return the user else throw exception
    @PostMapping(value = "unfollow", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity unFollow(@RequestBody FollowUnFollowRequestModel unFollowRequestModel) {
        return socialMediaAppService.unFollow(unFollowRequestModel.getFollowerId(), unFollowRequestModel.getFolloweeId());
    }

}
