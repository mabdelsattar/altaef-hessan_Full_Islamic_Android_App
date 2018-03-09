package com.daralmathour.altaefhessan.API.Model;

/**
 * Created by mohamed_3ntar on 2/19/2018.
 */

public class Month {
    private String ar;

    private String en;

    private String number;

    public String getAr ()
    {
        return ar;
    }

    public void setAr (String ar)
    {
        this.ar = ar;
    }

    public String getEn ()
    {
        return en;
    }

    public void setEn (String en)
    {
        this.en = en;
    }

    public String getNumber ()
    {
        return number;
    }

    public void setNumber (String number)
    {
        this.number = number;
    }

    @Override
    public String toString()
    {
        return "Month [ar = "+ar+", en = "+en+", number = "+number+"]";
    }
}
