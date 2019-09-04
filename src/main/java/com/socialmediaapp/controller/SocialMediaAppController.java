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
        return socialMediaAppService.createUser(userID, userName);
    }

    @GetMapping("createNewPost")
    public ResponseEntity createNewPost(@RequestParam("userID") int userID, @RequestParam("postContent") String postContent) {
        return socialMediaAppService.createNewPost(userID, postContent);
    }

    @GetMapping("getNewsFeed")
    public ResponseEntity getNewsFeed(@RequestParam("userID") int userID) {
        return socialMediaAppService.getNewsFeed(userID);
    }

    @GetMapping("follow")
    public ResponseEntity follow(@RequestParam("followerId") int followerId, @RequestParam("followeeId") int followeeId) {
        return socialMediaAppService.follow(followerId, followeeId);
    }

    @GetMapping("unfollow")
    public ResponseEntity unFollow(@RequestParam("followerId") int followerId, @RequestParam("followeeId") int followeeId) {
        return socialMediaAppService.unFollow(followerId, followeeId);
    }

}
