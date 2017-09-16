package com.example.android.javadevapp;

/**
 * Created by GOZMAN VICTOR on 9/15/2017.
 */

public class Developer {

    private String username;
    private String imageUrl;
    private String profileUrl;


    Developer(String username, String imageUrl,String profileUrl){
        this.username = username;
        this.imageUrl = imageUrl;
        this.profileUrl = profileUrl;
    }

    public String getUsername(){
        return username;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getProfileUrl(){
        return profileUrl;
    }
}
