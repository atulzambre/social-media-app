package com.socialmediaapp.serviceimpl;


import com.socialmediaapp.exception.CustomConflictException;
import com.socialmediaapp.exception.CustomNotFoundException;
import com.socialmediaapp.model.PostModel;
import com.socialmediaapp.util.InitialSetUpTestUtil;
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
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
public class SocialMediaAppServiceImplTestUtil extends InitialSetUpTestUtil {
    @InjectMocks
    private static SocialMediaAppServiceImpl socialMediaAppService;

    @InjectMocks
    private static UserOperationsServiceImpl userOperationsService;

    @BeforeClass
    public static void testDoInitialize_ShouldCreateUsersAndPosts() {
        InitialSetUpTestUtil.testDoInitialize_ShouldCreateUsersAndPosts();
    }

    @Test(expected = CustomNotFoundException.class)
    public void testCreateNewPost_IfUserNotPresent_ShouldThrowException() {
        ResponseEntity entityUser = socialMediaAppService.createNewPost("4", "4", "test post 4");
        assertEquals(HttpStatus.NOT_FOUND.value(), entityUser.getStatusCodeValue());
    }

    @Test
    public void testCreateNewPost_ShouldCreateNewPost() {
        userOperationsService.createUser("1", "test 1");
        ResponseEntity entityUser = socialMediaAppService.createNewPost("1", "1", "test post 1");
        assertEquals(HttpStatus.OK.value(), entityUser.getStatusCodeValue());
    }


    @Test
    public void testFollowUser_ShouldFollowUser() {
        ResponseEntity entityUser = socialMediaAppService.follow("5", "6");
        assertEquals(HttpStatus.OK.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = CustomNotFoundException.class)
    public void testFollowUser_IfUserNotPresent_ShouldThrowException() {
        ResponseEntity entityUser = socialMediaAppService.follow("7", "6");
        assertEquals(HttpStatus.NOT_FOUND.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = CustomConflictException.class)
    public void testFollowUser_IfAlreadyFollowingUser_ShouldThrowException() {
        socialMediaAppService.follow("5", "6");
        ResponseEntity entityUser = socialMediaAppService.follow("5", "6");
        assertEquals(HttpStatus.CONFLICT.value(), entityUser.getStatusCodeValue());
    }

    @Test
    public void testUnfollowUser_ShouldUnfollowUser() {
        socialMediaAppService.follow("8", "9");
        ResponseEntity entityUser = socialMediaAppService.unFollow("8", "9");
        assertEquals(HttpStatus.OK.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = CustomNotFoundException.class)
    public void testUnfollowUser_IfUserNotPresent_ShouldThrowException() {
        ResponseEntity entityUser = socialMediaAppService.unFollow("10", "6");
        assertEquals(HttpStatus.NOT_FOUND.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = CustomConflictException.class)
    public void testUnfollowUser_IfNotFollowingGivenUser_ShouldThrowException() {
        ResponseEntity entityUser = socialMediaAppService.unFollow("8", "9");
        assertEquals(HttpStatus.CONFLICT.value(), entityUser.getStatusCodeValue());
    }

    @Test
    public void testGetNewsFeed_ValidatePosts_ShouldReturnTop20LatestPosts() {
        Set<PostModel> setModels = Collections.unmodifiableSet((Set<PostModel>) Objects.requireNonNull(socialMediaAppService.getNewsFeed("11").getBody()));
        List<PostModel> postModels = new ArrayList<>(setModels);
        assertEquals(20, postModels.size());
    }

    @Test
    public void testGetNewsFeed_ValidateTop5Posts_ShouldValidateTop5Posts() {
        Set<PostModel> setModels = Collections.unmodifiableSet((Set<PostModel>) Objects.requireNonNull(socialMediaAppService.getNewsFeed("11").getBody()));
        List<PostModel> postModels = new ArrayList<>(setModels);
        assertEquals("test 13 post 14", postModels.get(0).getPostContent());
        assertEquals("test 13 post 13", postModels.get(1).getPostContent());
        assertEquals("test 11 post 4", postModels.get(2).getPostContent());
        assertEquals("test 13 post 12", postModels.get(3).getPostContent());
        assertEquals("test 13 post 11", postModels.get(4).getPostContent());
    }

    @Test
    public void testGetNewsFeed_ValidateLast5Posts_ShouldValidateLast5Posts() {
        Set<PostModel> setModels = Collections.unmodifiableSet((Set<PostModel>) Objects.requireNonNull(socialMediaAppService.getNewsFeed("11").getBody()));
        List<PostModel> postModels = new ArrayList<>(setModels);
        assertEquals("test 12 post 7", postModels.get(15).getPostContent());
        assertEquals("test 12 post 6", postModels.get(16).getPostContent());
        assertEquals("test 12 post 5", postModels.get(17).getPostContent());
        assertEquals("test 12 post 4", postModels.get(18).getPostContent());
        assertEquals("test 12 post 3", postModels.get(19).getPostContent());
    }

    @Test
    public void testGetNewsFeed_Validate10thPost_ShouldValidate10thPost() {
        Set<PostModel> setModels = Collections.unmodifiableSet((Set<PostModel>) Objects.requireNonNull(socialMediaAppService.getNewsFeed("11").getBody()));
        List<PostModel> postModels = new ArrayList<>(setModels);
        assertEquals("test 13 post 6", postModels.get(9).getPostContent());

    }

    @Test
    public void testGetNewsFeed_Validate15thPost_ShouldValidate15thPost() {
        Set<PostModel> setModels = Collections.unmodifiableSet((Set<PostModel>) Objects.requireNonNull(socialMediaAppService.getNewsFeed("11").getBody()));
        List<PostModel> postModels = new ArrayList<>(setModels);
        assertEquals("test 13 post 1", postModels.get(14).getPostContent());
    }

    @Test(expected = CustomNotFoundException.class)
    public void testGetNewsFeed_IfUserNotPresent_ShouldThrowException() {
        ResponseEntity entity = socialMediaAppService.getNewsFeed("20");
        assertEquals(HttpStatus.NOT_FOUND.value(), entity.getStatusCodeValue());
    }

    @Test
    public void testGetNewsFeed_ValidateLatestPost_ShouldReturnLatestPost() {
        userOperationsService.createUser("14", "test 14");
        socialMediaAppService.createNewPost("14", "1", "test 14 Latest Post");
        Set<PostModel> setModels = Collections.unmodifiableSet((Set<PostModel>) Objects.requireNonNull(socialMediaAppService.getNewsFeed("14").getBody()));
        List<PostModel> postModels = new ArrayList<>(setModels);
        assertEquals(1, postModels.size());
        assertEquals("test 14 Latest Post", postModels.get(0).getPostContent());

    }

    @Test
    public void testGetNewsFeed_IfNoPost_ShouldReturnBlankResponse() {
        userOperationsService.createUser("15", "test 15");
        Set<PostModel> setModels = Collections.unmodifiableSet((Set<PostModel>) Objects.requireNonNull(socialMediaAppService.getNewsFeed("15").getBody()));
        List<PostModel> postModels = new ArrayList<>(setModels);
        assertEquals(0, postModels.size());
    }

}
