package com.swp.petlog.setting;

public class NoticeData {
    private String Notice_id;
    private String Notice_title;
    private String Notice_content;
    private String Notice_date;


    public String getNotice_id() {
        return Notice_id;
    }

    public void setNotice_id(String notice_id) {
        Notice_id = notice_id;
    }

    public String getNotice_title() {
        return Notice_title;
    }

    public void setNotice_title(String notice_title) {
        Notice_title = notice_title;
    }

    public String getNotice_content() {
        return Notice_content;
    }

    public void setNotice_content(String notice_content) {
        Notice_content = notice_content;
    }

    public String getNotice_date() {
        return Notice_date;
    }

    public void setNotice_date(String notice_date) {
        Notice_date = notice_date;
    }

    public NoticeData(String notice_id, String notice_title, String notice_content, String notice_date) {
        Notice_id = notice_id;
        Notice_title = notice_title;
        Notice_content = notice_content;
        Notice_date = notice_date;
    }
}
