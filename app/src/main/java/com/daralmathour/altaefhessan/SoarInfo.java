package com.daralmathour.altaefhessan;

/**
 * Created by codelab on 1/1/2018.
 */

public class SoarInfo {
    public  String Name;
    public  boolean isMecca;
    public  int AyatCount;
    public int FromPage;


    public SoarInfo(String name, boolean isMecca, int ayatCount,int from) {
        Name = name;
        this.isMecca = isMecca;
        AyatCount = ayatCount;
        this.FromPage = from;
    }

    public SoarInfo() {
    }
}
