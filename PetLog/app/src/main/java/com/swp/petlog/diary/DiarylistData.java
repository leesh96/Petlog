package com.swp.petlog.diary;

public class DiarylistData {
    private int member_id;
    private String member_title;
    private String member_contents;
    private String member_date;
    private int member_mood;
    private boolean selected;
    private String member_img;

    public String getMember_img() {
        return member_img;
    }

    public void setMember_img(String member_img) {
        this.member_img = member_img;
    }

    public DiarylistData(String member_title, String member_date) {
        this.member_title = member_title;
        this.member_date = member_date;
        this.selected = false;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getMember_contents() {
        return member_contents;
    }

    public void setMember_contents(String member_contents) {
        this.member_contents = member_contents;
    }

    public String getMember_title() {return member_title;}
    public String getMember_date() {return member_date;}
    public int getMember_mood() {return member_mood;}

    public void setMember_title(String member_title) {
        this.member_title = member_title;
    }

    public void setMember_date(String member_date) {
        this.member_date = member_date;
    }

    public void setMember_mood(int member_mood) {
        this.member_mood = member_mood;
    }
}
