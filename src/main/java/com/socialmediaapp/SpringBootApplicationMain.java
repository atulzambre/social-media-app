package com.socialmediaapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SocialMediaApp designed to Create Users, Retrieve All Users, Follow Users, Unfollow Users
 * and get the top 20 recent feeds from users and there following users.
 * SocialMediaController is the entry point to access the Http Methods.
 * Used Lambda/Stream API , Builder Design Pattern, Factory Design Pattern, Dependency Injection etc.
 * Returning the updated User Models with the successful response to better understand the working of the API.
 *
 * @author atulzambre
 */

@SpringBootApplication
class SpringBootApplicationMain {

    //    Main method starts spring boot application
    public static void main(String[] args) {

        SpringApplication.run(SpringBootApplicationMain.class, args);
    }
}
