package com.daralmathour.altaefhessan.Models;

/**
 * Created by mohamed_3ntar on 2/19/2018.
 */

public class PrayerTime {

    boolean notify;
    String name;
    String time;

    public PrayerTime(boolean notify, String name, String time) {
        this.notify = notify;
        this.name = name;
        this.time = time;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "PrayerTime{" +
                "notify=" + notify +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
