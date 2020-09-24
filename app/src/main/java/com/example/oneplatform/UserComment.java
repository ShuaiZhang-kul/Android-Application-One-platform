package com.example.oneplatform;

public class UserComment {

    private String userName;
    private String userComment;

    public UserComment(String userName, String userComment) {
        this.userName = userName;
        this.userComment = userComment;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserComment() {
        return userComment;
    }


}
