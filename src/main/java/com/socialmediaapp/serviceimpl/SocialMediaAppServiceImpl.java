package com.socialmediaapp.serviceimpl;

import com.socialmediaapp.exception.PostsNotAvailableException;
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SocialMediaAppServiceImpl implements SocialMediaAppService {
    public static List<UserModel> userList = new ArrayList<UserModel>(5);

    public ResponseEntity createUser(int userID, String userName) {

        UserModel user = new UserModel();

        try {
            if (userID <= 0 || Objects.isNull(userName) || userName.isEmpty())
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
    public ResponseEntity createNewPost(int userID,int postID, String postContent) {
        PostModel newPost = new PostModel();

        UserModel user;
        try {
            if (userID <= 0 ||postID<=0 || postContent.isEmpty())
                throw new RequestParamException(ErrorMessageConstantModel.MISSING_REQUEST_PARAM);

            newPost.setPostContent(postContent);
            user = userList.parallelStream().filter(s -> s.getUserID() == userID).findFirst().orElseThrow(() -> new UserDoesNotExistsException(ErrorMessageConstantModel.USER_DOES_NOT_EXISTS));
            newPost.setPostID(postID);
            newPost.setPostCreated(LocalDateTime.now());
            if (Objects.isNull(user.getPosts())) {
                List<PostModel> postModels = new ArrayList<>();
                postModels.add(newPost);
                user.setPosts(postModels);
            } else {
                if(user.getPosts().parallelStream().anyMatch(s->s.getPostID()==postID))
                    throw new RequestParamException(ErrorMessageConstantModel.POST_ALREADY_EXISTS);
                user.getPosts().add(newPost);
            }


        } catch (RequestParamException e) {
            System.out.println(e.getStackTrace());
            throw new RequestParamException(e.getMessage());
        } catch (UserDoesNotExistsException e) {
            throw new UserDoesNotExistsException(e.getMessage());
        }
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity follow(int followerId, int followeeId) {
        UserModel followerModel;
        UserModel followeeModel;
        try {
            if (followeeId == followerId) {
                throw new RequestParamException(ErrorMessageConstantModel.CAN_NOT_FOLLOW_SELF);
            } else if (followeeId <= 0 || followerId <= 0) {
                throw new RequestParamException(ErrorMessageConstantModel.MISSING_REQUEST_PARAM);
            }
            followerModel = userList.parallelStream().filter(s -> s.getUserID() == followerId).findFirst().orElseThrow(() -> new UserDoesNotExistsException(ErrorMessageConstantModel.FOLLOWER_DOES_NOT_EXISTS));
            followeeModel = userList.parallelStream().filter(s -> s.getUserID() == followeeId).findFirst().orElseThrow(() -> new UserDoesNotExistsException(ErrorMessageConstantModel.FOLLOWEE_DOES_NOT_EXISTS));
            if (Objects.nonNull(followerModel.getFollowee()) && followerModel.getFollowee().parallelStream().anyMatch(s -> s.getFolloweeId() == followeeModel.getUserID()))
                throw new RequestParamException(ErrorMessageConstantModel.ALREADY_FOLLOWING);
            FollowModel followModel = new FollowModel();
            followModel.setFolloweeId(followeeModel.getUserID());
            followModel.setFolloweeName(followeeModel.getUserName());
            if (Objects.isNull(followerModel.getFollowee())) {
                List<FollowModel> followeeModels = new ArrayList<>();
                followeeModels.add(followModel);
                followerModel.setFollowee(followeeModels);
            } else {
                followerModel.getFollowee().add(followModel);
            }

        } catch (RequestParamException e) {
            System.out.println(e.getStackTrace());
            throw new RequestParamException(e.getMessage());
        } catch (UserDoesNotExistsException e) {
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
                throw new RequestParamException(ErrorMessageConstantModel.CAN_NOT_UNFOLLOW_SELF);
            } else if (followeeId <= 0 || followerId <= 0) {
                throw new RequestParamException(ErrorMessageConstantModel.MISSING_REQUEST_PARAM);
            }

            followerModel = userList.parallelStream().filter(s -> s.getUserID() == followerId).findFirst().orElseThrow(() -> new UserDoesNotExistsException(ErrorMessageConstantModel.FOLLOWER_DOES_NOT_EXISTS));
            followeeModel = userList.parallelStream().filter(s -> s.getUserID() == followeeId).findFirst().orElseThrow(() -> new UserDoesNotExistsException(ErrorMessageConstantModel.FOLLOWEE_DOES_NOT_EXISTS));

            if (followerModel.getFollowee().parallelStream().anyMatch(s -> s.getFolloweeId() == followeeModel.getUserID())) {
                followerModel.getFollowee().removeIf(s -> s.getFolloweeId() == followeeModel.getUserID());
            } else {
                throw new RequestParamException(ErrorMessageConstantModel.NOT_FOLLOWING_USER);
            }
        } catch (RequestParamException e) {
            System.out.println(e.getStackTrace());
            throw new RequestParamException(e.getMessage());
        } catch (UserDoesNotExistsException e) {
            System.out.println(e.getStackTrace());
            throw new UserDoesNotExistsException(e.getMessage());
        }
        return new ResponseEntity(followerModel, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getNewsFeed(int userID) {
        UserModel userModel=null;
        List<UserModel> checkList=new ArrayList<>(userList);
        List<PostModel> newsFeedModel = null;
        try {
            if (userID <= 0) {
                throw new RequestParamException(ErrorMessageConstantModel.MISSING_REQUEST_PARAM);
            }
            userModel = checkList.parallelStream().filter(s -> s.getUserID() == userID).findFirst().orElseThrow(() -> new UserDoesNotExistsException(ErrorMessageConstantModel.USER_DOES_NOT_EXISTS));
            if (Objects.nonNull(userModel.getPosts()))
                newsFeedModel = new ArrayList<>(userModel.getPosts());

            if (Objects.nonNull(userModel.getFollowee())) {
                for (FollowModel followModel : userModel.getFollowee()) {
                    for (UserModel userModel1 : checkList) {
                        if (followModel.getFolloweeId() == userModel1.getUserID() && Objects.nonNull(userModel1.getPosts())) {
                            newsFeedModel.addAll(userModel1.getPosts());
                        }
                    }


                }
            }
            if (Objects.isNull(newsFeedModel)||newsFeedModel.isEmpty()) {
                throw new PostsNotAvailableException(ErrorMessageConstantModel.POSTS_NOT_AVAILABLE);
            }
            Collections.sort(newsFeedModel);


        } catch (RequestParamException e) {
            System.out.println(e.getStackTrace());
            throw new RequestParamException(e.getMessage());
        } catch (UserDoesNotExistsException e) {
            System.out.println(e.getStackTrace());
            throw new UserDoesNotExistsException(e.getMessage());
        } catch (PostsNotAvailableException e) {
            System.out.println(e.getStackTrace());
            throw new PostsNotAvailableException(e.getMessage());
        }

        return new ResponseEntity(newsFeedModel.stream().limit(20).collect(Collectors.toList()), HttpStatus.OK);
    }
}
