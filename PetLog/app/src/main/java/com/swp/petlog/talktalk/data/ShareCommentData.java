package com.swp.petlog.talktalk.data;

public class ShareCommentData {
    private String id;
    private String share_id;
    private String share_comment;
    private String share_nickname;

    //

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShare_id() {
        return share_id;
    }

    public void setShare_id(String share_id) {
        this.share_id = share_id;
    }

    public String getShare_comment() {
        return share_comment;
    }

    public void setShare_comment(String share_comment) {
        this.share_comment = share_comment;
    }

    public String getShare_nickname() {
        return share_nickname;
    }

    public void setShare_nickname(String share_nickname) {
        this.share_nickname = share_nickname;
    }

    //


    public ShareCommentData(String share_id, String share_comment, String share_nickname) {
        this.share_id = share_id;
        this.share_comment = share_comment;
        this.share_nickname = share_nickname;
    }
}

