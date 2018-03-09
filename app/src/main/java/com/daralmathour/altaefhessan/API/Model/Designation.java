package com.daralmathour.altaefhessan.API.Model;

/**
 * Created by mohamed_3ntar on 2/19/2018.
 */

public class Designation {
    private String abbreviated;

    private String expanded;

    public String getAbbreviated ()
    {
        return abbreviated;
    }

    public void setAbbreviated (String abbreviated)
    {
        this.abbreviated = abbreviated;
    }

    public String getExpanded ()
    {
        return expanded;
    }

    public void setExpanded (String expanded)
    {
        this.expanded = expanded;
    }

    @Override
    public String toString()
    {
        return "Designation [abbreviated = "+abbreviated+", expanded = "+expanded+"]";
    }
}
