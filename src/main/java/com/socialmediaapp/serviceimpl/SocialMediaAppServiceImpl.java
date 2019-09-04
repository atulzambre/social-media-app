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
import java.util.*;
import java.util.stream.Collectors;

/**
 * SocialMediaAppServiceImpl is implemented the methods from SocialMediaAppService and developed business logic for the problem statement.
 * Returning the updated User Models with the successful response to better understand the working of the API.
 * Used Lambda/Stream API , Builder Design Pattern, Factory Design Pattern, Dependency Injection etc.
 *
 * @author atulzambre
 */
@Service
public class SocialMediaAppServiceImpl implements SocialMediaAppService {
    //    Created static final userList collection to store the User related data.
    private static final List<UserModel> userList = new ArrayList<>(5);


    /**
     * createUser method stores the userID and userName in the collection. Initially it checks for the conditions.
     * for every User userID must me unique and userName should not me empty.
     * if the conditions are not satisfied then it throw exceptions which is then catch in CustomExceptionHandler.
     *
     * @param userID
     * @param userName
     * @return Successfully stored user object wrapping in ResponseEntity.
     */
    public ResponseEntity createUser(int userID, String userName) {

        UserModel user = new UserModel();

        try {
            //check - if null and empty request params.
            if (userID <= 0 || Objects.isNull(userName) || userName.isEmpty())
                throw new RequestParamException(ErrorMessageConstantModel.MISSING_REQUEST_PARAM);
            user.setUserID(userID);
            user.setUserName(userName);

            //check - if user already present in the collection if not then save the user.
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

        //Wrap the response user object in ResponseEntity.
        ResponseEntity<UserModel> response;
        response = new ResponseEntity<>(user, HttpStatus.OK);
        return response;
    }

    /**
     * getAllUsers method retrieves all the available Users in the collection. Initially it checks for the collection emptiness.
     * if the collection is empty then it throws exceptions which is then catch in CustomExceptionHandler.
     *
     * @return List of Users available in the collection.
     */
    @Override
    public ResponseEntity getAllUsers() {
        try {
            //check if the collection is empty means currently there is no user present.
            if (userList.isEmpty()) {
                throw new UserDoesNotExistsException(ErrorMessageConstantModel.USER_DOES_NOT_EXISTS);
            }
        } catch (UserDoesNotExistsException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new UserDoesNotExistsException(e.getMessage());
        }
        //Wrap the response user objects in ResponseEntity.
        ResponseEntity<List<UserModel>> response;
        response = new ResponseEntity<>(userList, HttpStatus.OK);
        return response;
    }

    /**
     * createNewPost method stores the new posts for the user. Initially it checks for the conditions.
     * postID must be unique to store the post Also the User for the userID must be exists.
     * Posts are also containing the creation date and time.
     * if the conditions are not satisfied then it throw exceptions which is then catch in CustomExceptionHandler.
     *
     * @param userID
     * @param postID
     * @param postContent
     * @return User with all its posts.
     */
    @Override
    public ResponseEntity createNewPost(int userID, int postID, String postContent) {
        PostModel newPost = new PostModel();

        UserModel user;
        try {
            //check - if null and empty request params.
            if (userID <= 0 || postID <= 0 || postContent.isEmpty())
                throw new RequestParamException(ErrorMessageConstantModel.MISSING_REQUEST_PARAM);

            newPost.setPostContent(postContent);
            //check -  the User with userID is present in the collection and throws UserDoesNotExistsException if false.
            user = userList.parallelStream().filter(s -> s.getUserID() == userID).findFirst().orElseThrow(() -> new UserDoesNotExistsException(ErrorMessageConstantModel.USER_DOES_NOT_EXISTS));
            newPost.setPostID(postID);
            newPost.setPostCreated(LocalDateTime.now());
            //check - if the User is having any posts if not then create new post
            if (Objects.isNull(user.getPosts())) {
                List<PostModel> postModels = new ArrayList<>();
                postModels.add(newPost);
                user.setPosts(postModels);
            } else {
                //check - if the postID is already exists.
                if (user.getPosts().parallelStream().anyMatch(s -> s.getPostID() == postID))
                    throw new RequestParamException(ErrorMessageConstantModel.POST_ALREADY_EXISTS);
                user.getPosts().add(newPost);
            }


        } catch (RequestParamException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new RequestParamException(e.getMessage());
        } catch (UserDoesNotExistsException e) {
            throw new UserDoesNotExistsException(e.getMessage());
        }

        //Wrap the response User with all the posts objects in ResponseEntity.
        ResponseEntity<UserModel> response;
        response = new ResponseEntity<>(user, HttpStatus.OK);
        return response;
    }


    /**
     * follow method stores information about one user following other to get the feeds. Initially it checks for the conditions.
     * if the user tried to follow itself which is not allowed.
     * followerId and followeeId must be unique to store the follow information Also the User for both of them must be exists.
     * Also check for follower already following followee.
     * if the conditions are not satisfied then it throw exceptions which is then catch in CustomExceptionHandler.
     *
     * @param followerId
     * @param followeeId
     * @return User with all the followee information.
     */
    @Override
    public ResponseEntity follow(int followerId, int followeeId) {
        UserModel followerModel;
        UserModel followeeModel;
        try {
            //check - if user tried to follow itself
            if (followeeId == followerId) {
                throw new RequestParamException(ErrorMessageConstantModel.CAN_NOT_FOLLOW_SELF);
            }
            //check - if null and empty request params.
            else if (followeeId <= 0 || followerId <= 0) {
                throw new RequestParamException(ErrorMessageConstantModel.MISSING_REQUEST_PARAM);
            }

            //check - if follower and followee must exists in the collection.
            followerModel = userList.parallelStream().filter(s -> s.getUserID() == followerId).findFirst().orElseThrow(() -> new UserDoesNotExistsException(ErrorMessageConstantModel.FOLLOWER_DOES_NOT_EXISTS));
            followeeModel = userList.parallelStream().filter(s -> s.getUserID() == followeeId).findFirst().orElseThrow(() -> new UserDoesNotExistsException(ErrorMessageConstantModel.FOLLOWEE_DOES_NOT_EXISTS));
            //check - if user already following the followee.
            if (Objects.nonNull(followerModel.getFollowee()) && followerModel.getFollowee().parallelStream().anyMatch(s -> s.getFolloweeId() == followeeModel.getUserID()))
                throw new RequestParamException(ErrorMessageConstantModel.ALREADY_FOLLOWING);
            FollowModel followModel = new FollowModel();
            followModel.setFolloweeId(followeeModel.getUserID());
            followModel.setFolloweeName(followeeModel.getUserName());
            //check - if follower dont have any followee then  create new followee.
            if (Objects.isNull(followerModel.getFollowee())) {
                List<FollowModel> followeeModels = new ArrayList<>();
                followeeModels.add(followModel);
                followerModel.setFollowee(followeeModels);
            } else {
                followerModel.getFollowee().add(followModel);
            }

        } catch (RequestParamException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new RequestParamException(e.getMessage());
        } catch (UserDoesNotExistsException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new UserDoesNotExistsException(e.getMessage());
        }
        //Wrap the response User with all the followee objects in ResponseEntity.
        ResponseEntity<UserModel> response;
        response = new ResponseEntity<>(followerModel, HttpStatus.OK);
        return response;
    }

    /**
     * unfollow method removes information about one user following other and should not get the feeds. Initially it checks for the conditions.
     * if the user tried to unfollow itself which is not allowed.
     * followerId and followeeId must be unique to update the follow information Also the User for both of them must be exists.
     * Also check for follower already unfollowing followee.
     * if the conditions are not satisfied then it throw exceptions which is then catch in CustomExceptionHandler.
     *
     * @param followerId
     * @param followeeId
     * @return User with all the followee information.
     */
    @Override
    public ResponseEntity unFollow(int followerId, int followeeId) {
        UserModel followerModel;
        UserModel followeeModel;

        try {
            //check - if user tried to unfollow itself
            if (followeeId == followerId) {
                throw new RequestParamException(ErrorMessageConstantModel.CAN_NOT_UNFOLLOW_SELF);
            }
            //check - if null and empty request params.
            else if (followeeId <= 0 || followerId <= 0) {
                throw new RequestParamException(ErrorMessageConstantModel.MISSING_REQUEST_PARAM);
            }

            //check - if follower and followee must exists in the collection.
            followerModel = userList.parallelStream().filter(s -> s.getUserID() == followerId).findFirst().orElseThrow(() -> new UserDoesNotExistsException(ErrorMessageConstantModel.FOLLOWER_DOES_NOT_EXISTS));
            followeeModel = userList.parallelStream().filter(s -> s.getUserID() == followeeId).findFirst().orElseThrow(() -> new UserDoesNotExistsException(ErrorMessageConstantModel.FOLLOWEE_DOES_NOT_EXISTS));

            //check - if user is following the followee then remove it else throw already not following.
            if (followerModel.getFollowee().parallelStream().anyMatch(s -> s.getFolloweeId() == followeeModel.getUserID())) {
                followerModel.getFollowee().removeIf(s -> s.getFolloweeId() == followeeModel.getUserID());
            } else {
                throw new RequestParamException(ErrorMessageConstantModel.NOT_FOLLOWING_USER);
            }
        } catch (RequestParamException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new RequestParamException(e.getMessage());
        } catch (UserDoesNotExistsException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new UserDoesNotExistsException(e.getMessage());
        }

        //Wrap the response User with all the followee objects in ResponseEntity.
        ResponseEntity<UserModel> response;
        response = new ResponseEntity<>(followerModel, HttpStatus.OK);
        return response;
    }


    /**
     * getNewsFeed method retrieves maximum top 20 recent feeds from User and its followees.Initially it checks for the conditions.
     * it will throw an exception if there are no any feeds available.
     * Also check for the userID must exists in the collection.
     * if the conditions are not satisfied then it throw exceptions which is then catch in CustomExceptionHandler.
     * @param userID
     * @return Maximum top 20 recent posts (returns with postID, postContent and postCreation to better understand the recentness).
     */
    @Override
    public ResponseEntity getNewsFeed(int userID) {
        UserModel userModel;
        List<UserModel> checkList = new ArrayList<>(userList);
        List<PostModel> newsFeedModel = null;
        try {
            //check - if null and empty request params.
            if (userID <= 0) {
                throw new RequestParamException(ErrorMessageConstantModel.MISSING_REQUEST_PARAM);
            }
            //check - if User must exists in the collection.
            userModel = checkList.parallelStream().filter(s -> s.getUserID() == userID).findFirst().orElseThrow(() -> new UserDoesNotExistsException(ErrorMessageConstantModel.USER_DOES_NOT_EXISTS));

            //getall the posts of the User to the newsFeedModel
            if (Objects.nonNull(userModel.getPosts()))
                newsFeedModel = new ArrayList<>(userModel.getPosts());

            //check - if the User followee have any posts if they have then add those posts to the newsFeedModel
            if (Objects.nonNull(userModel.getFollowee())) {
                for (FollowModel followModel : userModel.getFollowee()) {
                    for (UserModel userModel1 : checkList) {
                        if (followModel.getFolloweeId() == userModel1.getUserID() && Objects.nonNull(userModel1.getPosts())) {
                            newsFeedModel.addAll(userModel1.getPosts());
                        }
                    }
                }
            }
            //check - if the newsFeedModel is empty.
            if (Objects.isNull(newsFeedModel) || newsFeedModel.isEmpty()) {
                throw new PostsNotAvailableException(ErrorMessageConstantModel.POSTS_NOT_AVAILABLE);
            }
            //sort the newsFeedModel model according to the posts creation date and time ascending order
            //Implemented Comparable interface in PostModel class.
            Collections.sort(newsFeedModel);


        } catch (RequestParamException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new RequestParamException(e.getMessage());
        } catch (UserDoesNotExistsException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new UserDoesNotExistsException(e.getMessage());
        } catch (PostsNotAvailableException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new PostsNotAvailableException(e.getMessage());
        }
        //Wrap the response with max top 20 feeds in ResponseEntity.
        ResponseEntity response;
        response = new ResponseEntity(newsFeedModel.stream().limit(20).collect(Collectors.toList()), HttpStatus.OK);
        return response;
    }
}
