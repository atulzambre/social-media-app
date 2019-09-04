package com.socialmediaapp.serviceimpl;


import com.socialmediaapp.exception.PostsNotAvailableException;
import com.socialmediaapp.exception.RequestParamException;
import com.socialmediaapp.exception.UserAlreadyExistsException;
import com.socialmediaapp.exception.UserDoesNotExistsException;
import com.socialmediaapp.model.UserModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SocialMediaAppServiceImplTest {
    @InjectMocks
    private SocialMediaAppServiceImpl socialMediaAppService;


    @Test
    public void createUsersTest() {
        ResponseEntity entityUser = socialMediaAppService.createUser(1, "atul");
        UserModel user = (UserModel) entityUser.getBody();
        Assert.assertEquals(HttpStatus.OK.value(), entityUser.getStatusCodeValue());
        Assert.assertEquals("atul", user.getUserName());
    }

    @Test(expected = RequestParamException.class)
    public void createUsersRequestParamExceptionTest() {
        ResponseEntity entityUser = socialMediaAppService.createUser(-1, "atul");
        Assert.assertEquals(HttpStatus.BAD_REQUEST, entityUser.getStatusCodeValue());
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void createUsersUserAlreadyExistsExceptionTest() {
        ResponseEntity entityUser = socialMediaAppService.createUser(1, "atul");
        Assert.assertEquals(HttpStatus.CONFLICT, entityUser.getStatusCodeValue());
    }

    @Test
    public void getAllUsersTest() {
        socialMediaAppService.createUser(2, "atul");
        ResponseEntity entityUser = socialMediaAppService.getAllUsers();
        Assert.assertEquals(HttpStatus.OK.value(), entityUser.getStatusCodeValue());
    }

    @Test
    public void createNewPostTest() {
        socialMediaAppService.createUser(3, "atul");
        ResponseEntity entityUser = socialMediaAppService.createNewPost(3, "Comment Post");
        Assert.assertEquals(HttpStatus.OK.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = RequestParamException.class)
    public void createNewPostRequestParamExceptionTest() {

        ResponseEntity entityUser = socialMediaAppService.createNewPost(-3, "Comment Post");
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = UserDoesNotExistsException.class)
    public void createNewPostUserDoesNotExistsExceptionTest() {
        ResponseEntity entityUser = socialMediaAppService.createNewPost(4, "Comment Post");
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), entityUser.getStatusCodeValue());
    }

    @Test
    public void createNewPostElseExceptionTest() {
        socialMediaAppService.createUser(4, "atul");
        socialMediaAppService.createNewPost(4, "Comment Post");
        ResponseEntity entityUser = socialMediaAppService.createNewPost(4, "Comment Post");
        Assert.assertEquals(HttpStatus.OK.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = RequestParamException.class)
    public void followRequestParamExceptionTest() {

        ResponseEntity entityUser = socialMediaAppService.follow(1, 1);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = RequestParamException.class)
    public void followRequestParamExceptionValueTest() {

        ResponseEntity entityUser = socialMediaAppService.follow(0, 1);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = UserDoesNotExistsException.class)
    public void followUserDoesNotExistsExceptionFollowerTest() {

        ResponseEntity entityUser = socialMediaAppService.follow(7, 2);
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = UserDoesNotExistsException.class)
    public void followUserDoesNotExistsExceptionFolloweeTest() {

        ResponseEntity entityUser = socialMediaAppService.follow(1, 7);
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = RequestParamException.class)
    public void followRequestParamExceptionFolloweeTest() {
        socialMediaAppService.follow(1, 2);
        ResponseEntity entityUser = socialMediaAppService.follow(1, 2);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), entityUser.getStatusCodeValue());
    }

    @Test
    public void unFollowTest() {
        ResponseEntity entityUser = socialMediaAppService.unFollow(1, 2);
        Assert.assertEquals(HttpStatus.OK.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = RequestParamException.class)
    public void unFollowRequestParamExceptionTest() {
        ResponseEntity entityUser = socialMediaAppService.unFollow(1, 1);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = RequestParamException.class)
    public void unFollowRequestParamExceptionFailTest() {
        ResponseEntity entityUser = socialMediaAppService.unFollow(-1, 1);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = UserDoesNotExistsException.class)
    public void unFollowUserDoesNotExistsExceptionTest() {
        ResponseEntity entityUser = socialMediaAppService.unFollow(1, 4);
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), entityUser.getStatusCodeValue());
    }

    @Test
    public void getNewsFeedTest() {
        socialMediaAppService.createUser(5, "test");
        socialMediaAppService.createNewPost(5, "test post");
        socialMediaAppService.createUser(6, "test");
        socialMediaAppService.createNewPost(6, "test post");
        socialMediaAppService.follow(5, 6);
        ResponseEntity entityUser = socialMediaAppService.getNewsFeed(5);
        Assert.assertEquals(HttpStatus.OK.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = RequestParamException.class)
    public void getNewsFeedRequestParamExceptionTest() {

        ResponseEntity entityUser = socialMediaAppService.getNewsFeed(-5);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = UserDoesNotExistsException.class)
    public void getNewsFeedUserDoesNotExistsExceptionTest() {

        ResponseEntity entityUser = socialMediaAppService.getNewsFeed(8);
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), entityUser.getStatusCodeValue());
    }

    @Test(expected = PostsNotAvailableException.class)
    public void getNewsFeedPostsNotAvailableExceptionTest() {
        socialMediaAppService.createUser(9,"test");
        ResponseEntity entityUser = socialMediaAppService.getNewsFeed(9);
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), entityUser.getStatusCodeValue());
    }


}
