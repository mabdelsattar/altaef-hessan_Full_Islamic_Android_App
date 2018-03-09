package com.daralmathour.altaefhessan.PrayerAlarm;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class SampleBootReceiver extends BroadcastReceiver {
    SampleAlarmReceiver alarm = new SampleAlarmReceiver();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("SampleSchedulingService","Boot Received");
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis()+5);
            alarm.setAlarm(context,calendar);
        }
        else
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis()+5);
            alarm.setAlarm(context,calendar);
        }
    }
}