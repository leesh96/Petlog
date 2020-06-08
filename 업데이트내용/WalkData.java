package com.example.test02.data;
//ShareActivity-ArrayList에 데이터를 저장하기 위해 사용되는 클래스
//ShareAdapter-ArrayList에 있는 데이터를 리사이클러뷰에 보여줄 때 사용

public class WalkData {

    private String Walk_id;
    private String Walk_title;
    private String Walk_content;
    private String Walk_img;
    private String Walk_nickname;
    private String Walk_date;

    public String getWalk_date() {
        return Walk_date;
    }

    public void setWalk_date(String walk_date) {
        Walk_date = walk_date;
    }
//

    public String getWalk_nickname() {
        return Walk_nickname;
    }

    public void setWalk_nickname(String walk_nickname) {
        Walk_nickname = walk_nickname;
    }

    public String getWalk_img(){
        return Walk_img;
    }

    public String getWalk_id() {
        return Walk_id;
    }

    public String getWalk_title() {
        return Walk_title;
    }

    public String getWalk_content() {
        return Walk_content;
    }

    public void setWalk_img(String Walk_img){
        this.Walk_img=Walk_img;
    }

    public void setWalk_id(String Walk_id) {
        this.Walk_id = Walk_id;
    }

    public void setWalk_title(String Walk_title) {
        this.Walk_title = Walk_title;
    }

    public void setWalk_content(String Walk_content) {
        this.Walk_content = Walk_content;
    }

    public WalkData(String walk_id, String walk_title, String walk_content, String walk_nickname, String walk_date) {
        Walk_id = walk_id;
        Walk_title = walk_title;
        Walk_content = walk_content;
        Walk_nickname = walk_nickname;
        Walk_date = walk_date;
    }
}
