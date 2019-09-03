package com.socialmediaapp.serviceimpl;

import com.socialmediaapp.exception.RequestParamException;
import com.socialmediaapp.exception.UserAlreadyExistsException;
import com.socialmediaapp.exception.UserDoesNotExistsException;
import com.socialmediaapp.model.ErrorMessageConstantModel;
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

        try {
            if (userID == 0 || userName == null || userName.isEmpty())
                throw new RequestParamException(ErrorMessageConstantModel.MISSING_REQUEST_PARAM);
            user.setUserID(userID);
            user.setUserName(userName);
            if (!userList.isEmpty() && userList.parallelStream().anyMatch(s -> s.getUserID() == user.getUserID())) {
                throw new UserAlreadyExistsException(ErrorMessageConstantModel.USER_ALREADY_EXISTS);
            } else {
                userList.add(user);
            }
        } catch (RequestParamException e) {
            throw new RequestParamException(e.getMessage());
        } catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException(e.getMessage());
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getAllUsers() {
        try {
            if (userList.isEmpty()) {
                throw new UserDoesNotExistsException(ErrorMessageConstantModel.USER_DOES_NOT_EXISTS);
            }
        } catch (UserDoesNotExistsException e) {
            System.out.println(e.getStackTrace());
            throw new UserDoesNotExistsException(e.getMessage());
        }
        return new ResponseEntity(userList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity createNewPost(int userID, String postContent) {
        PostModel newPost = new PostModel();

        UserModel user;
        try {
            if(userID==0||postContent.isEmpty())
                throw new RequestParamException(ErrorMessageConstantModel.MISSING_REQUEST_PARAM);
            newPost.setPostContent(postContent);
            user = userList.parallelStream().filter(s -> s.getUserID() == userID).findFirst().orElseThrow(() -> new UserDoesNotExistsException(ErrorMessageConstantModel.USER_DOES_NOT_EXISTS));
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


        } catch (RequestParamException e) {
            System.out.println(e.getStackTrace());
            throw new RequestParamException(e.getMessage());
        }
        catch(UserDoesNotExistsException e){
            throw new UserDoesNotExistsException(e.getMessage());
        }
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity follow(int followerId, int followeeId) {
        UserModel followerModel;
        UserModel followeeModel;
        try {
            if (followeeId == followerId||followeeId==0||followerId==0) {
                throw new RequestParamException(ErrorMessageConstantModel.CAN_NOT_FOLLOW_SELF);
            }
            followerModel = userList.parallelStream().filter(s -> s.getUserID() == followerId).findFirst().orElseThrow(() -> new UserDoesNotExistsException(ErrorMessageConstantModel.FOLLOWER_DOES_NOT_EXISTS));
            followeeModel = userList.parallelStream().filter(s -> s.getUserID() == followeeId).findFirst().orElseThrow(() -> new UserDoesNotExistsException(ErrorMessageConstantModel.FOLLOWEE_DOES_NOT_EXISTS));
            if (followerModel.getFollowee() != null && followerModel.getFollowee().parallelStream().anyMatch(s -> s.getFolloweeId() == followeeModel.getUserID()))
                throw new RequestParamException(ErrorMessageConstantModel.ALREADY_FOLLOWING);
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

        } catch (RequestParamException e) {
            System.out.println(e.getStackTrace());
            throw new RequestParamException(e.getMessage());
        }
        catch(UserDoesNotExistsException e){
            System.out.println(e.getStackTrace());
            throw new UserDoesNotExistsException(e.getMessage());
        }
        return new ResponseEntity(followerModel, HttpStatus.OK);
    }

    @Override
    public ResponseEntity unFollow(int followerId, int followeeId) {
        UserModel followerModel;
        UserModel followeeModel;

        try {
            if (followeeId == followerId) {
                throw new Exception("Can not unfollow self.");
            }
            followerModel = userList.parallelStream().filter(s -> s.getUserID() == followerId).findFirst().orElseThrow(() -> new Exception("No Such Follower Available"));
            followeeModel = userList.parallelStream().filter(s -> s.getUserID() == followeeId).findFirst().orElseThrow(() -> new Exception("No Such Followee Available"));

            if (followerModel.getFollowee().parallelStream().anyMatch(s -> s.getFolloweeId() == followeeModel.getUserID())) {
                followerModel.getFollowee().removeIf(s -> s.getFolloweeId() == followeeModel.getUserID());
            } else {
                throw new Exception("You are not following this user.");
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(followerModel, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getNewsFeed(int userID) {
        UserModel userModel;
        List<PostModel> newsFeedModel = new ArrayList<>();
        try {
            userModel = userList.parallelStream().filter(s -> s.getUserID() == userID).findFirst().orElseThrow(() -> new Exception("User Not Exists"));
            if (userModel.getPosts() != null)
                newsFeedModel = userModel.getPosts();


        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(newsFeedModel, HttpStatus.OK);
    }
}
