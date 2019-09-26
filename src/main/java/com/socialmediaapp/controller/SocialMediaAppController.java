package com.socialmediaapp.controller;

import com.socialmediaapp.exception.CustomBadRequestException;
import com.socialmediaapp.model.CreatePostRequestModel;
import com.socialmediaapp.model.FollowUnFollowRequestModel;
import com.socialmediaapp.model.UserModel;
import com.socialmediaapp.service.SocialMediaAppService;
import com.socialmediaapp.service.UserOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

    @Autowired
    private UserOperationsService userOperationsService;


    // getAllUsers() will return the Users from the Collection else throw exception
    @GetMapping("getAllUsers")
    public ResponseEntity getAllUsers() {
        return userOperationsService.getAllUsers();
    }

    //createUser() will return the successfully created User else throw exception
    @PostMapping("createUser")
    public ResponseEntity createUser(@Valid @RequestBody UserModel userRequest) {
        return userOperationsService.createUser(userRequest.getUserId(), userRequest.getUserName());
    }

    //createNewPost() will return the successfully created posts else throw exception
    @PostMapping(value = "createNewPost", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createNewPost(@Valid @RequestBody CreatePostRequestModel postRequestModel) throws InterruptedException {
        return socialMediaAppService.createNewPost(postRequestModel.getUserId(), postRequestModel.getPostId(), postRequestModel.getPostContent());
    }

    //getNewsFeed() will return the to 20 recent post made by user or their followee else throw exception
    @GetMapping("getNewsFeed")
    public ResponseEntity getNewsFeed(@Valid @RequestParam("userId") @NotNull String userId) {
        return socialMediaAppService.getNewsFeed(userId);
    }

    //follow() will follow the user and return the user else throw exception
    @PutMapping(value = "follow", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity follow(@NotNull @RequestBody FollowUnFollowRequestModel followRequestModel) {
        if(followRequestModel.getFolloweeId()==followRequestModel.getFollowerId())
            throw new CustomBadRequestException("Can not follow yourself!");
        return socialMediaAppService.follow(followRequestModel.getFollowerId(), followRequestModel.getFolloweeId());
    }

    //unfollow() will unfollow the user and return the user else throw exception
    @PutMapping(value = "unfollow", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity unFollow(@NotNull @RequestBody FollowUnFollowRequestModel unFollowRequestModel) {
        if(unFollowRequestModel.getFolloweeId()==unFollowRequestModel.getFollowerId())
            throw new CustomBadRequestException("Can not Unfollow yourself!");
        return socialMediaAppService.unFollow(unFollowRequestModel.getFollowerId(), unFollowRequestModel.getFolloweeId());
    }

}
