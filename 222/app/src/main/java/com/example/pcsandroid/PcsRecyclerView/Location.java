package com.example.pcsandroid.PcsRecyclerView;

//interface comparable implement for sorting
public class Location{
    private String title;
    private String address;
    private String tag;

    public Location(String title, String address, String tag) {
        this.title = title;
        this.address = address;
        this.tag = tag;
    }
    public Location(){

    }


    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getTag() {
        return tag;
    }

}
