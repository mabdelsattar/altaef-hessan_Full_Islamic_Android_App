package com.daralmathour.altaefhessan.Activities;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daralmathour.altaefhessan.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


import static com.daralmathour.altaefhessan.Constant.DOWNLOADED_FILE_NAME_AL_Hozife_AYAT;
import static com.daralmathour.altaefhessan.Constant.DOWNLOADED_FILE_NAME_AL_Hozife_PAGE;
import static com.daralmathour.altaefhessan.Constant.DOWNLOADED_FILE_NAME_AL_Sodes_AYAT;
import static com.daralmathour.altaefhessan.Constant.DOWNLOADED_FILE_NAME_AL_Sodes_PAGE;
import static com.daralmathour.altaefhessan.Constant.DOWNLOADED_FILE_NAME_AL_menshwe_AYAT;
import static com.daralmathour.altaefhessan.Constant.DOWNLOADED_FILE_NAME_AL_menshwe_PAGE;
import static com.daralmathour.altaefhessan.Constant.DOWNLOADED_FILE_NAME_Abd_Elbaset_AYAT;
import static com.daralmathour.altaefhessan.Constant.DOWNLOADED_FILE_NAME_Abd_Elbaset_PAGE;

public class DownloadActivity extends AppCompatActivity {

    String mLine;

    //int counter= 0;
    String _Url;
    String _fileName;
    Spinner songerSpinner;
    public static final String DOWNLOADED_FILE_NAME_AL_Sodes_AYAT = "QuranKarim_AL_Sodes_AYAT";
    public static final String DOWNLOADED_FILE_NAME_AL_Hozife_AYAT = "QuranKarim_AL_Hozife_AYAT";
    public static final String DOWNLOADED_FILE_NAME_AL_menshwe_AYAT = "QuranKarim_AL_menshwe_AYAT";
    public static final String DOWNLOADED_FILE_NAME_Abd_Elbaset_AYAT = "QuranKarim_Abd_Elbaset_AYAT";

    public static int selectedSound = 0;
    Button btnDownload;
    TextView textView;
    ProgressBar progressBarcustom;
    Spinner songersSpinnercustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        songersSpinnercustom = (Spinner) findViewById(R.id.songersSpinnercustom);
        progressBarcustom = (ProgressBar) findViewById(R.id.progressBarcustom);
        btnDownload = (Button) findViewById(R.id.btnDownload);
        textView = (TextView) findViewById(R.id.textView);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnDownload.setBackgroundColor(Color.WHITE);
                btnDownload.setTextColor(Color.WHITE);
                textView.setText(textView.getText()+" Loading...");
                btnDownload.setVisibility(View.GONE);


                btnDownload.setVisibility(View.GONE);
                songersSpinnercustom.setActivated(false);



                if (selectedSound == 0)
                    _Url = "http://mojamah.net/appfiles/AL_Sodes-/";
                if (selectedSound == 1)
                    _Url = "http://mojamah.net/appfiles/AL_Hozife-/";
                if (selectedSound == 2)
                    _Url = "http://mojamah.net/appfiles/Abd_Elbaset-/";
                if (selectedSound == 3)
                    _Url = "http://mojamah.net/appfiles/AL_menshwe-/";

