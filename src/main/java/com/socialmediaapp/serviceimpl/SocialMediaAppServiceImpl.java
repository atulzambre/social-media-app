package com.socialmediaapp.serviceimpl;

import com.socialmediaapp.model.FollowModel;
import com.socialmediaapp.model.PostModel;
import com.socialmediaapp.model.UserModel;
import com.socialmediaapp.service.SocialMediaAppService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SocialMediaAppServiceImpl implements SocialMediaAppService {
    public static List<UserModel> userList = new ArrayList<UserModel>();
    private Integer postCount = 0;

    public ResponseEntity createUser(int userID, String userName) {
        UserModel user = new UserModel();
        user.setUserID(userID);
        user.setUserName(userName);
        try {
            if (!userList.isEmpty() && userList.parallelStream().anyMatch(s -> s.getUserID() == user.getUserID())) {
                throw new Exception("Duplicate User");
            } else {
                userList.add(user);
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getAllUsers() {
        try {
            if (userList.isEmpty()) {
                throw new Exception("No Users Found");
            }
        }catch(Exception e){
            System.out.println(e.getStackTrace());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(userList,HttpStatus.OK);
    }

    @Override
    public ResponseEntity createNewPost(int userID, String postContent) {
        PostModel newPost = new PostModel();
        newPost.setPostContent(postContent);
        UserModel user;
        try {
            user = userList.parallelStream().filter(s -> s.getUserID() == userID).findFirst().orElseThrow(() -> new Exception("No Such User Available"));
            postCount++;
            newPost.setPostID(postCount);
            newPost.setPostCreated(LocalDateTime.now());
            if (user.getPosts() == null) {
                List<PostModel> postModels = new ArrayList<>();
                postModels.add(newPost);
                user.setPosts(postModels);
            } else {
                user.getPosts().add(newPost);
            }


        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity follow(int followerId, int followeeId) {
        UserModel followerModel;
        UserModel followeeModel;
        try {
            if (followeeId == followerId) {
                throw new Exception("Can not follow self.");
            }
            followerModel = userList.parallelStream().filter(s -> s.getUserID() == followerId).findFirst().orElseThrow(() -> new Exception("No Such Follower Available"));
            followeeModel = userList.parallelStream().filter(s -> s.getUserID() == followeeId).findFirst().orElseThrow(() -> new Exception("No Such Followee Available"));
            if(followerModel.getFollowee()!=null&&followerModel.getFollowee().parallelStream().anyMatch(s->s.getFolloweeId()==followeeModel.getUserID()))
                throw new Exception("Already Following this user");
            FollowModel followModel = new FollowModel();
            followModel.setFolloweeId(followeeModel.getUserID());
            followModel.setFolloweeName(followeeModel.getUserName());
            if (followerModel.getFollowee() == null) {
                List<FollowModel> followeeModels = new ArrayList<>();
                followeeModels.add(followModel);
                followerModel.setFollowee(followeeModels);
            } else {
                followerModel.getFollowee().add(followModel);
            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(followerModel, HttpStatus.OK);
    }

    @Override
    public ResponseEntity unFollow(int followerId, int followeeId) {
        UserModel followerModel;
        UserModel followeeModel;

        try{
            if (followeeId == followerId) {
                throw new Exception("Can not unfollow self.");
            }
            followerModel = userList.parallelStream().filter(s -> s.getUserID() == followerId).findFirst().orElseThrow(() -> new Exception("No Such Follower Available"));
            followeeModel = userList.parallelStream().filter(s -> s.getUserID() == followeeId).findFirst().orElseThrow(() -> new Exception("No Such Followee Available"));

            if(followerModel.getFollowee().parallelStream().anyMatch(s->s.getFolloweeId()==followeeModel.getUserID())){
                followerModel.getFollowee().removeIf(s->s.getFolloweeId()==followeeModel.getUserID());
            }
            else{
                throw new Exception("You are not following this user.");
            }
        }
        catch(Exception e){
            System.out.println(e.getStackTrace());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(followerModel,HttpStatus.OK);
    }

    @Override
    public ResponseEntity getNewsFeed(int userID) {
        UserModel userModel;
        List<PostModel> newsFeedModel=new ArrayList<>();
            try{
                userModel=userList.parallelStream().filter(s->s.getUserID()==userID).findFirst().orElseThrow(()->new Exception("User Not Exists"));
                if(userModel.getPosts()!=null)
                newsFeedModel=userModel.getPosts();


            }
            catch(Exception e){
                System.out.println(e.getStackTrace());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(newsFeedModel,HttpStatus.OK);
    }
}
