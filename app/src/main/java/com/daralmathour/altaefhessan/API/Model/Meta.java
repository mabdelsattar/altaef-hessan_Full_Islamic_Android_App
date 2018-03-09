package com.daralmathour.altaefhessan.API.Model;

/**
 * Created by mohamed_3ntar on 2/19/2018.
 */

public class Meta {
    private String midnightMode;

    private String latitudeAdjustmentMethod;

    private String timezone;

    private String school;

    private Method method;

    private String longitude;

    private String latitude;

    private Offset offset;

    public String getMidnightMode ()
    {
        return midnightMode;
    }

    public void setMidnightMode (String midnightMode)
    {
        this.midnightMode = midnightMode;
    }

    public String getLatitudeAdjustmentMethod ()
    {
        return latitudeAdjustmentMethod;
    }

    public void setLatitudeAdjustmentMethod (String latitudeAdjustmentMethod)
    {
        this.latitudeAdjustmentMethod = latitudeAdjustmentMethod;
    }

    public String getTimezone ()
    {
        return timezone;
    }

    public void setTimezone (String timezone)
    {
        this.timezone = timezone;
    }

    public String getSchool ()
    {
        return school;
    }

    public void setSchool (String school)
    {
        this.school = school;
    }

    public Method getMethod ()
    {
        return method;
    }

    public void setMethod (Method method)
    {
        this.method = method;
    }

    public String getLongitude ()
    {
        return longitude;
    }

    public void setLongitude (String longitude)
    {
        this.longitude = longitude;
    }

    public String getLatitude ()
    {
        return latitude;
    }

    public void setLatitude (String latitude)
    {
        this.latitude = latitude;
    }

    public Offset getOffset ()
    {
        return offset;
    }

    public void setOffset (Offset offset)
    {
        this.offset = offset;
    }

    @Override
    public String toString()
    {
        return "Meta [midnightMode = "+midnightMode+", latitudeAdjustmentMethod = "+latitudeAdjustmentMethod+", timezone = "+timezone+", school = "+school+", method = "+method+", longitude = "+longitude+", latitude = "+latitude+", offset = "+offset+"]";
    }
}
