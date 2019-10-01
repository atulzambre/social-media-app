package com.socialmediaapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialmediaapp.model.CreatePostRequestModel;
import com.socialmediaapp.model.FollowUnFollowRequestModel;
import com.socialmediaapp.model.PostModel;
import com.socialmediaapp.model.UserModel;
import com.socialmediaapp.service.SocialMediaAppService;
import com.socialmediaapp.service.UserOperationsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Junit/Mockito test cases to unit test the API all the methods and business logics.
 *
 * @author atulzambre
 */
@RunWith(SpringRunner.class)
@WebMvcTest(SocialMediaAppController.class)
public class SocialMediaAppControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SocialMediaAppService socialMediaAppService;

    @MockBean
    private UserOperationsService userOperationsService;

    @Test
    public void controllerGetAllUsers_ShouldReturnAllTheUsersFromCollectionTest() throws Exception {
        UserModel userModel = new UserModel();
        userModel.setUserId("1");
        userModel.setUserName("test");
        ResponseEntity<UserModel> responseEntity;
        responseEntity = new ResponseEntity<>(userModel, HttpStatus.OK);
        Mockito.when(userOperationsService.getAllUsers()).thenReturn(responseEntity);
        mvc.perform(MockMvcRequestBuilders.get("/getAllUsers").accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
    }

    @Test
    public void controllerCreateUser_ShouldCreateNewUserTest() throws Exception {
        UserModel userModel = new UserModel();
        userModel.setUserId("1");
        userModel.setUserName("test");
        ResponseEntity<UserModel> responseEntity;
        responseEntity = new ResponseEntity<>(userModel, HttpStatus.OK);
        Mockito.when(userOperationsService.createUser("1", "test")).thenReturn(responseEntity);
        mvc.perform(post("/createUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userModel)))
                .andExpect(status().isOk());
    }


    @Test
    public void controllerCreateNewPost_ShouldCreateNewPostTest() throws Exception {
        UserModel userModel = new UserModel();
        userModel.setUserId("1");
        userModel.setUserName("test");
        PostModel postModel = new PostModel();
        postModel.setPostId("1");
        postModel.setPostContent("test");
        userModel.setPosts(Collections.singleton(postModel));
        CreatePostRequestModel createPostRequestModel = new CreatePostRequestModel();
        createPostRequestModel.setUserId("1");
        createPostRequestModel.setPostId("1");
        createPostRequestModel.setPostContent("Test");
        ResponseEntity<UserModel> responseEntity;
        responseEntity = new ResponseEntity<>(userModel, HttpStatus.OK);
        Mockito.when(socialMediaAppService.createNewPost("1", "1", "test")).thenReturn(responseEntity);
        mvc.perform(post("/createNewPost")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createPostRequestModel)))
                .andExpect(status().isOk());
    }

    @Test
    public void controllerGetNewsFeed_ShouldReturnPostsOfUserAndFolloweesTest() throws Exception {

        PostModel postModel = new PostModel();
        postModel.setPostId("1");
        postModel.setPostContent("test");
        List<PostModel> postModelList = Collections.singletonList(postModel);
        ResponseEntity<List<PostModel>> responseEntity;
        responseEntity = new ResponseEntity<>(postModelList, HttpStatus.OK);
        Mockito.when(socialMediaAppService.getNewsFeed("1")).thenReturn(responseEntity);
        mvc.perform(MockMvcRequestBuilders.get("/getNewsFeed?userId=1").accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
    }

    @Test
    public void controllerFollowUser_ShouldFollowUserTest() throws Exception {
        UserModel userModel = new UserModel();
        userModel.setUserId("1");
        userModel.setUserName("test");
        FollowUnFollowRequestModel followRequestModel = new FollowUnFollowRequestModel();
        followRequestModel.setFollowerId("1");
        followRequestModel.setFolloweeId("2");
        ResponseEntity<UserModel> responseEntity;
        responseEntity = new ResponseEntity<>(userModel, HttpStatus.OK);
        Mockito.when(socialMediaAppService.follow("1", "2")).thenReturn(responseEntity);
        mvc.perform(put("/follow")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(followRequestModel)))
                .andExpect(status().isOk());
    }

    @Test
    public void controllerUnFollow_ShouldUnfollowUserTest() throws Exception {
        UserModel userModel = new UserModel();
        userModel.setUserId("1");
        userModel.setUserName("test");
        FollowUnFollowRequestModel unfollowRequestModel = new FollowUnFollowRequestModel();
        unfollowRequestModel.setFollowerId("1");
        unfollowRequestModel.setFolloweeId("2");
        ResponseEntity<UserModel> responseEntity;
        responseEntity = new ResponseEntity<>(userModel, HttpStatus.OK);
        Mockito.when(socialMediaAppService.follow("1", "2")).thenReturn(responseEntity);
        mvc.perform(put("/unfollow")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(unfollowRequestModel)))
                .andExpect(status().isOk());
    }

}
