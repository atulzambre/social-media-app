package com.socialmediaapp.model;

public class ErrorMessageConstantModel {


    public static final String MISSING_REQUEST_PARAM = "Missing/Incorrect Request Parameters.";

    public static final String USER_ALREADY_EXISTS = "User Already Exists.";

    public static final String USER_DOES_NOT_EXISTS = "User Does Not Exists.";

    public static final String FOLLOWER_DOES_NOT_EXISTS = "Follower Does Not Exists.";

    public static final String FOLLOWEE_DOES_NOT_EXISTS = "Followee Does Not Exists.";

    public static final String CAN_NOT_FOLLOW_SELF = "You Can Not Follow Yourself.";

    public static final String CAN_NOT_UNFOLLOW_SELF = "You Can Not UnFollow Yourself.";

    public static final String ALREADY_FOLLOWING = "Already Following The User.";

    public static final String NOT_FOLLOWING_USER = "You Are Not Following This User.";

    public static final String POSTS_NOT_AVAILABLE = "No Posts To Show.";


    private ErrorMessageConstantModel() {
    }

}