                final DownloadActivity.DownloadTask downloadTask = new DownloadActivity.DownloadTask(DownloadActivity.this, "");
                downloadTask.execute(_Url + "nofileName");



            }
        });

        ArrayList<String> list = new ArrayList<>();
        list.add("عبد الرحمن السديس");
        list.add("علي الحذيفي");
        list.add("عبد الباسط عبد الصمد");
        list.add("محمد صديق المنشاوي");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list);
        //  ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
        //        R.array.planets_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        songersSpinnercustom.setAdapter(adapter);


        //init base
        //Set the listener for when each option is clicked.
        songersSpinnercustom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSound = position;
                SetProgressBarValues();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
               // Log.v(TAG,"Permission is granted");
                return true;
            } else {

               // Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
           // Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    void SetProgressBarValues() {

        if (selectedSound == 0) {
            _fileName = DOWNLOADED_FILE_NAME_AL_Sodes_AYAT;
        }
        if (selectedSound == 1) {
            _fileName = DOWNLOADED_FILE_NAME_AL_Hozife_AYAT;
        }
        if (selectedSound == 2) {
            _fileName = DOWNLOADED_FILE_NAME_Abd_Elbaset_AYAT;
        }
        if (selectedSound == 3) {
            _fileName = DOWNLOADED_FILE_NAME_AL_menshwe_AYAT;
        }
        if(isStoragePermissionGranted()) {
            int count = GetFilesCountInPath();
            float ratio = (float) count / (float) 6236;
            int barvalue = (int) (ratio * 100);
            if (barvalue == 0)
                barvalue++;

            progressBarcustom.setProgress(barvalue);
            textView.setText(count + " / " + 6236);
            if(btnDownload.getVisibility() == View.GONE)
                textView.setText(count + " / " + 6236+" Loading...");
        }

    }

    int GetFilesCountInPath() {
        File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + _fileName);
        // File file = new File(root + File.separator + fileName);
        if (!root.exists()) {
            root.mkdirs();
            return 0;
        } else {
            return root.listFiles().length;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
          //  Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
            if(isStoragePermissionGranted()) {
                int count = GetFilesCountInPath();
                float ratio = (float) count / (float) 6236;
                int barvalue = (int) (ratio * 100);
                if (barvalue == 0)
                    barvalue++;

                progressBarcustom.setProgress(barvalue);
                textView.setText(count + " / " + 6236);
                if(btnDownload.getVisibility() == View.GONE)
                    textView.setText(count + " / " + 6236+" Loading...");

            }
        }else{
            Toast.makeText(getApplicationContext(),"برجاء تفعيل صلاحية الاستخدام",Toast.LENGTH_LONG).show();
        }
    }
    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;
        private String fileNameo;

        public DownloadTask(Context context, String fileName) {
            this.context = context;
            this.fileNameo = fileName;
        }

        @Override
        protected String doInBackground(String... sUrl) {

            BufferedReader reader = null;
            try {
                reader = new BufferedReader(
                        new InputStreamReader(getAssets().open("ayat_names.txt")));

                while ((mLine = reader.readLine()) != null) {
                    //process line
                    //  mLine += "";
                    String fileName =mLine;

                    String _Url = "";

                    if (selectedSound == 0)
                        _Url = "http://mojamah.net/appfiles/AL_Sodes-/";
                    if (selectedSound == 1)
                        _Url = "http://mojamah.net/appfiles/AL_Hozife-/";
                    if (selectedSound == 2)
                        _Url = "http://mojamah.net/appfiles/Abd_Elbaset-/";
                    if (selectedSound == 3)
                        _Url = "http://mojamah.net/appfiles/AL_menshwe-/";

                    File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + _fileName);
                    root.mkdirs();
                    File output = new File(root + File.separator + fileName);
                    if(!output.exists()) {
                     //region download file here
                        InputStream input = null;
                        OutputStream outputfile = null;
                        HttpURLConnection connection = null;
                        try {
                            URL url = new URL(sUrl[0]);
                            connection = (HttpURLConnection) url.openConnection();
                            connection.connect();

                            // expect HTTP 200 OK, so we don't mistakenly save error report
                            // instead of the file
                            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                                return "Server returned HTTP " + connection.getResponseCode()
                                        + " " + connection.getResponseMessage();
                            }

                            // this will be useful to display download percentage
                            // might be -1: server did not report the length
                            int fileLength = connection.getContentLength();

                            // download the file
                            input = connection.getInputStream();
                            String _fileName ="";

                            if (selectedSound == 0)
                                _fileName = DOWNLOADED_FILE_NAME_AL_Sodes_AYAT;
                            if (selectedSound == 1)
                                _fileName = DOWNLOADED_FILE_NAME_AL_Hozife_AYAT;
                            if (selectedSound == 2)
                                _fileName = DOWNLOADED_FILE_NAME_Abd_Elbaset_AYAT;
                            if (selectedSound == 3)
                                _fileName = DOWNLOADED_FILE_NAME_AL_menshwe_AYAT;

                            File rootfile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + _fileName);
                            rootfile.mkdirs();
                            outputfile = new FileOutputStream(rootfile + File.separator + fileName);
                            byte data[] = new byte[4096];
                            long total = 0;
                            int count;
                            while ((count = input.read(data)) != -1) {
                                // allow canceling with back button
                                if (isCancelled()) {
                                    input.close();
                                    return null;
                                }
                                total += count;
                                // publishing the progress....
                                if (fileLength > 0) // only if total length is known
                                    publishProgress((int) (total * 100 / fileLength));
                                outputfile.write(data, 0, count);
                            }
                        } catch (Exception e) {
                            return e.toString();
                        } finally {
                            try {
                                if (outputfile != null)
                                    outputfile.close();
                                if (input != null)
                                    input.close();
                            } catch (IOException ignored) {
                            }

                            if (connection != null)
                                connection.disconnect();
                        }
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                SetProgressBarValues();
                                // change UI elements here
                            }
                        });
                        //endregion
                    }

                }

            } catch (IOException e) {
                //log the exception
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        //log the exception
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnDownload.setVisibility(View.GONE);
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            SetProgressBarValues();
         //   mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
          /*  mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
            mProgressDialog.setCancelable(false);*/
           // SetProgressBarValues();
            SetProgressBarValues();
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            //SetProgressBarValues();
            //mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            else {
               // Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
                SetProgressBarValues();
            }
        }

    }


}
