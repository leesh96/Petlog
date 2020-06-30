package com.swp.petlog.petsta.data;

public class PetstaCommentData {
    private String post_id, member_comment, member_nick;
    private String member_id;

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getMember_comment() {
        return member_comment;
    }

    public void setMember_comment(String member_comment) {
        this.member_comment = member_comment;
    }

    public String getMember_nick() {
        return member_nick;
    }

    public void setMember_nick(String member_nick) {
        this.member_nick = member_nick;
    }

    public PetstaCommentData(String post_id, String member_comment, String member_nick) {
        this.post_id = post_id;
        this.member_comment = member_comment;
        this.member_nick = member_nick;
    }
}
