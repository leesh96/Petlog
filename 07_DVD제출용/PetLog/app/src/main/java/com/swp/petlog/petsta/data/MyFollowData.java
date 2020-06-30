package com.swp.petlog.petsta.data;

public class MyFollowData {
    private String follower_nickname;
    private String follower_face;

    public MyFollowData(String follower_nickname) {
        this.follower_nickname = follower_nickname;
    }

    public String getFollower_face() {
        return follower_face;
    }

    public void setFollower_face(String follower_face) {
        this.follower_face = follower_face;
    }

    public String getFollowerNickname() { return follower_nickname; }

    public void setFollowerNickname(String follower_nickname) { this.follower_nickname = follower_nickname; }

}