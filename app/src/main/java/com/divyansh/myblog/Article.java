package com.divyansh.myblog;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Divyansh on 20-03-2018.
 */

public class Article {

    @SerializedName("id")
    Integer id;

    @SerializedName("heading")
    String heading;

    @SerializedName("title")
    String title;

    @SerializedName("date")
    String date;

    @SerializedName("content")
    String content;

    public Integer getId(){
        return id;
    }

    public String getTitle() { return title; }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getHeading() {
        return heading;
    }
}
