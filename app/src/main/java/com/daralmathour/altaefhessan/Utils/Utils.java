package com.daralmathour.altaefhessan.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.daralmathour.altaefhessan.Utils.Constant.API_BASE_URL;

/**
 * Created by mohamed_3ntar on 2/19/2018.
 */

public class Utils {

    public static String getRequestUrl(double latitude,double longitude,int method ,int month , int year)
    {
        String url=API_BASE_URL;
        url+="latitude=";
        url+=String.valueOf(latitude);
        url+="&longitude=";
        url+=String.valueOf(longitude);
        url+="&method=";
        url+=String.valueOf(method);
        url+="&month=";
        url+=String.valueOf(month);
        url+="&year=";
        url+=String.valueOf(year);
        Log.d(Constant.TAG,url);
        return url;
    }

    public static String msToString(long ms) {
        long totalSecs = ms/1000;
        long hours = (totalSecs / 3600);
        long mins = (totalSecs / 60) % 60;
        long secs = totalSecs % 60;
        String minsString = (mins == 0)
                ? "00"
                : ((mins < 10)
                ? "0" + mins
                : "" + mins);
        String secsString = (secs == 0)
                ? "00"
                : ((secs < 10)
                ? "0" + secs
                : "" + secs);
        if (hours > 0)
            return hours + ":" + minsString + ":" + secsString;
        else if (mins > 0)
            return "00:"+mins + ":" + secsString;
        else return "00:"+"00"+":" + secsString;
    }

    public static String convertSecondsToHMmSs(long seconds) {
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        return String.format("%d:%02d:%02d", h,m,s);
    }

    public static Long getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Long result = calendar.getTime().getTime();
        Log.d("loadTransaction", "getStartOfDay :" + result);
        return result;
    }

    public static boolean checkNextTime(String time) {
        Log.d(Constant.TAG, "time : " + time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String currentTime = dateFormat.format(new Date()).toString();
        Log.d(Constant.TAG, "currentTime : " + currentTime);

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = dateFormat.parse(currentTime);
            d2 = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long elapsed = d2.getTime() - d1.getTime();
        System.out.println(elapsed);
        if (elapsed > -5000)
            return true;
        else
            return false;
    }
    public static boolean checkCurrentPrayTime(String time) {
        Log.d(Constant.TAG, "time : " + time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String currentTime = dateFormat.format(new Date()).toString();
        Log.d(Constant.TAG, "currentTime : " + currentTime);

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = dateFormat.parse(currentTime);
            d2 = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long elapsed = d2.getTime() - d1.getTime();
        System.out.println(elapsed);
        Log.d("PrayerTime","SampleSchedulingService elapsed time = "+elapsed);
        if (elapsed == 0)
            return true;
        else
            return false;
    }
    public static long getDiffractInMaltese(String time) {
        Log.d(Constant.TAG, "StartTime : " + time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String currentTime = dateFormat.format(new Date()).toString();
        Log.d(Constant.TAG, "currentTime : " + currentTime );

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = dateFormat.parse(currentTime);
            d2 = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("StartTime","SampleSchedulingService d2.getTime() , d1.getTime() = "+d2.getTime() +" " +d1.getTime());
        long elapsed = d2.getTime() - d1.getTime();
        System.out.println(elapsed);
        Log.d("StartTime","SampleSchedulingService elapsed time = "+elapsed);
        if (elapsed<0)
//            elapsed = d1.getTime() - d2.getTime();
            elapsed+=24*60*60*1000;
        Log.d("StartTime","SampleSchedulingService elapsed time = "+elapsed);
        return elapsed;

    }


    public static long getDiffractInMaltese(String end , String start) {
        Log.d(Constant.TAG, "EndTime : " + end);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        Log.d(Constant.TAG, "StartTime: " + start );

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = dateFormat.parse(start);
            d2 = dateFormat.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("StartTime","SampleSchedulingService d2.getTime() , d1.getTime() = "+d2.getTime() +" " +d1.getTime());
        long elapsed = d2.getTime() - d1.getTime();
        System.out.println(elapsed);
        Log.d("StartTime","SampleSchedulingService elapsed time = "+elapsed);
        if (elapsed<0)
//            elapsed = d1.getTime() - d2.getTime();
            elapsed+=24*60*60*1000;
        Log.d("StartTime","SampleSchedulingService elapsed time = "+elapsed);
        return elapsed;

    }

    public static String parseTimeToAmPm(String time) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Log.d(Constant.TAG, "data : " + time);
            Log.d(Constant.TAG, "data : " + time.substring(0, 5));

            Date date3 = sdf.parse(time.substring(0, 5));
            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a");
            result = sdf2.format(date3);
            Log.d(Constant.TAG, "data : " + result);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static   ArrayList<Boolean> loadPrayerNotificationStatus(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("PrayerTimeNotificationStatus", Context.MODE_PRIVATE);
        ArrayList<Boolean> prayerNotificationStatusResult = new ArrayList<Boolean>();
        prayerNotificationStatusResult.add(preferences.getBoolean("FajrNotification", true));
        prayerNotificationStatusResult.add(preferences.getBoolean("SunriseNotification", true));
        prayerNotificationStatusResult.add(preferences.getBoolean("DhuhrNotification", true));
        prayerNotificationStatusResult.add(preferences.getBoolean("AsrNotification", true));
        prayerNotificationStatusResult.add(preferences.getBoolean("MaghribNotification", true));
        prayerNotificationStatusResult.add(preferences.getBoolean("IshaNotification", true));
        return prayerNotificationStatusResult;
    }
}
