package com.daralmathour.altaefhessan.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.daralmathour.altaefhessan.API.ConnectionManager;
import com.daralmathour.altaefhessan.API.Model.Data;
import com.daralmathour.altaefhessan.PrayerAlarm.SampleAlarmReceiver;
import com.daralmathour.altaefhessan.R;
import com.daralmathour.altaefhessan.Utils.Constant;
import com.daralmathour.altaefhessan.Utils.LocationManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.daralmathour.altaefhessan.API.ConnectionManager.httpGetRequest;
import static com.daralmathour.altaefhessan.Utils.Utils.checkNextTime;
import static com.daralmathour.altaefhessan.Utils.Utils.getDiffractInMaltese;
import static com.daralmathour.altaefhessan.Utils.Utils.getRequestUrl;
import static com.daralmathour.altaefhessan.Utils.Utils.loadPrayerNotificationStatus;
import static com.daralmathour.altaefhessan.Utils.Utils.msToString;
import static com.daralmathour.altaefhessan.Utils.Utils.parseTimeToAmPm;

public class MawaqeetElsalahActivity extends AppCompatActivity implements
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        AudioManager.OnAudioFocusChangeListener{

    /**
     * ButterKnife Code
     **/
    @BindView(R.id.topBar)
    RelativeLayout mTopBar;
    @BindView(R.id.menuBtn)
    ImageView mMenuBtn;
    @BindView(R.id.remindTimeHint)
    TextView mRemindTimeHint;
    @BindView(R.id.prayerName)
    TextView mPrayerName;
    @BindView(R.id.prayerTime)
    TextView mPrayerTime;
    @BindView(R.id.remindTime)
    TextView mRemindTime;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.country)
    TextView mCountry;
    @BindView(R.id.prayerItem1)
    android.support.v7.widget.CardView mPrayerItem1;
    @BindView(R.id.prayerItem1Name)
    TextView mPrayerItem1Name;
    @BindView(R.id.prayerItem1Icon)
    ImageView mPrayerItem1Icon;
    @BindView(R.id.prayerItem1Time)
    TextView mPrayerItem1Time;
    @BindView(R.id.prayerItem1Notification)
    ImageView mPrayerItem1Notification;
    @BindView(R.id.prayerItem2)
    android.support.v7.widget.CardView mPrayerItem2;
    @BindView(R.id.prayerItem2Name)
    TextView mPrayerItem2Name;
    @BindView(R.id.prayerItem2Icon)
    ImageView mPrayerItem2Icon;
    @BindView(R.id.prayerItem2Time)
    TextView mPrayerItem2Time;
    @BindView(R.id.prayerItem2Notification)
    ImageView mPrayerItem2Notification;
    @BindView(R.id.prayerItem3)
    android.support.v7.widget.CardView mPrayerItem3;
    @BindView(R.id.prayerItem3Name)
    TextView mPrayerItem3Name;
    @BindView(R.id.prayerItem3Icon)
    ImageView mPrayerItem3Icon;
    @BindView(R.id.prayerItem3Time)
    TextView mPrayerItem3Time;
    @BindView(R.id.prayerItem3Notification)
    ImageView mPrayerItem3Notification;
    @BindView(R.id.prayerItem4)
    android.support.v7.widget.CardView mPrayerItem4;
    @BindView(R.id.prayerItem4Name)
    TextView mPrayerItem4Name;
    @BindView(R.id.prayerItem4Icon)
    ImageView mPrayerItem4Icon;
    @BindView(R.id.prayerItem4Time)
    TextView mPrayerItem4Time;
    @BindView(R.id.prayerItem4Notification)
    ImageView mPrayerItem4Notification;
    @BindView(R.id.prayerItem5)
    android.support.v7.widget.CardView mPrayerItem5;
    @BindView(R.id.prayerItem5Name)
    TextView mPrayerItem5Name;
    @BindView(R.id.prayerItem5Icon)
    ImageView mPrayerItem5Icon;
    @BindView(R.id.prayerItem5Time)
    TextView mPrayerItem5Time;
    @BindView(R.id.prayerItem5Notification)
    ImageView mPrayerItem5Notification;
    @BindView(R.id.prayerItem6)
    android.support.v7.widget.CardView mPrayerItem6;
    @BindView(R.id.prayerItem6Name)
    TextView mPrayerItem6Name;
    @BindView(R.id.prayerItem6Icon)
    ImageView mPrayerItem6Icon;
    @BindView(R.id.prayerItem6Time)
    TextView mPrayerItem6Time;
    @BindView(R.id.prayerItem6Notification)
    ImageView mPrayerItem6Notification;
    /**
     * ButterKnife Code
     **/

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<Boolean> prayerNotificationStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mawaqeet_elsalah);
        ButterKnife.bind(this);
        if (getIntent().hasExtra("PrayerName")) {

        }

        locationManager = new LocationManager(this);
        locationManager.getCoarseLocation();

        SampleAlarmReceiver alarm = new SampleAlarmReceiver();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() + 5);
        alarm.setAlarm(getApplicationContext(), calendar);

        prayerNotificationStatus = loadPrayerNotificationStatus(this);
        initNotificationIcon();
    }

    @Override
    protected void onResume() {
        loadTodayData();
        super.onResume();
    }

    private void initNotificationIcon() {
        if (prayerNotificationStatus.get(0)) {
            mPrayerItem1Notification.setImageResource(R.drawable.notification_on);
            mPrayerItem1Notification.setAlpha(1f);
        } else {
            mPrayerItem1Notification.setImageResource(R.drawable.notifications_off);
            mPrayerItem1Notification.setAlpha(0.5f);
        }
        if (prayerNotificationStatus.get(1)) {
            mPrayerItem2Notification.setImageResource(R.drawable.notification_on);
            mPrayerItem2Notification.setAlpha(1f);
        } else {
            mPrayerItem2Notification.setImageResource(R.drawable.notifications_off);
            mPrayerItem2Notification.setAlpha(0.5f);
        }

        if (prayerNotificationStatus.get(2)) {
            mPrayerItem3Notification.setImageResource(R.drawable.notification_on);
            mPrayerItem3Notification.setAlpha(1f);
        } else {
            mPrayerItem3Notification.setImageResource(R.drawable.notifications_off);
            mPrayerItem3Notification.setAlpha(0.5f);
        }

        if (prayerNotificationStatus.get(3)) {
            mPrayerItem4Notification.setImageResource(R.drawable.notification_on);
            mPrayerItem4Notification.setAlpha(1f);
        } else {
            mPrayerItem4Notification.setImageResource(R.drawable.notifications_off);
            mPrayerItem4Notification.setAlpha(0.5f);
        }


        if (prayerNotificationStatus.get(4)) {
            mPrayerItem5Notification.setImageResource(R.drawable.notification_on);
            mPrayerItem5Notification.setAlpha(1f);
        } else {
            mPrayerItem5Notification.setImageResource(R.drawable.notifications_off);
            mPrayerItem5Notification.setAlpha(0.5f);
        }


        if (prayerNotificationStatus.get(5)) {
            mPrayerItem6Notification.setImageResource(R.drawable.notification_on);
            mPrayerItem6Notification.setAlpha(1f);
        } else {
            mPrayerItem6Notification.setImageResource(R.drawable.notifications_off);
            mPrayerItem6Notification.setAlpha(0.5f);
        }

    }

    private void savePrayerNotificationStatus(String key, boolean value) {
        SharedPreferences preferences = getSharedPreferences("PrayerTimeNotificationStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
        editor.apply();
    }


    private void loadTodayData() {
        sharedPreferences = getSharedPreferences("PrayerTime", Context.MODE_PRIVATE);

        Calendar cal = Calendar.getInstance();
        int dayIndex = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        month += 1;
        int year = cal.get(Calendar.YEAR);
        String key = "y_" + String.valueOf(year) + "_m_" + String.valueOf(month) + "_d_" + String.valueOf(dayIndex);
        Log.d(Constant.TAG, key);
        String jsonObject = sharedPreferences.getString(key, "");
        checkLocationEnable();
        if (checkAndRequestPermissions()) {

            currentLocation = locationManager.getCoarseLocation();
        }
        else {

        }
        if (jsonObject.equals("") || currentLocation!=null) {
            if (currentLocation!=null)
                loadPrayerTimeData(currentLocation.getLatitude(), currentLocation.getLongitude(), 5, month, year);
            else
                loadPrayerTimeData(21.422613, 39.826208, 4, month, year);
        }
        else {
            Data data = new Gson().fromJson(jsonObject, Data.class);
            Log.d(Constant.TAG, "data : " + data.toString());
            bindDataToView(data);
        }
    }

    private void bindDataToView(Data data) {

        String timeFajr = parseTimeToAmPm(data.getTimings().getFajr());
        String timeSunrise = parseTimeToAmPm(data.getTimings().getSunrise());
        String timeDhuhr = parseTimeToAmPm(data.getTimings().getDhuhr());
        String timeAsr = parseTimeToAmPm(data.getTimings().getAsr());
        String timeMaghrib = parseTimeToAmPm(data.getTimings().getMaghrib());
        String timeIsha = parseTimeToAmPm(data.getTimings().getIsha());

        mPrayerItem1.setCardBackgroundColor(this.getResources().getColor(R.color.color_green));
        mPrayerItem2.setCardBackgroundColor(this.getResources().getColor(R.color.color_green));
        mPrayerItem3.setCardBackgroundColor(this.getResources().getColor(R.color.color_green));
        mPrayerItem4.setCardBackgroundColor(this.getResources().getColor(R.color.color_green));
        mPrayerItem5.setCardBackgroundColor(this.getResources().getColor(R.color.color_green));
        mPrayerItem6.setCardBackgroundColor(this.getResources().getColor(R.color.color_green));


        mCountry.setText(data.getMeta().getTimezone());
        if (checkNextTime(timeFajr)) {
            addProgressTime(timeFajr,timeIsha);
            mPrayerName.setText("الفجر");
            mPrayerTime.setText(timeFajr);
            mPrayerItem1.setCardBackgroundColor(this.getResources().getColor(R.color.color_green_2));

        } else if (checkNextTime(timeSunrise)) {
            addProgressTime(timeSunrise,timeFajr);
            mPrayerName.setText("الشروق");
            mPrayerTime.setText(timeSunrise);
            mPrayerItem2.setCardBackgroundColor(this.getResources().getColor(R.color.color_green_2));
        } else if (checkNextTime(timeDhuhr)) {
            addProgressTime(timeDhuhr,timeSunrise);
            mPrayerName.setText("الظهر");
            mPrayerTime.setText(timeDhuhr);
            mPrayerItem3.setCardBackgroundColor(this.getResources().getColor(R.color.color_green_2));
        } else if (checkNextTime(timeAsr)) {
            addProgressTime(timeAsr,timeDhuhr);
            mPrayerName.setText("العصر");
            mPrayerTime.setText(timeAsr);
            mPrayerItem4.setCardBackgroundColor(this.getResources().getColor(R.color.color_green_2));
        } else if (checkNextTime(timeMaghrib)) {
            addProgressTime(timeMaghrib , timeAsr);
            mPrayerName.setText("المغرب");
            mPrayerTime.setText(timeMaghrib);
            mPrayerItem5.setCardBackgroundColor(this.getResources().getColor(R.color.color_green_2));
        } else if (checkNextTime(timeIsha)) {
            addProgressTime(timeIsha,timeMaghrib);
            mPrayerName.setText("العشاء");
            mPrayerTime.setText(timeIsha);
            mPrayerItem6.setCardBackgroundColor(this.getResources().getColor(R.color.color_green_2));
        } else {
            addProgressTime(timeFajr,timeIsha);
            mPrayerName.setText("الفجر");
            mPrayerTime.setText(timeFajr);
            mPrayerItem1.setCardBackgroundColor(this.getResources().getColor(R.color.color_green_2));
        }
        mPrayerItem1Time.setText(timeFajr);
        mPrayerItem2Time.setText(timeSunrise);
        mPrayerItem3Time.setText(timeDhuhr);
        mPrayerItem4Time.setText(timeAsr);
        mPrayerItem5Time.setText(timeMaghrib);
        mPrayerItem6Time.setText(timeIsha);
    }

    String formattedTime;
    Calendar c;
    SimpleDateFormat tf;

    Date nextPrayerTime = null;

    CountDownTimer countDownTimer;
    private void addProgressTime(String nextPrayerTime , String lastTime) {



        tf = new SimpleDateFormat("HH:mm:ss");
        Log.d("StartTime", nextPrayerTime);

        long dif = getDiffractInMaltese(lastTime,nextPrayerTime);
        Log.d("DiffractTime", ""+dif);
        long factor = dif/100;
        long progress = dif /factor;

        countDownTimer = new CountDownTimer(getDiffractInMaltese(nextPrayerTime), 1000) {
            public void onTick(long millisUntilFinished) {
                mRemindTime.setText(msToString(millisUntilFinished));
            }

            public void onFinish() {
                loadTodayData();
            }

        }.start();

    }


    public void applyPrayerNotification(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.prayerItem1Notification:
                savePrayerNotificationStatus("FajrNotification", !prayerNotificationStatus.get(0));
                prayerNotificationStatus.set(0, !prayerNotificationStatus.get(0));
                break;
            case R.id.prayerItem2Notification:

                savePrayerNotificationStatus("SunriseNotification", !prayerNotificationStatus.get(1));
                prayerNotificationStatus.set(1, !prayerNotificationStatus.get(1));
                break;
            case R.id.prayerItem3Notification:

                savePrayerNotificationStatus("DhuhrNotification", !prayerNotificationStatus.get(2));
                prayerNotificationStatus.set(2, !prayerNotificationStatus.get(2));
                break;
            case R.id.prayerItem4Notification:
                savePrayerNotificationStatus("AsrNotification", !prayerNotificationStatus.get(3));
                prayerNotificationStatus.set(3, !prayerNotificationStatus.get(3));

                break;
            case R.id.prayerItem5Notification:

                savePrayerNotificationStatus("MaghribNotification", !prayerNotificationStatus.get(4));
                prayerNotificationStatus.set(4, !prayerNotificationStatus.get(4));
                break;
            case R.id.prayerItem6Notification:
                savePrayerNotificationStatus("IshaNotification", !prayerNotificationStatus.get(5));
                prayerNotificationStatus.set(5, !prayerNotificationStatus.get(5));

                break;

        }
        initNotificationIcon();
    }

    private void loadPrayerTimeData(double latitude, double longitude, int method, final int month, final int year) {
        // show loading dialog
        httpGetRequest(this, getRequestUrl(latitude, longitude, method, month, year), new ConnectionManager.OnGetRequestFinishListener() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d(Constant.TAG, response.toString());
                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    Log.d(Constant.TAG, "dataArray size : " + dataArray.length());
                    if (dataArray.length() > 0) {
                        clearCashedData();
                        editor = sharedPreferences.edit();
                        editor.putInt("month", month);
                        editor.putInt("numberOfDays", dataArray.length());
                        String key = "";
                        for (int i = 0; i < dataArray.length(); i++) {
                            key = "y_" + String.valueOf(year) + "_m_" + String.valueOf(month) + "_d_" + String.valueOf((i + 1));
                            Log.d(Constant.TAG, key);
                            editor.putString(key, dataArray.get(i).toString());
                        }
                        editor.commit();
                        editor.apply();

                        loadTodayData();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //dismiss dialog
            }

            @Override
            public void onFailure(String message) {
                //dismiss dialog
            }
        });

    }

    private void clearCashedData() {
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }


    LocationManager locationManager;
    Location currentLocation;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    private boolean checkAndRequestPermissions() {
        int locationPermission1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int locationPermission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission1 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (locationPermission2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d(Constant.TAG, "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(Constant.TAG, "location services permission granted");
                        currentLocation = locationManager.getCoarseLocation();
                        loadTodayData();
                    } else {
                        Log.d(Constant.TAG, "Some permissions are not granted ask again ");
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                            showDialogOK("Allow Location Permission To Get ",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        } else {
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("ok", okListener)
                .setNegativeButton("cancel", okListener)
                .create()
                .show();
    }


    private void checkLocationEnable() {
        android.location.LocationManager locationManager;
        locationManager = (android.location.LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MawaqeetElsalahActivity.this);
            builder.setTitle("تفعيل تحديد الموقع");
            builder.setMessage("قم بالسماح للتطبيق لاستخدام الموقع الخاص بك");
            builder.setPositiveButton("فتح الاعدادات", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, 2);

                }
            });
            builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
    }


    private MediaPlayer mMediaPlayer;

    private void playAzanSound() {
        mMediaPlayer = new MediaPlayer();

        mMediaPlayer = MediaPlayer.create(this, R.raw.azan_abdel_baset);

        try {
            final AudioManager audioManager = (AudioManager) this
                    .getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void stopAzanSound() {
        mMediaPlayer.stop();
    }

    @Override
    protected void onStop() {
        if (mMediaPlayer != null)
            stopAzanSound();
        super.onStop();
    }



    private MediaPlayer mAudioPlayer = null;
    private void initMediaPlayer() {
        if (mAudioPlayer != null) {
            Log.d("PlayAzan", "Stopping Athan");
            if (mAudioPlayer.isPlaying()) { mAudioPlayer.stop(); }
            mAudioPlayer.release();
            mAudioPlayer = null;
        }

        if (null == mAudioPlayer) {
            mAudioPlayer = new MediaPlayer();
            String path = "android.resource://" + getPackageName() + "/" +R.raw.azan_abdel_baset;

            try {
                mAudioPlayer.setDataSource(this, Uri.parse(path));
                mAudioPlayer.setOnPreparedListener(this);
                mAudioPlayer.setOnErrorListener(this);
                mAudioPlayer.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);
                mAudioPlayer.prepareAsync(); // prepare async to not block main thread
                Log.d("PlayAzan", "Audio player prepared asynchronously!");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("PlayAzan", e.getMessage(), e);
            }
        }
    }

    @Override

    protected void onPause() {
        if (mAudioPlayer!=null)
            if (mAudioPlayer.isPlaying()) {
            mAudioPlayer.stop();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mAudioPlayer != null) {
            Log.d("PlayAzan", "Stopping Athan");
            if (mAudioPlayer.isPlaying()) {
                mAudioPlayer.stop();
            }
            mAudioPlayer.release();
            mAudioPlayer = null;
        }
        super.onDestroy();

    }

    @Override
    public void onAudioFocusChange(int focusChange) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mAudioPlayer.start();
        Log.i("PlayAzan", "Audio started playing!");
        if(!mAudioPlayer.isPlaying()) {
            Log.w("PlayAzan", "Problem in playing audio");
        }
    }
}