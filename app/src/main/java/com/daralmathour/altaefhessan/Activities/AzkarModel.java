package com.daralmathour.altaefhessan.Activities;

/**
 * Created by codelab on 6/3/2018.
 */

public class AzkarModel {
   public String Content;
    public String Sawap;
    public  String Repeat;

    public AzkarModel(String content, String sawap,String repeat) {
        Content = content;
        Sawap = sawap;
        Repeat = repeat;
    }

    public String getRepeat() {
        return Repeat;
    }

    public void setRepeat(String repeat) {
        Repeat = repeat;
    }

    public AzkarModel() {
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getSawap() {
        return Sawap;
    }

    public void setSawap(String sawap) {
        Sawap = sawap;
    }
}
