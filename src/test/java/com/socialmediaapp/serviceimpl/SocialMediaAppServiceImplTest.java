package com.socialmediaapp.serviceimpl;


import com.socialmediaapp.exception.CustomConflictException;
import com.socialmediaapp.exception.CustomNotFoundException;
import com.socialmediaapp.model.PostModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

/**
 * Junit/Mockito test cases to unit test the API all the methods and business logics.
 *
 * @author atulzambre
 */
@RunWith(SpringRunner.class)
public class SocialMediaAppServiceImplTest {
    @InjectMocks
    private SocialMediaAppServiceImpl socialMediaAppService;

    @InjectMocks
    private UserOperationsServiceImpl userOperationsService;


    @Test(expected = CustomNotFoundException.class)
    public void createNewPostNotFoundUserTest() {
        ResponseEntity entityUser = socialMediaAppService.createNewPost("4", "4", "test post 4");
        assertEquals(HttpStatus.NOT_FOUND.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = CustomNotFoundException.class)
    public void createNewPostNotFoundTest() {
        ResponseEntity entityUser = socialMediaAppService.createNewPost("4", "4", "test post 4");
        assertEquals(HttpStatus.NOT_FOUND.value(), entityUser.getStatusCodeValue());
    }

    @Test
    public void createNewPostTest() {
        userOperationsService.createUser("1", "test 1");
        ResponseEntity entityUser = socialMediaAppService.createNewPost("1", "1", "test post 1");
        assertEquals(HttpStatus.OK.value(), entityUser.getStatusCodeValue());
    }

    @Test
    public void followTest() {
        userOperationsService.createUser("5", "test 5");
        userOperationsService.createUser("6", "test 6");
        ResponseEntity entityUser = socialMediaAppService.follow("5", "6");
        assertEquals(HttpStatus.OK.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = CustomNotFoundException.class)
    public void followUserNotFoundTest() {
        ResponseEntity entityUser = socialMediaAppService.follow("7", "6");
        assertEquals(HttpStatus.NOT_FOUND.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = CustomConflictException.class)
    public void followAlreadyFoundTest() {
        ResponseEntity entityUser = socialMediaAppService.follow("5", "6");
        assertEquals(HttpStatus.CONFLICT.value(), entityUser.getStatusCodeValue());
    }

    @Test
    public void unfollowTest() {
        userOperationsService.createUser("8", "test 8");
        userOperationsService.createUser("9", "test 9");
        socialMediaAppService.follow("8", "9");
        ResponseEntity entityUser = socialMediaAppService.unFollow("8", "9");
        assertEquals(HttpStatus.OK.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = CustomNotFoundException.class)
    public void unfollowUserNotFoundTest() {
        ResponseEntity entityUser = socialMediaAppService.unFollow("10", "6");
        assertEquals(HttpStatus.NOT_FOUND.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = CustomConflictException.class)
    public void unfollowAlreadyTest() {
        ResponseEntity entityUser = socialMediaAppService.unFollow("8", "9");
        assertEquals(HttpStatus.CONFLICT.value(), entityUser.getStatusCodeValue());
    }

    @Test
    public void getNewFeedTest() {

        userOperationsService.createUser("11", "test 11");
        userOperationsService.createUser("12", "test 12");
        userOperationsService.createUser("13", "test 13");
        socialMediaAppService.createNewPost("11", "1", "test 1");
        socialMediaAppService.createNewPost("11", "2", "test 2");
        socialMediaAppService.createNewPost("11", "3", "test 3");
        socialMediaAppService.createNewPost("11", "4", "test 4");
        socialMediaAppService.createNewPost("11", "5", "test 5");
        socialMediaAppService.createNewPost("11", "6", "test 6");
        socialMediaAppService.createNewPost("11", "7", "test 7");
        socialMediaAppService.createNewPost("11", "8", "test 8");
        socialMediaAppService.createNewPost("11", "9", "test 9");
        socialMediaAppService.createNewPost("11", "10", "test 10");
        socialMediaAppService.createNewPost("12", "1", "test 1");
        socialMediaAppService.createNewPost("12", "2", "test 2");
        socialMediaAppService.createNewPost("12", "3", "test 3");
        socialMediaAppService.createNewPost("12", "4", "test 4");
        socialMediaAppService.createNewPost("12", "5", "test 5");
        socialMediaAppService.createNewPost("12", "6", "test 6");
        socialMediaAppService.createNewPost("12", "7", "test 7");
        socialMediaAppService.createNewPost("13", "1", "test 1");
        socialMediaAppService.createNewPost("13", "2", "test 2");
        socialMediaAppService.createNewPost("13", "3", "test 3");
        socialMediaAppService.createNewPost("13", "4", "test 4");
        socialMediaAppService.createNewPost("13", "5", "test 5");
        socialMediaAppService.createNewPost("13", "6", "test 6");
        socialMediaAppService.createNewPost("13", "7", "test 7");
        socialMediaAppService.follow("11", "12");
        socialMediaAppService.follow("11", "13");
        ResponseEntity<List<PostModel>> postModels;
        postModels = socialMediaAppService.getNewsFeed("11");
        assertEquals(20, Objects.requireNonNull(postModels.getBody()).size());
    }


}
