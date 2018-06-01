package com.daralmathour.altaefhessan.AyatInfo;

import java.util.ArrayList;

/**
 * Created by codelab on 4/27/2018.
 */

public class PageInformation {

    public int pageIndex;
    public ArrayList<AyahInformation> pageAyat;

    public PageInformation(int pageIndex, ArrayList<AyahInformation> pageAyat) {
        this.pageIndex = pageIndex;
        this.pageAyat = pageAyat;
    }

    public PageInformation() {
    }


    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public ArrayList<AyahInformation> getPageAyat() {
        return pageAyat;
    }

    public void setPageAyat(ArrayList<AyahInformation> pageAyat) {
        this.pageAyat = pageAyat;
    }
}
