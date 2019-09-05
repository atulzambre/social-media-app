package com.socialmediaapp.model;

import javax.validation.constraints.Pattern;

public class FollowUnFollowRequestModel {
    @Pattern(regexp = "^[1-9][0-9]*$")
    private String followerId;
    @Pattern(regexp = "^[1-9][0-9]*$")
    private String followeeId;

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    public String getFolloweeId() {
        return followeeId;
    }

    public void setFolloweeId(String followeeId) {
        this.followeeId = followeeId;
    }
}
