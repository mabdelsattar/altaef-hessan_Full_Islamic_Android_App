package com.daralmathour.altaefhessan.API.Model;

/**
 * Created by mohamed_3ntar on 2/19/2018.
 */

public class Weekday {
    private String en;
    private String ar;

    public String getEn ()
    {
        return en;
    }

    public void setEn (String en)
    {
        this.en = en;
    }

    public String getAr() {
        return ar;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    @Override
    public String toString() {
        return "Weekday{" +
                "en='" + en + '\'' +
                ", ar='" + ar + '\'' +
                '}';
    }
}
