package com.daralmathour.altaefhessan.API.Model;

/**
 * Created by mohamed_3ntar on 2/19/2018.
 */

public class Method {
    private String id;

    private String name;

    private Params params;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public Params getParams ()
    {
        return params;
    }

    public void setParams (Params params)
    {
        this.params = params;
    }

    @Override
    public String toString()
    {
        return "Method [id = "+id+", name = "+name+", params = "+params+"]";
    }
}
