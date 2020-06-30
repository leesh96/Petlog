package com.swp.petlog.talktalk.data;

//ShareActivity-ArrayList에 데이터를 저장하기 위해 사용되는 클래스
//ShareAdapter-ArrayList에 있는 데이터를 리사이클러뷰에 보여줄 때 사용

public class ShareData {

    private String share_id;
    private String share_title;
    private String share_content;
    private String share_img;
    private String share_nickname;
    private String share_date;

    //

    public String getShare_date() {
        return share_date;
    }

    public void setShare_date(String share_date) {
        this.share_date = share_date;
    }

    public String getShare_nickname() {

        return share_nickname;
    }

    public void setShare_nickname(String share_nickname) {

        this.share_nickname = share_nickname;
    }

    public String getShare_img(){
        return share_img;
    }

    public String getShare_id() {
        return share_id;
    }

    public String getShare_title() {
        return share_title;
    }

    public String getShare_content() {
        return share_content;
    }

    public void setShare_img(String share_img){
        this.share_img=share_img;
    }

    public void setShare_id(String share_id) {
        this.share_id = share_id;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }


    public void setShare_content(String share_content) {
        this.share_content = share_content;
    }

    public ShareData(String share_id,String share_title, String share_content, String share_nickname, String share_date) {
        this.share_id = share_id;
        this.share_title = share_title;
        this.share_content = share_content;
        this.share_nickname=share_nickname;
        this.share_date=share_date;
        //  this.share_img = share_img;
    }
}
