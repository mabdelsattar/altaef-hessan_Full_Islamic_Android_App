package com.daralmathour.altaefhessan.API.Model;

/**
 * Created by mohamed_3ntar on 2/19/2018.
 */

public class Offset {
    private String Fajr;

    private String Isha;

    private String Asr;

    private String Dhuhr;

    private String Midnight;

    private String Sunset;

    private String Sunrise;

    private String Imsak;

    private String Maghrib;

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

    public String getAsr ()
    {
        return Asr;
    }

    public void setAsr (String Asr)
    {
        this.Asr = Asr;
    }

    public String getDhuhr ()
    {
        return Dhuhr;
    }

    public void setDhuhr (String Dhuhr)
    {
        this.Dhuhr = Dhuhr;
    }

    public String getMidnight ()
    {
        return Midnight;
    }

    public void setMidnight (String Midnight)
    {
        this.Midnight = Midnight;
    }

    public String getSunset ()
    {
        return Sunset;
    }

    public void setSunset (String Sunset)
    {
        this.Sunset = Sunset;
    }

    public String getSunrise ()
    {
        return Sunrise;
    }

    public void setSunrise (String Sunrise)
    {
        this.Sunrise = Sunrise;
    }

    public String getImsak ()
    {
        return Imsak;
    }

    public void setImsak (String Imsak)
    {
        this.Imsak = Imsak;
    }

    public String getMaghrib ()
    {
        return Maghrib;
    }

    public void setMaghrib (String Maghrib)
    {
        this.Maghrib = Maghrib;
    }

    @Override
    public String toString()
    {
        return "Offset [Fajr = "+Fajr+", Isha = "+Isha+", Asr = "+Asr+", Dhuhr = "+Dhuhr+", Midnight = "+Midnight+", Sunset = "+Sunset+", Sunrise = "+Sunrise+", Imsak = "+Imsak+", Maghrib = "+Maghrib+"]";
    }
}
