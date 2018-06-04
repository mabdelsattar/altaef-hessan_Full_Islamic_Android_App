package com.daralmathour.altaefhessan.Activities;

/**
 * Created by codelab on 6/3/2018.
 */

public class SearchViewModel {
    public  String AyahNumber;
    public  String PageNumber;
    public  String Content;
    public  String SoraName;


    public SearchViewModel() {
    }

    public SearchViewModel(String ayahNumber, String pageNumber, String content,String soraName) {
        AyahNumber = ayahNumber;
        PageNumber = pageNumber;
        Content = content;
        SoraName = soraName;
    }

    public String getSoraName() {
        return SoraName;
    }

    public void setSoraName(String soraName) {
        SoraName = soraName;
    }

    public String getAyahNumber() {
        return AyahNumber;
    }

    public void setAyahNumber(String ayahNumber) {
        AyahNumber = ayahNumber;
    }

    public String getPageNumber() {
        return PageNumber;
    }

    public void setPageNumber(String pageNumber) {
        PageNumber = pageNumber;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
