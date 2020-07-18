package com.example.location;

import java.io.Serializable;

public class KSH_TestEntity implements Serializable {
    private String key;
    private String title;

//    public KSH_TestEntity(){}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
