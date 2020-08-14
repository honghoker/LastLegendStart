package com.example.locationsave.HEP.KSH.NavIntent;

public class KSH_NoticeEntity {
    String day;
    String title;

    public KSH_NoticeEntity(){}

    public KSH_NoticeEntity(String day, String title) {
        this.day = day;
        this.title = title;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
