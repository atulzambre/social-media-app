package com.socialmediaapp.controller;

import com.socialmediaapp.service.SocialMediaAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocialMediaAppController {

    @Autowired
    private SocialMediaAppService socialMediaAppService;

    @GetMapping("ping")
    public String getPing() {

        return "Social Media App is working.";
    }

    @GetMapping("getAllUsers")
    public ResponseEntity getAllUsers() {
        return socialMediaAppService.getAllUsers();
    }

    @GetMapping("createUser")
    public ResponseEntity createUser(@RequestParam("userID") int userID, @RequestParam("userName") String userName) {

        ResponseEntity responseUser = socialMediaAppService.createUser(userID, userName);
        return responseUser;
    }

    @GetMapping("createNewPost")
    public ResponseEntity createNewPost(@RequestParam("userID") int userID, @RequestParam("postContent") String postContent) {
        ResponseEntity responseUser = socialMediaAppService.createNewPost(userID, postContent);
        return responseUser;
    }

    @GetMapping("getNewsFeed")
    public String getNewsFeed() {
        return "20 most recent feeds created.";
    }

    @GetMapping("follow")
    public ResponseEntity follow(@RequestParam("followerId") int followerId, @RequestParam("followeeId") int followeeId) {
        ResponseEntity responseFollow = socialMediaAppService.follow(followerId, followeeId);
        return responseFollow;
    }

    @GetMapping("unfollow")
    public ResponseEntity unFollow(@RequestParam("followerId") int followerId, @RequestParam("followeeId") int followeeId) {
        ResponseEntity responseFollow = socialMediaAppService.unFollow(followerId, followeeId);
        return responseFollow;
    }

}
