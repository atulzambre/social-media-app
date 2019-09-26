package com.socialmediaapp.model;

public class ErrorMessageConstantModel {


    public static final String MISSING_REQUEST_PARAM = "Missing/Incorrect request parameters.Please try again with correct parameters.";

    public static final String USER_ALREADY_EXISTS = "User already exists in the collection. Please try with different/unique userID.";

    public static final String USER_DOES_NOT_EXISTS = "User does not exists in the collection. Please try to add the User first.";

    public static final String FOLLOWER_DOES_NOT_EXISTS = "Follower does not exists in the collection. Please try to add the User first.";

    public static final String FOLLOWEE_DOES_NOT_EXISTS = "Followee does not exists in the collection. Please try to add the User first.";

    public static final String CAN_NOT_FOLLOW_SELF = "You can not follow yourself. Please try with different followeeId.";

    public static final String CAN_NOT_UNFOLLOW_SELF = "You can not unFollow yourself.Please check the Users you are following.";

    public static final String ALREADY_FOLLOWING = "Already following this user.";

    public static final String NOT_FOLLOWING_USER = "You are not following this User.Please follow the User first.";

    public static final String POSTS_NOT_AVAILABLE = "There are no posts available to show.";

    public static final String POST_ALREADY_EXISTS = "This post already exists. Please try with different postID.";


    private ErrorMessageConstantModel() {
    }

}
