package com.socialmediaapp.controller;

import com.socialmediaapp.model.PostModel;
import com.socialmediaapp.model.UserModel;
import com.socialmediaapp.service.SocialMediaAppService;
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

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SocialMediaAppController.class)
public class SocialMediaAppControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SocialMediaAppService socialMediaAppService;

    @Test
    public void getAllUsersTest() throws Exception {
        UserModel userModel=new UserModel();
        userModel.setUserID(1);
        userModel.setUserName("test");
        Mockito.when(socialMediaAppService.getAllUsers()).thenReturn(new ResponseEntity(userModel, HttpStatus.OK));
        mvc.perform(MockMvcRequestBuilders.get("/getAllUsers").accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
    }

    @Test
    public void createUserTest() throws Exception {
        UserModel userModel=new UserModel();
        userModel.setUserID(1);
        userModel.setUserName("test");
        Mockito.when(socialMediaAppService.createUser(1,"test")).thenReturn(new ResponseEntity(userModel, HttpStatus.OK));
        mvc.perform(MockMvcRequestBuilders.get("/createUser?userID=1&userName=test").accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
    }

    @Test
    public void createNewPostTest() throws Exception {
        UserModel userModel=new UserModel();
        userModel.setUserID(1);
        userModel.setUserName("test");
        PostModel postModel=new PostModel();
        postModel.setPostID(1);
        postModel.setPostContent("test");
        userModel.setPosts(Arrays.asList(postModel));
        Mockito.when(socialMediaAppService.createNewPost(1,"test")).thenReturn(new ResponseEntity(userModel, HttpStatus.OK));
        mvc.perform(MockMvcRequestBuilders.get("/createNewPost?userID=1&postContent=test").accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
    }

//    @Test
//    public void createNewPostTest() throws Exception {
//        UserModel userModel=new UserModel();
//        userModel.setUserID(1);
//        userModel.setUserName("test");
//        PostModel postModel=new PostModel();
//        postModel.setPostID(1);
//        postModel.setPostContent("test");
//        userModel.setPosts(Arrays.asList(postModel));
//        Mockito.when(socialMediaAppService.createNewPost(1,"test")).thenReturn(new ResponseEntity(userModel, HttpStatus.OK));
//        mvc.perform(MockMvcRequestBuilders.get("/createNewPost?userID=1&postContent=test").accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
//    }

}
