package com.socialmediaapp.serviceimpl;

import com.socialmediaapp.exception.CustomConflictException;
import com.socialmediaapp.exception.CustomNotFoundException;
import com.socialmediaapp.model.ErrorMessageConstantModel;
import com.socialmediaapp.model.PostModel;
import com.socialmediaapp.model.UserModel;
import com.socialmediaapp.service.SocialMediaAppService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
    static final Map<String, UserModel> userIdIndex = new ConcurrentHashMap<>();

    /**
     * createNewPost method stores the new posts for the user.
     *
     * @param userId      Users unique key
     * @param postId
     * @param postContent
     * @return User with all its posts.
     */
    @Override
    public ResponseEntity createNewPost(String userId, String postId, String postContent) {
        if (!Objects.nonNull(userIdIndex.get(userId)))
            throw new CustomNotFoundException(ErrorMessageConstantModel.USER_DOES_NOT_EXISTS);
        UserModel user = userIdIndex.get(userId);
        if (Objects.nonNull(user.getPostModelMap().get(postId)))
            throw new CustomConflictException(ErrorMessageConstantModel.POST_ALREADY_EXISTS);

        PostModel newPost = new PostModel();
        synchronized (this) {
            newPost.setPostContent(postContent);
            newPost.setPostId(postId);
            newPost.setPostCreated();
            user.getPostModelMap().put(postId, newPost);
            user.getPosts().add(newPost);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    /**
     * follow method stores information about one user following other to get the feeds.
     *
     * @param followerId
     * @param followeeId
     * @return User with all the followee information.
     */
    @Override
    public ResponseEntity follow(String followerId, String followeeId) {
        if (!(Objects.nonNull(userIdIndex.get(followerId)) && Objects.nonNull(userIdIndex.get(followeeId))))
            throw new CustomNotFoundException(ErrorMessageConstantModel.USER_DOES_NOT_EXISTS);
        if (userIdIndex.get(followerId).getFollowees().contains(followeeId))
            throw new CustomConflictException(ErrorMessageConstantModel.ALREADY_FOLLOWING);
        synchronized (this) {
            userIdIndex.get(followerId).getFollowees().add(followeeId);
        }
        return new ResponseEntity<>(userIdIndex.get(followerId), HttpStatus.OK);
    }


    /**
     * unfollow method removes information about one user following other and should not get the feeds.
     *
     * @param followerId
     * @param followeeId
     * @return User with all the followee information.
     */
    @Override
    public ResponseEntity unFollow(String followerId, String followeeId) {
        if (!(Objects.nonNull(userIdIndex.get(followerId)) && Objects.nonNull(userIdIndex.get(followeeId))))
            throw new CustomNotFoundException(ErrorMessageConstantModel.USER_DOES_NOT_EXISTS);
        if (!userIdIndex.get(followerId).getFollowees().contains(followeeId))
            throw new CustomConflictException(ErrorMessageConstantModel.NOT_FOLLOWING_USER);

        synchronized (this) {
            userIdIndex.get(followerId).getFollowees().remove(followeeId);
        }
        return new ResponseEntity<>(userIdIndex.get(followerId), HttpStatus.OK);
    }


    /**
     * getNewsFeed method retrieves maximum top 20 recent feeds from User and its followees.
     *
     * @param userId
     * @return Maximum top 20 recent posts (returns with postID, postContent and postCreation to better understand the recentness).
     */
    @Override
    public ResponseEntity getNewsFeed(String userId) {
        if (!Objects.nonNull(userIdIndex.get(userId)))
            throw new CustomNotFoundException(ErrorMessageConstantModel.USER_DOES_NOT_EXISTS);

        UserModel user = userIdIndex.get(userId);
        List<PostModel> allFolloweePosts = new ArrayList<>();
        synchronized (this) {
            for (String followeeId : user.getFollowees()) {
                allFolloweePosts.addAll(userIdIndex.get(followeeId).getTopPosts(20));
            }
            allFolloweePosts.addAll(user.getTopPosts(20));
        }
        allFolloweePosts.sort(Comparator.comparing(PostModel::getPostCreated));
        return new ResponseEntity(allFolloweePosts.stream().limit(20).collect(Collectors.toList()), HttpStatus.OK);
    }
}
