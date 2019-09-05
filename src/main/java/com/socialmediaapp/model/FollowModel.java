package com.socialmediaapp.model;

public class FollowModel {
    private String followeeId;
    private String followeeName;

    public FollowModel(String followeeId, String followeeName) {
        this.followeeId = followeeId;
        this.followeeName = followeeName;
    }

    public String getFolloweeId() {
        return followeeId;
    }

    public void setFolloweeId(String followeeId) {
        this.followeeId = followeeId;
    }

    public String getFolloweeName() {
        return followeeName;
    }

    public void setFolloweeName(String followeeName) {
        this.followeeName = followeeName;
    }
}
