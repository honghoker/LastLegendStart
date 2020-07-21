package com.example.pcsandroid;

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

class Pcs_compareTo implements Comparable<Location>{

    public Pcs_compareTo(String order) {

    }

    @Override
    public int compareTo(Location location) {
        return ;
    }
}
