package com.example.oneplatform;

public class UserCard {


    private int iconId;
    private String userName;
    private String userText;
    private String postId;




    public UserCard(int iconId, String userName, String userText, String postId) {
        this.iconId = iconId;
        this.userName = userName;
        this.userText = userText;
        this.postId = postId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPostId() { return postId; }

    public String getUserText() {
        return userText;
    }

    public int getIconId() {
        return iconId;
    }

}
