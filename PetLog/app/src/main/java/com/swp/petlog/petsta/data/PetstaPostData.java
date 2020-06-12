package com.swp.petlog.petsta.data;

public class PetstaPostData {
    private int member_id, member_likecnt, member_commentcnt, member_tag;
    private String member_content, member_image, member_nickname, member_writetime;
    private String member_face;
    private int member_liked;

    public int getMember_liked() {
        return member_liked;
    }

    public void setMember_liked(int member_liked) {
        this.member_liked = member_liked;
    }

    public String getMember_face() {
        return member_face;
    }

    public void setMember_face(String member_face) {
        this.member_face = member_face;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public int getMember_likecnt() {
        return member_likecnt;
    }

    public void setMember_likecnt(int member_likecnt) {
        this.member_likecnt = member_likecnt;
    }

    public int getMember_commentcnt() {
        return member_commentcnt;
    }

    public void setMember_commentcnt(int member_commentcnt) {
        this.member_commentcnt = member_commentcnt;
    }

    public int getMember_tag() {
        return member_tag;
    }

    public void setMember_tag(int member_tag) {
        this.member_tag = member_tag;
    }

    public String getMember_content() {
        return member_content;
    }

    public void setMember_content(String member_content) {
        this.member_content = member_content;
    }

    public String getMember_image() {
        return member_image;
    }

    public void setMember_image(String member_image) {
        this.member_image = member_image;
    }

    public String getMember_nickname() {
        return member_nickname;
    }

    public void setMember_nickname(String member_nickname) {
        this.member_nickname = member_nickname;
    }

    public String getMember_writetime() {
        return member_writetime;
    }

    public void setMember_writetime(String member_writetime) {
        this.member_writetime = member_writetime;
    }
}
