package com.daralmathour.altaefhessan.API.Model;

/**
 * Created by mohamed_3ntar on 2/19/2018.
 */

public class Params {
    private String Fajr;

    private String Isha;

    public String getFajr ()
    {
        return Fajr;
    }

    public void setFajr (String Fajr)
    {
        this.Fajr = Fajr;
    }

    public String getIsha ()
    {
        return Isha;
    }

    public void setIsha (String Isha)
    {
        this.Isha = Isha;
    }

    @Override
    public String toString()
    {
        return "Params [Fajr = "+Fajr+", Isha = "+Isha+"]";
    }
}
