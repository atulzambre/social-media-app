package com.socialmediaapp.serviceimpl;

import com.socialmediaapp.exception.UserAlreadyExistsException;
import com.socialmediaapp.exception.UserDoesNotExistsException;
import com.socialmediaapp.model.ErrorMessageConstantModel;
import com.socialmediaapp.model.PostModel;
import com.socialmediaapp.model.UserModel;
import com.socialmediaapp.service.SocialMediaAppService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * SocialMediaAppServiceImpl is implemented the methods from SocialMediaAppService and developed business logic for the problem statement.
 * Returning the updated User Models with the successful response to better understand the working of the API.
 * Used Lambda/Stream API , Builder Design Pattern, Factory Design Pattern, Dependency Injection etc.
 *
 * @author atulzambre
 */
@Service
public class SocialMediaAppServiceImpl implements SocialMediaAppService {
    private static final CopyOnWriteArrayList<UserModel> userCollectionDatabase = new CopyOnWriteArrayList<>();

    private static final Map<String, UserModel> userIdIndex = new HashMap<>();


    /**
     * createUser method stores the userID and userName in the collection. Initially it checks for the conditions.
     * for every User userID must me unique and userName should not me empty.
     * if the conditions are not satisfied then it throw exceptions which is then catch in CustomExceptionHandler.
     *
     * @param userId
     * @param userName
     * @return Successfully stored user object wrapping in ResponseEntity.
     */
    public ResponseEntity createUser(String userId, String userName) {
        if (Objects.nonNull(userIdIndex.get(userId)))
            throw new UserAlreadyExistsException("User Already Exists!");
        UserModel newUser = new UserModel();
        newUser.setUserId(userId);
        newUser.setUserName(userName);
        synchronized (this) {
            userCollectionDatabase.add(newUser);
            userIdIndex.put(userId, newUser);
        }
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    /**
     * getAllUsers method retrieves all the available Users in the collection. Initially it checks for the collection emptiness.
     * if the collection is empty then it throws exceptions which is then catch in CustomExceptionHandler.
     *
     * @return List of Users available in the collection.
     */
    @Override
    public ResponseEntity getAllUsers() {
            if (userCollectionDatabase.isEmpty()) {
                throw new UserDoesNotExistsException(ErrorMessageConstantModel.USER_DOES_NOT_EXISTS);
            }
        return new ResponseEntity<>(userCollectionDatabase, HttpStatus.OK);
    }

    /**
     * createNewPost method stores the new posts for the user. Initially it checks for the conditions.
     * postID must be unique to store the post Also the User for the userID must be exists.
     * Posts are also containing the creation date and time.
     * if the conditions are not satisfied then it throw exceptions which is then catch in CustomExceptionHandler.
     *
     * @param userId
     * @param postId
     * @param postContent
     * @return User with all its posts.
     */
    @Override
    public ResponseEntity createNewPost(String userId, String postId, String postContent) {
        if (!Objects.nonNull(userIdIndex.get(userId)))
            throw new UserDoesNotExistsException("User Does Not Exists!");
        UserModel user = userIdIndex.get(userId);
        if (Objects.nonNull(user.getPostModelMap().get(postId)))
            throw new UserAlreadyExistsException("Post is already present!");

        PostModel newPost = new PostModel();
        newPost.setPostContent(postContent);
        newPost.setPostId(postId);
        newPost.setPostCreated();
        user.getPostModelMap().put(postId, newPost);
        user.getPosts().add(newPost);
        return new ResponseEntity<>(user, HttpStatus.OK);
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
    public ResponseEntity follow(String followerId, String followeeId) {
        if(!(Objects.nonNull(userIdIndex.get(followerId))&&Objects.nonNull(userIdIndex.get(followeeId))))
            throw new UserDoesNotExistsException("User Does Not Exists");
        if(userIdIndex.get(followerId).getFollowees().contains(followeeId))
            throw new UserAlreadyExistsException("Already Following this user");
        UserModel followeeUser=userIdIndex.get(followeeId);
        userIdIndex.get(followerId).getFollowees().add(followeeId);
        return new ResponseEntity(userIdIndex.get(followerId),HttpStatus.OK);
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
    public ResponseEntity unFollow(String followerId, String followeeId) {
        if(!(Objects.nonNull(userIdIndex.get(followerId))&&Objects.nonNull(userIdIndex.get(followeeId))))
            throw new UserDoesNotExistsException("User Does Not Exists");
        if(!userIdIndex.get(followerId).getFollowees().contains(followeeId))
            throw new UserAlreadyExistsException("Already UnFollowing this user");
        UserModel followeeUser=userIdIndex.get(followeeId);
        userIdIndex.get(followerId).getFollowees().remove(followeeId);
        return new ResponseEntity(userIdIndex.get(followerId),HttpStatus.OK);

    }


    /**
     * getNewsFeed method retrieves maximum top 20 recent feeds from User and its followees.Initially it checks for the conditions.
     * it will throw an exception if there are no any feeds available.
     * Also check for the userID must exists in the collection.
     * if the conditions are not satisfied then it throw exceptions which is then catch in CustomExceptionHandler.
     *
     * @param userId
     * @return Maximum top 20 recent posts (returns with postID, postContent and postCreation to better understand the recentness).
     */
    @Override
    public ResponseEntity getNewsFeed(String userId) {
//        UserModel userModel;
//        List<UserModel> proxyuserCollectionDatabase = new ArrayList<>(userCollectionDatabase);
//        List<PostModel> newsFeedModel = null;
//        try {
//            //check - if null and empty request params.
//            if (userId <= 0) {
//                throw new RequestParamException(ErrorMessageConstantModel.MISSING_REQUEST_PARAM);
//            }
//            //check - if User must exists in the collection.
//            userModel = proxyuserCollectionDatabase.stream().filter(s -> s.getUserId() == userId).findFirst().orElseThrow(() -> new UserDoesNotExistsException(ErrorMessageConstantModel.USER_DOES_NOT_EXISTS));
//
//            //getall the posts of the User to the newsFeedModel
//            //Thread Safe - use use threads else remove the syncronized
//            synchronized (this) {
//                if (Objects.nonNull(userModel.getPosts()))
//                    newsFeedModel = new ArrayList<>(userModel.getPosts());
//
//                //check - if the User followee have any posts if they have then add those posts to the newsFeedModel
//                if (Objects.nonNull(userModel.getFollowee())) {
//                    for (Object followModel : userModel.getFollowee()) {
//                        for (UserModel innerUserModel : proxyuserCollectionDatabase) {
//                            if (followModel.getFolloweeId() == innerUserModel.getUserId() && Objects.nonNull(innerUserModel.getPosts())) {
//                                newsFeedModel.addAll(innerUserModel.getPosts());
//                            }
//                        }
//                    }
//                }
//            }
//            //check - if the newsFeedModel is empty.
//            if (Objects.isNull(newsFeedModel) || newsFeedModel.isEmpty()) {
//                throw new PostsNotAvailableException(ErrorMessageConstantModel.POSTS_NOT_AVAILABLE);
//            }
//            //sort the newsFeedModel model according to the posts creation date and time ascending order
//            //Implemented Comparable interface in PostModel class.
//            Collections.sort(newsFeedModel);
//
//
//        } catch (RequestParamException e) {
//
//            throw new RequestParamException(e.getMessage());
//        } catch (UserDoesNotExistsException e) {
//
//            throw new UserDoesNotExistsException(e.getMessage());
//        } catch (PostsNotAvailableException e) {
//
//            throw new PostsNotAvailableException(e.getMessage());
//        }
//        //Wrap the response with max top 20 feeds in ResponseEntity.
//        ResponseEntity response;
//        response = new ResponseEntity(newsFeedModel.stream().limit(20).collect(Collectors.toList()), HttpStatus.OK);
//        return response;

        return null;
    }


}
