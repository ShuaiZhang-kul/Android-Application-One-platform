package com.example.oneplatform;

public class UserPost {
    private int iconId;
    private String likeNum;
    private String commentNum;
    private String postId;
    private String post;

    public UserPost(int iconId, String likeNum,String commentNum, String postId, String post) {
        this.iconId = iconId;
        this.likeNum = likeNum;
        this.commentNum = commentNum;
        this.postId = postId;
        this.post = post;
    }


    public int getIconId() { return iconId; }

    public String getPostId() {
        return postId;
    }

    public String getLikeNum() {
        return likeNum;
    }


    public String getCommentNum() { return commentNum; }

    public String getPost() { return post; }

}
