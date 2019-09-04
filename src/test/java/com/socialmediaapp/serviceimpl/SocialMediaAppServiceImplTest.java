package com.socialmediaapp.serviceimpl;


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
    SocialMediaAppServiceImpl socialMediaAppService;


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


}
