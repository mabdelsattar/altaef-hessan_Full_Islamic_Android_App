package com.daralmathour.altaefhessan.PrayerAlarm;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.daralmathour.altaefhessan.API.Model.Data;
import com.daralmathour.altaefhessan.Activities.MawaqeetElsalahActivity;
import com.daralmathour.altaefhessan.Activities.StopAthanActivity;
import com.daralmathour.altaefhessan.R;
import com.daralmathour.altaefhessan.Utils.Constant;

import java.util.ArrayList;
import java.util.Calendar;

import static com.daralmathour.altaefhessan.Utils.Utils.checkCurrentPrayTime;
import static com.daralmathour.altaefhessan.Utils.Utils.loadPrayerNotificationStatus;
import static com.daralmathour.altaefhessan.Utils.Utils.parseTimeToAmPm;


public class SampleSchedulingService extends IntentService {
    SharedPreferences sharedPreferences;
    Data data;
    public SampleSchedulingService() {
        super("SchedulingService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {

        sharedPreferences = getSharedPreferences("PrayerTime", Context.MODE_PRIVATE);
        Calendar cal = Calendar.getInstance();
        int dayIndex = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        month += 1;
        int year = cal.get(Calendar.YEAR);
        String key = "y_" + String.valueOf(year) + "_m_" + String.valueOf(month) + "_d_" + String.valueOf(dayIndex);
        Log.d(Constant.TAG, key);
        String jsonObject = sharedPreferences.getString(key, "");

        if (!jsonObject.equals("")) {
            Data data = new Gson().fromJson(jsonObject, Data.class);
            Log.d(Constant.TAG, "data : " + data.toString());


            String timeFajr = parseTimeToAmPm(data.getTimings().getFajr());
            String timeSunrise = parseTimeToAmPm(data.getTimings().getSunrise());
            String timeDhuhr = parseTimeToAmPm(data.getTimings().getDhuhr());
            String timeAsr = parseTimeToAmPm(data.getTimings().getAsr());
            String timeMaghrib = parseTimeToAmPm(data.getTimings().getMaghrib());
            String timeIsha = parseTimeToAmPm(data.getTimings().getIsha());
            ArrayList<Boolean> prayerNotificationStatus;
            prayerNotificationStatus = loadPrayerNotificationStatus(this);

            if (checkCurrentPrayTime(timeFajr))
            {
                if (prayerNotificationStatus.get(0))
                    openApplicationForAzan("الفجر");
            }
            else if (checkCurrentPrayTime(timeSunrise))
            {
                if (prayerNotificationStatus.get(1))
                    openApplicationForAzan("الشروق");

            }
            else if (checkCurrentPrayTime(timeDhuhr))
            {
                if (prayerNotificationStatus.get(2))
                    openApplicationForAzan("الظهر");

            }
            else if (checkCurrentPrayTime(timeAsr))
            {
                if (prayerNotificationStatus.get(3))
                    openApplicationForAzan("العصر");

            }
            else if (checkCurrentPrayTime(timeMaghrib))
            {
                if (prayerNotificationStatus.get(4))
                    openApplicationForAzan("المغرب");

            }
            else if (checkCurrentPrayTime(timeIsha))
            {
                if (prayerNotificationStatus.get(5))
                    openApplicationForAzan("العشاء");
            }
        }

        Log.d("PrayerTime","SampleSchedulingService");
        SampleAlarmReceiver.completeWakefulIntent(intent);
    }
    private void openApplicationForAzan(String prayerName)
    {
        if (!isMyServiceRunning(AthanService.class)) {
            showNotification(this, prayerName);
            Intent audioIntent = new Intent(this, AthanService.class);
            startService(audioIntent);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    private void showNotification(Context context,String prayerName)
    {

        int notificationId = 0;

        // Use one intent to show MawaqeetElsalahActivity and another intent to stop athan (by notification
        // button or swipe left or volume button press)
        Intent intent = new Intent(context, MawaqeetElsalahActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, 0, intent, 0);
        PendingIntent stopAudioIntent = StopAthanActivity.getIntent(notificationId, context);

        Bitmap largeIconBmp = BitmapFactory.decodeResource(context.getResources(),
                R.mipmap.ic_launcher);
        /*Keep this in case we need it
        Resources res = context.getResources();
        int height = (int) res.getDimension(android.R.dimen.notification_large_icon_height);
        int width = (int) res.getDimension(android.R.dimen.notification_large_icon_width);
        largeIconBmp = Bitmap.createScaledBitmap(largeIconBmp, width, height, false);*/

        String contentTitle = "اللطائف الحسان - حان موعد الاذان";
        String contentTxt = String.format("حان الان موعد أذن  : "+ prayerName );

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setSmallIcon(R.drawable.mawaqeet_elsalah)
                        .setContentTitle(contentTitle)
                        .setContentText(contentTxt)
                        .setContentIntent(activity)
                        .setCategory(NotificationCompat.CATEGORY_ALARM)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true)
                        .setDeleteIntent(stopAudioIntent)
                        .setLargeIcon(largeIconBmp);

        // TODO: add a timeout (till Iqama) with android O

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // Build the notification and issue it
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}