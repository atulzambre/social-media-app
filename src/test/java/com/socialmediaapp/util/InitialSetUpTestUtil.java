package com.socialmediaapp.util;

import com.socialmediaapp.service.SocialMediaAppService;
import com.socialmediaapp.service.UserOperationsService;
import com.socialmediaapp.serviceimpl.SocialMediaAppServiceImpl;
import com.socialmediaapp.serviceimpl.UserOperationsServiceImpl;
import org.junit.BeforeClass;
import org.mockito.InjectMocks;

public class InitialSetUpTestUtil {
    @InjectMocks
    private static SocialMediaAppService socialMediaAppService;

    @InjectMocks
    private static UserOperationsService userOperationsService;

    //Creates Users, Posts and followto test.
    @BeforeClass
    public static void testDoInitialize_ShouldCreateUsersAndPosts() {
        socialMediaAppService = new SocialMediaAppServiceImpl();
        userOperationsService = new UserOperationsServiceImpl();
        userOperationsService.createUser("5", "test 5");
        userOperationsService.createUser("6", "test 6");

        userOperationsService.createUser("8", "test 8");
        userOperationsService.createUser("9", "test 9");

        userOperationsService.createUser("11", "test 11");
        userOperationsService.createUser("12", "test 12");
        userOperationsService.createUser("13", "test 13");
        socialMediaAppService.createNewPost("11", "1", "test 11 post 1");
        socialMediaAppService.createNewPost("11", "2", "test 11 post 2");
        socialMediaAppService.createNewPost("11", "3", "test 11 post 3");
        socialMediaAppService.createNewPost("11", "5", "test 11 post 5");
        socialMediaAppService.createNewPost("11", "6", "test 11 post 6");
        socialMediaAppService.createNewPost("11", "7", "test 11 post 7");
        socialMediaAppService.createNewPost("11", "8", "test 11 post 8");
        socialMediaAppService.createNewPost("11", "9", "test 11 post 9");
        socialMediaAppService.createNewPost("11", "10", "test 11 post 10");
        socialMediaAppService.createNewPost("12", "1", "test 12 post 1");
        socialMediaAppService.createNewPost("12", "2", "test 12 post 2");
        socialMediaAppService.createNewPost("12", "3", "test 12 post 3");
        socialMediaAppService.createNewPost("12", "4", "test 12 post 4");
        socialMediaAppService.createNewPost("12", "5", "test 12 post 5");
        socialMediaAppService.createNewPost("12", "6", "test 12 post 6");
        socialMediaAppService.createNewPost("12", "7", "test 12 post 7");
        socialMediaAppService.createNewPost("13", "1", "test 13 post 1");
        socialMediaAppService.createNewPost("13", "2", "test 13 post 2");
        socialMediaAppService.createNewPost("13", "3", "test 13 post 3");
        socialMediaAppService.createNewPost("13", "4", "test 13 post 4");
        socialMediaAppService.createNewPost("13", "5", "test 13 post 5");
        socialMediaAppService.createNewPost("13", "6", "test 13 post 6");
        socialMediaAppService.createNewPost("13", "7", "test 13 post 7");
        socialMediaAppService.createNewPost("13", "8", "test 13 post 8");
        socialMediaAppService.createNewPost("13", "9", "test 13 post 9");
        socialMediaAppService.createNewPost("13", "10", "test 13 post 10");
        socialMediaAppService.createNewPost("13", "11", "test 13 post 11");
        socialMediaAppService.createNewPost("13", "12", "test 13 post 12");
        socialMediaAppService.createNewPost("11", "4", "test 11 post 4");
        socialMediaAppService.createNewPost("13", "13", "test 13 post 13");
        socialMediaAppService.createNewPost("13", "14", "test 13 post 14");
        socialMediaAppService.follow("11", "12");
        socialMediaAppService.follow("11", "13");
    }


}
