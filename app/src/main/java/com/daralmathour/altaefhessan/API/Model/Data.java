package com.daralmathour.altaefhessan.API.Model;

/**
 * Created by mohamed_3ntar on 2/19/2018.
 */

public class Data {
    private Timings timings;

    private Date date;

    private Meta meta;

    public Timings getTimings ()
    {
        return timings;
    }

    public void setTimings (Timings timings)
    {
        this.timings = timings;
    }

    public Date getDate ()
    {
        return date;
    }

    public void setDate (Date date)
    {
        this.date = date;
    }

    public Meta getMeta ()
    {
        return meta;
    }

    public void setMeta (Meta meta)
    {
        this.meta = meta;
    }

    @Override
    public String toString()
    {
        return " [timings = "+timings+", date = "+date+", meta = "+meta+"]";
    }

}
