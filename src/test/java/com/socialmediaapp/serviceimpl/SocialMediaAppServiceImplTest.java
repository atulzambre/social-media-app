package com.socialmediaapp.serviceimpl;


import com.socialmediaapp.exception.CustomConflictException;
import com.socialmediaapp.exception.CustomNotFoundException;
import com.socialmediaapp.model.PostModel;
import com.socialmediaapp.service.SocialMediaAppService;
import com.socialmediaapp.service.UserOperationsService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Junit/Mockito test cases to unit test the API all the methods and business logic.
 *
 * @author atulzambre
 */
@RunWith(SpringRunner.class)
public class SocialMediaAppServiceImplTest {
    @InjectMocks
    private static SocialMediaAppService socialMediaAppService;

    @InjectMocks
    private static UserOperationsService userOperationsService;

    @BeforeClass
    public static void createUsersAndPosts_UsedForGetNewsFeed_ShouldCreateUsersAndPostsTest(){
        socialMediaAppService = new SocialMediaAppServiceImpl();
        userOperationsService= new UserOperationsServiceImpl();
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


    @Test(expected = CustomNotFoundException.class)
    public void createNewPost_UserNotPresent_ShouldThrowExceptionTest() {
        ResponseEntity entityUser = socialMediaAppService.createNewPost("4", "4", "test post 4");
        assertEquals(HttpStatus.NOT_FOUND.value(), entityUser.getStatusCodeValue());
    }

    @Test
    public void createNewPost_ShouldCreateNewPostTest() {
        userOperationsService.createUser("1", "test 1");
        ResponseEntity entityUser = socialMediaAppService.createNewPost("1", "1", "test post 1");
        assertEquals(HttpStatus.OK.value(), entityUser.getStatusCodeValue());
    }


    @Test
    public void followUser_ShouldFollowUserTest() {
        ResponseEntity entityUser = socialMediaAppService.follow("5", "6");
        assertEquals(HttpStatus.OK.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = CustomNotFoundException.class)
    public void followUser_UserNotPreset_ShouldThrowExceptionTest() {
        ResponseEntity entityUser = socialMediaAppService.follow("7", "6");
        assertEquals(HttpStatus.NOT_FOUND.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = CustomConflictException.class)
    public void followUser_AlreadyFollowingUser_ShouldThrowExceptionTest() {
        userOperationsService.createUser("5", "test 5");
        userOperationsService.createUser("6", "test 6");
        socialMediaAppService.unFollow("5", "6");
        ResponseEntity entityUser = socialMediaAppService.follow("5", "6");
        assertEquals(HttpStatus.CONFLICT.value(), entityUser.getStatusCodeValue());
    }

    @Test
    public void unfollowUser_ShouldUnfollowUserTest() {
        userOperationsService.createUser("8", "test 8");
        userOperationsService.createUser("9", "test 9");
        socialMediaAppService.follow("8", "9");
        ResponseEntity entityUser = socialMediaAppService.unFollow("8", "9");
        assertEquals(HttpStatus.OK.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = CustomNotFoundException.class)
    public void unfollowUser_UserNotPresent_ShouldThrowExceptionTest() {
        ResponseEntity entityUser = socialMediaAppService.unFollow("10", "6");
        assertEquals(HttpStatus.NOT_FOUND.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = CustomConflictException.class)
    public void unfollowUser_NotFollowingGivenUser_ShouldThrowExceptionTest() {
        ResponseEntity entityUser = socialMediaAppService.unFollow("8", "9");
        assertEquals(HttpStatus.CONFLICT.value(), entityUser.getStatusCodeValue());
    }

    @Test
    public void getNewsFeed_ValidateNumberOfPosts_ShouldReturn20PostsTest(){
        Set<PostModel> setModels = Collections.unmodifiableSet((Set<PostModel>) Objects.requireNonNull(socialMediaAppService.getNewsFeed("11").getBody()));
        List<PostModel> postModels = new ArrayList<>(setModels);
        assertEquals(20, postModels.size());
    }
    @Test
    public void getNewsFeed_ValidateTop5PostsFromUserAndFollowees_ShouldReturnLatestTop5PostsTest(){
        Set<PostModel> setModels = Collections.unmodifiableSet((Set<PostModel>) Objects.requireNonNull(socialMediaAppService.getNewsFeed("11").getBody()));
        List<PostModel> postModels = new ArrayList<>(setModels);
        assertEquals("test 13 post 14", postModels.get(0).getPostContent());
        assertEquals("test 13 post 13", postModels.get(1).getPostContent());
        assertEquals("test 11 post 4", postModels.get(2).getPostContent());
        assertEquals("test 13 post 12", postModels.get(3).getPostContent());
        assertEquals("test 13 post 11", postModels.get(4).getPostContent());
    }

    @Test
    public void getNewsFeed_ValidateLast5PostsFromUserAndFollowees_ShouldReturnLast5PostsTest(){
        Set<PostModel> setModels = Collections.unmodifiableSet((Set<PostModel>) Objects.requireNonNull(socialMediaAppService.getNewsFeed("11").getBody()));
        List<PostModel> postModels = new ArrayList<>(setModels);
        assertEquals("test 12 post 7", postModels.get(15).getPostContent());
        assertEquals("test 12 post 6", postModels.get(16).getPostContent());
        assertEquals("test 12 post 5", postModels.get(17).getPostContent());
        assertEquals("test 12 post 4", postModels.get(18).getPostContent());
        assertEquals("test 12 post 3", postModels.get(19).getPostContent());
    }

    @Test
    public void getNewsFeed_Validate10thPostFromUserOrFollowees_ShouldReturn10thPostTest(){
        Set<PostModel> setModels = Collections.unmodifiableSet((Set<PostModel>) Objects.requireNonNull(socialMediaAppService.getNewsFeed("11").getBody()));
        List<PostModel> postModels = new ArrayList<>(setModels);
        assertEquals("test 13 post 6", postModels.get(9).getPostContent());

    }

    @Test
    public void getNewsFeed_Validate15thPostFromUserOrFollowees_ShouldReturn15thPostTest(){
        Set<PostModel> setModels = Collections.unmodifiableSet((Set<PostModel>) Objects.requireNonNull(socialMediaAppService.getNewsFeed("11").getBody()));
        List<PostModel> postModels = new ArrayList<>(setModels);
        assertEquals("test 13 post 1", postModels.get(14).getPostContent());
    }

    @Test(expected = CustomNotFoundException.class)
    public void getNewsFeed_UserNotPresent_ShouldThrowExceptionTest() {
        ResponseEntity entity = socialMediaAppService.getNewsFeed("20");
        assertEquals(HttpStatus.NOT_FOUND.value(), entity.getStatusCodeValue());
    }

    @Test
    public void getNewsFeed_ValidateLatestPost_ShouldReturnLatestPostTest(){
        userOperationsService.createUser("14", "test 14");
        socialMediaAppService.createNewPost("14", "1", "test 14 Latest Post");
        Set<PostModel> setModels = Collections.unmodifiableSet((Set<PostModel>) Objects.requireNonNull(socialMediaAppService.getNewsFeed("14").getBody()));
        List<PostModel> postModels = new ArrayList<>(setModels);
        assertEquals(1, postModels.size());
        assertEquals("test 14 Latest Post", postModels.get(0).getPostContent());

    }

}
