package com.daralmathour.altaefhessan.Models;

/**
 * Created by codelab on 3/20/2018.
 */


public class Movie {
    private String title, Url;
    private  int ImgUrl;

    public Movie(String title, String url, int imgUrl) {
        this.title = title;
        Url = url;
        ImgUrl = imgUrl;
    }

    public Movie() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public int getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(int imgUrl) {
        ImgUrl = imgUrl;
    }
}