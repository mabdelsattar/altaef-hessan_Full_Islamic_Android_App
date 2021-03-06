package com.daralmathour.altaefhessan.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daralmathour.altaefhessan.*;
import com.daralmathour.altaefhessan.AyatInfo.AllAyatInforamation;
import com.daralmathour.altaefhessan.AyatInfo.AyahInformation;
import com.daralmathour.altaefhessan.AyatInfo.MyView;
import com.daralmathour.altaefhessan.AyatInfo.PageInformation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
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


public class QuranActivity extends AppCompatActivity {


    public  static int AyayNumber = -1;
    public  static int SoraAyahNumber = -1;
    public  static int heightRatio;
    AllAyatInforamation allAyatInforamation=null;
    RelativeLayout quran_layout;
    ArrayList<Integer> allPages;
    int index = 603;
   // TextView soraName;
    CheckBox checkRepeat;
    public  static  int selectedSound= 0;
    public Constant constObj;
    AppConfigurations appConfigurations;
    boolean fromHome;
    boolean fromSearch;
    ImageView btnPlay;
    ImageView btnDownloadAyat;
    String SoraName;
    Spinner songerSpinner;
    ViewPager mViewPager;
    final Context context = this;
    private ImageView Tafseerbutton;
    private float[] lastTouchDownXY = new float[2];
    private static float xPos,yPos;

    //public  static int currentPos = 0;
    public  static int savepos = -1;
public  static  boolean isSaved= false;


    void ShareImage()
    {
        int myindex = -1;
        if(savepos != -1)
            myindex = savepos;
        else
            myindex = 603-index;

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),allPages.get(myindex));

        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.parseColor("#DBF7D1"));
        canvas.drawBitmap(bitmap, 0, 0, null);
        String path = getExternalCacheDir()+"/shareimage.jpg";
        java.io.OutputStream out = null;
        java.io.File file=new java.io.File(path);
        try { out = new java.io.FileOutputStream(file); newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); out.flush(); out.close(); } catch (Exception e) { e.printStackTrace(); } path=file.getPath();
        Uri bmpUri = Uri.parse("file://"+path);

        Intent shareIntent = new Intent();
        shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        shareIntent.setType("image/jpg");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.daralmathour.altaefhessan");


        startActivity(Intent.createChooser(shareIntent,"المشاركة عبر"));

    }

    private int getActionBarHeight() {
        int actionBarHeight = getSupportActionBar().getHeight();
        if (actionBarHeight != 0)
            return actionBarHeight;
        final TypedValue tv = new TypedValue();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        } else if (getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, tv, true))
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        return actionBarHeight;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran);
        mViewPager = (ViewPager) findViewById(R.id.pager);


        ImageView btnSearch = (ImageView)findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuranActivity.this,SearchQuranActivity.class));
                finish();
            }
        });
        appConfigurations =new AppConfigurations();
        quran_layout = (RelativeLayout)findViewById(R.id.quran_layout);
        checkRepeat = (CheckBox) findViewById(R.id.checkRepeat);
        songerSpinner = (Spinner) findViewById(R.id.songersSpinner);

        Tafseerbutton =(ImageView) findViewById(R.id.btnTafseer);
        btnDownloadAyat =(ImageView) findViewById(R.id.btnDownloadAyat);

        btnDownloadAyat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuranActivity.this,DownloadActivity.class));
            }
        });

        Tafseerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //currentPos;
                // custom dialog
                final Dialog dialog = new Dialog(context);

                dialog.setContentView(R.layout.tafseerdialog);
                dialog.setTitle("تَدبُّر الآيَات");

                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.tafseerTxt);
                text.setText(constObj.TafseerList.get(603-mViewPager.getCurrentItem()));


                Button dialogButton = (Button) dialog.findViewById(R.id.btnCloseDialog);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });
        constObj = new Constant();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        savepos = prefs.getInt("saveindex", -1);
        savepos = 603- savepos;




        ImageView btnShare= (ImageView)findViewById(R.id.btnshare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareImage();
            }
        });


        ImageView btnMenu= (ImageView)findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuranActivity.this, FehrestActivity.class));
                finish();
            }
        });




        final ImageView btnSaveBookMark= (ImageView)findViewById(R.id.btnBookMark);


        if(savepos == index) {
            btnSaveBookMark.setImageResource(R.drawable.bookmark_marked);
            isSaved = true;
        }

        btnSaveBookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isSaved) {
                    btnSaveBookMark.setImageResource(R.drawable.bookmark_marked);
                    isSaved = true;
                    savepos = mViewPager.getCurrentItem();
                    //save index to shared preference
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("saveindex", savepos);
                    editor.putString("soraname", SoraName);
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"تم حفظ العلامه",Toast.LENGTH_LONG).show();


                }
                else{
                    btnSaveBookMark.setImageResource(R.drawable.bookmark);
                    isSaved = false;
                    //remove index -1 from shared prefernece
                    savepos = -1;
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("saveindex", -1);
                    editor.putString("soraname", null);

                    editor.commit();


                }

            }
        });


        SoraName = appConfigurations.allSoar.get(0).Name;
        //m = new MediaPlayer();
        btnPlay = (ImageView) findViewById(R.id.btnPlay);
        getSupportActionBar().hide();
        allPages = new ArrayList<>();

        initPagesDrawables();
        CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(this, allPages);


        mViewPager.setAdapter(mCustomPagerAdapter);

        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // save the X,Y coordinates
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    startTime = event.getEventTime();

                    int childCount = quran_layout.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View vv = quran_layout.getChildAt(i);
                        if (vv instanceof MyView) {
                            quran_layout.removeView(vv);
                            i--;
                            childCount--;
                        }
                    }

                    AyayNumber = -1;

                    xPos = event.getX();
                    yPos = event.getY();

                    if (allAyatInforamation == null)
                        allAyatInforamation = new AllAyatInforamation();


                    PageInformation pageInformation = allAyatInforamation.AllQuranPages.get(603-mViewPager.getCurrentItem());
                    ArrayList<AyahInformation> ayahInformations = pageInformation.getPageAyat();

                       int tempindex=-1;
                    for (AyahInformation ayah :
                            ayahInformations) {

                         tempindex++;
                        int y_start = (int) (((int) (mViewPager.getHeight()) * ayah.yStart) / 2024);
                        int y_end = (int) (((int) (mViewPager.getHeight()) * ayah.yEnd) / 2024);
                        int x_start = (int) (((int) (mViewPager.getWidth()) * ayah.xStart) / 1536);
                        int x_end = (int) (((int) (mViewPager.getWidth()) * ayah.xEnd) / 1536);


                        if (yPos >= y_start
                                && yPos < y_end) {


                            //Validate X position --one line
                            if(xPos > x_end && xPos <= x_start) {
                              //  Toast.makeText(getApplicationContext(), ayah.getAyahContent(), Toast.LENGTH_LONG).show();
                                //print(i.Aya_num)
                                //print(i.content)

                                childCount = quran_layout.getChildCount();
                                for (int i = 0; i < childCount; i++) {
                                    View vv = quran_layout.getChildAt(i);
                                    if (vv instanceof MyView) {
                                        quran_layout.removeView(vv);
                                        i--;
                                        childCount--;

                                    }
                                }

                                //check if multi lines
                                if((float)(y_end-y_start)/mViewPager.getHeight() > 0.08)
                                {
                                    int numberofRects= (int)((float)(y_end-y_start)/mViewPager.getHeight()/0.065);
                                    numberofRects++;

                                    int rectHeight= (int)((float)mViewPager.getHeight()*0.065);
                                    for(int i=0 ; i<numberofRects ; i++)
                                    {
                                        if(i == 0){
                                            MyView myView = new MyView(QuranActivity.this, (int) 0, y_start + heightRatio, x_start , (int)heightRatio+y_start+rectHeight);
                                            quran_layout.addView(myView);
                                        }
                                        else if(i == numberofRects - 1)
                                        {
                                            MyView myView = new MyView(QuranActivity.this, (int) x_end, (i*rectHeight)+y_start + heightRatio, mViewPager.getWidth(), (int)heightRatio+y_start+rectHeight+(i*rectHeight));
                                            quran_layout.addView(myView);
                                        }else{
                                            MyView myView = new MyView(QuranActivity.this, (int) 0, (i*rectHeight)+y_start + heightRatio, mViewPager.getWidth(), (int)heightRatio+y_start+rectHeight+(i*rectHeight));
                                            quran_layout.addView(myView);
                                        }

                                    }


                                }else{
                                    MyView myView = new MyView(QuranActivity.this, (int) x_start, y_start + heightRatio, x_end, y_end + heightRatio);
                                    quran_layout.addView(myView);
                                }


                                //MyView myView = new MyView(QuranActivity.this, (int) x_start, y_start + 100, x_end, y_end + 100);
                                //quran_layout.addView(myView);

                                AyayNumber = ayah.getAyahNumber();
                                SoraAyahNumber  =ayah.getSoraNumber();

                                if(player != null)
                                {
                                    player.pause();
                                    isPlaying= false;
                                    player = null;
                                    btnPlay.setImageResource(R.drawable.ic_play_arrow_white_24dp);

                                }


                                break;
                            }else if((float)(y_end-y_start)/mViewPager.getHeight() > 0.065){
                                int rectoHeight= (int)((float)mViewPager.getHeight()*0.065);


                                if(tempindex != ayahInformations.size()-1)
                                {

                                    int next_y_start = (int) (((int) (mViewPager.getHeight()) * ayahInformations.get(tempindex+1).yStart) / 2024);

                                    if(xPos < x_end && y_end-next_y_start < rectoHeight && y_end-yPos <rectoHeight)
                                        continue;
                                }

                              /*  if(xPos < x_end)
                                {
                                    ayah = ayahInformations.get(tempindex+1);
                                    tempindex++;
                                    y_start = (int) (((int) (mViewPager.getHeight()) * ayah.yStart) / 2024);
                                    y_end = (int) (((int) (mViewPager.getHeight()) * ayah.yEnd) / 2024);
                                    x_start = (int) (((int) (mViewPager.getWidth()) * ayah.xStart) / 1536);
                                    x_end = (int) (((int) (mViewPager.getWidth()) * ayah.xEnd) / 1536);

                                }*/

                                //Toast.makeText(getApplicationContext(), ayah.getAyahContent(), //Toast.LENGTH_LONG).show();
                                //print(i.Aya_num)
                                //print(i.content)
                                childCount = quran_layout.getChildCount();
                                for (int i = 0; i < childCount; i++) {
                                    View vv = quran_layout.getChildAt(i);
                                    if (vv instanceof MyView) {
                                        quran_layout.removeView(vv);
                                        i--;
                                        childCount--;
                                    }
                                }

                                AyayNumber  = ayah.getAyahNumber();
                                SoraAyahNumber  =ayah.getSoraNumber();
                                if(player != null)
                                {
                                    player.pause();
                                    isPlaying= false;
                                    player = null;
                                    btnPlay.setImageResource(R.drawable.ic_play_arrow_white_24dp);

                                }

                                //check if multi lines
                                if((float)(y_end-y_start)/mViewPager.getHeight() > 0.08)
                                {
                                    int numberofRects= (int)((float)(y_end-y_start)/mViewPager.getHeight()/0.065);
                                    numberofRects++;

                                    int  rectHeight= (int)((float)mViewPager.getHeight()*0.065);
                                    for(int i=0 ; i<numberofRects ; i++)
                                    {
                                        if(i == 0){
                                            MyView myView = new MyView(QuranActivity.this, (int) 0, y_start + heightRatio, x_start , (int)heightRatio+y_start+rectHeight);
                                            quran_layout.addView(myView);
                                        }
                                        else if(i == numberofRects - 1)
                                        {
                                            MyView myView = new MyView(QuranActivity.this, (int) x_end, (i*rectHeight)+y_start + heightRatio, mViewPager.getWidth(), (int)heightRatio+y_start+rectHeight+(i*rectHeight));
                                            quran_layout.addView(myView);
                                        }else{
                                            MyView myView = new MyView(QuranActivity.this, (int) 0, (i*rectHeight)+y_start + heightRatio, mViewPager.getWidth(), (int)heightRatio+y_start+rectHeight+(i*rectHeight));
                                            quran_layout.addView(myView);
                                        }

                                    }


                                }else{
                                    MyView myView = new MyView(QuranActivity.this, (int) x_start, y_start + heightRatio, x_end, y_end + heightRatio);
                                    quran_layout.addView(myView);
                                }
                                break;
                            }
                            //CGRect(x: x_end, y:y_start+Int((topBar.frame.size.height + bottomBar.frame.size.height))
                            //, width:x_start-x_end, height: y_end-y_start))
                        }




                        //  Toast.makeText(getBaseContext(), "x ="+xPos+" ,y ="+yPos, Toast.LENGTH_SHORT).show();

                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    //record the end time
                    endTime = event.getEventTime();
                    Log.d("LC", "IN UP");
                    if(endTime - startTime > 500){
                        //we have a 1000ms duration touch
                        //propagate your own event
                        Log.d("LC", "time touched greater than 1000ms");
                       // Toast.makeText(getBaseContext(), "Hello 123", Toast.LENGTH_SHORT).show();
                        startTime=0;
                        endTime=0;
                        return true; //notify that you handled this event (do not propagate)
                    }
                }else if(event.getAction() == MotionEvent.ACTION_MOVE){
                    Log.d("LC", "IN move");
                    endTime=0;
                }

                // let the touch event pass on to whoever needs it
                return false;
            }
        };
        mViewPager.setOnTouchListener(touchListener);



        if (getIntent() != null && getIntent().getExtras() != null) {


            fromHome =  getIntent().getExtras().getBoolean("fromhome",false);
            fromSearch =  getIntent().getExtras().getBoolean("fromSearch",false);
            if(fromSearch)
            {

                int w,h;
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                w=pref.getInt("w", 0);
                h=pref.getInt("h", 0);

                int page =Integer.valueOf(getIntent().getExtras().getString("Page"))-1;
                index = page;
                SoraName = getIntent().getExtras().getString("SoraName");
                index = 603 - index;
                ///savepos = 603 - savepos;
                if (savepos == index) {
                    btnSaveBookMark.setImageResource(R.drawable.bookmark_marked);
                    isSaved = true;
                }

                mViewPager.setCurrentItem(index);


                int SearcherAyahNum = Integer.valueOf(getIntent().getExtras().getString("AyahNumber"));
                AyayNumber = SearcherAyahNum;
                //draw Ayah Number
                AllAyatInforamation allAyatInforamation=null;
                allAyatInforamation = new AllAyatInforamation();

             PageInformation pageInformation=  allAyatInforamation.AllQuranPages.get(page);
             ArrayList<AyahInformation> pageAyat = pageInformation.pageAyat;
                AyahInformation ayah;
             for(int o= 0 ; o< pageAyat.size(); o++)
             {
                 if(pageAyat.get(o).ayahNumber == SearcherAyahNum
                         && appConfigurations.allSoar.get(pageAyat.get(o).soraNumber-1).Name.equals(SoraName))
                 {
                     ayah =pageAyat.get(o);
                     //Draw Ayah with all information

                     int childCount = quran_layout.getChildCount();
                     for (int k = 0; k < childCount; k++) {
                         View vv = quran_layout.getChildAt(k);
                         if (vv instanceof MyView) {
                             quran_layout.removeView(vv);
                             k--;
                             childCount--;
                         }
                     }


                     int y_start = (int) (((int) (h) * ayah.yStart) / 2024);
                     int y_end = (int) (((int) (h) * ayah.yEnd) / 2024);
                     int x_start = (int) (((int) (w) * ayah.xStart) / 1536);
                     int x_end = (int) (((int) (w) * ayah.xEnd) / 1536);

//check if multi lines
                     if((float)(y_end-y_start)/h > 0.08)
                     {
                         int numberofRects= (int)((float)(y_end-y_start)/h/0.065);
                         numberofRects++;

                         int rectHeight= (int)((float)h*0.065);
                         for(int i=0 ; i<numberofRects ; i++)
                         {
                             if(i == 0){
                                 MyView myView = new MyView(QuranActivity.this, (int) 0, y_start + heightRatio, x_start , (int)heightRatio+y_start+rectHeight);
                                 quran_layout.addView(myView);
                             }
                             else if(i == numberofRects - 1)
                             {
                                 MyView myView = new MyView(QuranActivity.this, (int) x_end, (i*rectHeight)+y_start + heightRatio,w, (int)heightRatio+y_start+rectHeight+(i*rectHeight));
                                 quran_layout.addView(myView);
                             }else{
                                 MyView myView = new MyView(QuranActivity.this, (int) 0, (i*rectHeight)+y_start + heightRatio, w, (int)heightRatio+y_start+rectHeight+(i*rectHeight));
                                 quran_layout.addView(myView);
                             }
                         }

                     }

                     else{
                         MyView myView = new MyView(QuranActivity.this, (int) x_start, y_start + heightRatio, x_end, y_end + heightRatio);
                         quran_layout.addView(myView);
                     }


                     //MyView myView = new MyView(QuranActivity.this, (int) x_start, y_start + 100, x_end, y_end + 100);
                     //quran_layout.addView(myView);

                     AyayNumber = ayah.getAyahNumber();
                     SoraAyahNumber  =ayah.getSoraNumber();

                     //till here
                     break;
                 }
             }



            }
            else {
                index = getIntent().getExtras().getInt("index");
                if (!fromHome) {
                    SoraName = appConfigurations.allSoar.get(getIntent().getExtras().getInt("position", 0)).Name;
                    index = 603 - index;
                  //  savepos = 603 - savepos;
                    //currentPos = index;
                    if (603-savepos == index) {
                        btnSaveBookMark.setImageResource(R.drawable.bookmark_marked);
                        isSaved = true;
                    }
                } else {
                    //getting it from shared preference
                    prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SoraName = prefs.getString("soraname", "");
                    // if(savepos == currentPos) {
                    btnSaveBookMark.setImageResource(R.drawable.bookmark_marked);
                    isSaved = true;
                    //}


                }
            }

        }

        ArrayList<String> list = new ArrayList<>();
        list.add("عبد الرحمن السديس");
        list.add("علي الحذيفي");
        list.add("عبد الباسط عبد الصمد");
        list.add("محمد صديق المنشاوي");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list);
        //  ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
        //        R.array.planets_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        songerSpinner.setAdapter(adapter);

        //Set the listener for when each option is clicked.
        songerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Change the selected item's text color
                //keep width and height here
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("w", mViewPager.getWidth());        // Saving integer
                editor.putInt("h", mViewPager.getHeight());        // Saving integer

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;
                int width = displayMetrics.widthPixels;


                Rect rectangle = new Rect();
                Window window = getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
                int statusBarHeight = rectangle.top;
                int contentViewTop =
                        window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
                int titleBarHeight= contentViewTop - statusBarHeight;

                Log.i("*** Elenasys :: ", "StatusBar Height= " + statusBarHeight + " , TitleBar Height = " + titleBarHeight);

                heightRatio =getActionBarHeight();//(int)(0.090125*height);
// Save the changes in SharedPreferences
                editor.commit(); // commit changes


                selectedSound = position;
                if (view != null)
                    ((TextView) view).setTextColor(Color.WHITE);
                btnPlay.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                if (isPlaying)
                {
                    player.pause();
                    player=null;
                    isPlaying=false;
                }
                else
                {
                    player=null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });







        if(fromHome)
            mViewPager.setCurrentItem(603-savepos);
        else
            mViewPager.setCurrentItem(index);


        if(savepos == index) {
            btnSaveBookMark.setImageResource(R.drawable.bookmark_marked);
            isSaved = true;
        }

        // getSupportActionBar().setTitle(SoraName);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               /* int childCount = quran_layout.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View vv = quran_layout.getChildAt(i);
                    if (vv instanceof MyView) {
                        quran_layout.removeView(vv);
                        i--;
                        childCount--;
                    }
                }
                AyayNumber = -1;*/


            }

            @Override
            public void onPageSelected(int position) {
                int childCount = quran_layout.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View vv = quran_layout.getChildAt(i);
                    if (vv instanceof MyView) {
                        quran_layout.removeView(vv);
                        i--;
                        childCount--;
                    }
                }

                AyayNumber = -1;



                //you have position on page
                index = 603-position;
                setMp3FileName(603-mViewPager.getCurrentItem());
                if(603-savepos == position) {
                    btnSaveBookMark.setImageResource(R.drawable.bookmark_marked);
                    isSaved = true;
                }else{
                    btnSaveBookMark.setImageResource(R.drawable.bookmark);
                    isSaved = false;
                }


                position = 603-position;
              //  soraName.setText(getSoraNamebyPage(position));

                btnPlay.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                if (isPlaying)
                {
                    if(player != null)
                    player.pause();
                    player=null;
                    isPlaying=false;
                }
                else
                {
                    player=null;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

       // soraName.setText(SoraName);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) //pause
                {
                    player.pause();
                    isPlaying=false;
                    btnPlay.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                    length=player.getCurrentPosition();
                }
                else
                {
                    if (player != null)//resume
                    {
                        btnPlay.setImageResource(R.drawable.ic_pause_circle_outline_white_24dp);
                        player.seekTo(length);
                        player.start();
                        isPlaying=true;
                    }
                    else
                    {
                      /*  setMp3FileName(603-mViewPager.getCurrentItem());
                        checkPer();*/

                        AyahInformation nextAyah = null;
                        int nextPage = mViewPager.getCurrentItem();

                        if(AyayNumber != -1) {
                           // if (isLastAyah())
                             //   nextPage = mViewPager.getCurrentItem() - 1;
                            nextAyah = GetCurrentAyah(nextPage);
                        } else{
                            nextAyah = FirstAyah(nextPage);
                        }

                        if(nextPage != mViewPager.getCurrentItem())
                            mViewPager.setCurrentItem(nextPage);

                        //Setting Ayah Number
                        AyayNumber = nextAyah.getAyahNumber();
                        SoraAyahNumber  =nextAyah.getSoraNumber();

                        //drawing ayah
                        int childCount = quran_layout.getChildCount();
                        for (int k = 0; k < childCount; k++) {
                            View vv = quran_layout.getChildAt(k);
                            if (vv instanceof MyView) {
                                quran_layout.removeView(vv);
                                k--;
                                childCount--;
                            }
                        }
                        int w,h;
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                        w=pref.getInt("w", 0);
                        h=pref.getInt("h", 0);

                        int y_start = (int) (((int) (h) * nextAyah.yStart) / 2024);
                        int y_end = (int) (((int) (h) * nextAyah.yEnd) / 2024);
                        int x_start = (int) (((int) (w) * nextAyah.xStart) / 1536);
                        int x_end = (int) (((int) (w) * nextAyah.xEnd) / 1536);

//check if multi lines
                        if((float)(y_end-y_start)/h > 0.08)
                        {
                            int numberofRects= (int)((float)(y_end-y_start)/h/0.065);
                            numberofRects++;

                            int rectHeight= (int)((float)h*0.065);
                            for(int i=0 ; i<numberofRects ; i++)
                            {
                                if(i == 0){
                                    MyView myView = new MyView(QuranActivity.this, (int) 0, y_start + heightRatio, x_start , (int)heightRatio+y_start+rectHeight);
                                    quran_layout.addView(myView);
                                }
                                else if(i == numberofRects - 1)
                                {
                                    MyView myView = new MyView(QuranActivity.this, (int) x_end, (i*rectHeight)+y_start + heightRatio,w, (int)heightRatio+y_start+rectHeight+(i*rectHeight));
                                    quran_layout.addView(myView);
                                }else{
                                    MyView myView = new MyView(QuranActivity.this, (int) 0, (i*rectHeight)+y_start + heightRatio, w, (int)heightRatio+y_start+rectHeight+(i*rectHeight));
                                    quran_layout.addView(myView);
                                }
                            }

                        }

                        else {
                            MyView myView = new MyView(QuranActivity.this, (int) x_start, y_start + heightRatio, x_end, y_end + heightRatio);
                            quran_layout.addView(myView);
                        }

                        //them play so
                        setMp3FileName(603 - mViewPager.getCurrentItem());
                        checkPer();


                    }
                }



            }
        });

        int yh= mViewPager.getHeight();
    }
    private void setMp3FileName(int page)
    {

        if(AyayNumber == -1) {
            //S_SoraNumber - PageNumber.mp3
            int sora = getSoraIndexbyPage(page);
            String soraStr = "";
            String pageStr = "";

            page++;
            if (page < 10)
                pageStr = "00" + page;
            else if (page < 100)
                pageStr = "0" + page;
            else
                pageStr = "" + page;

            if (sora < 10)
                soraStr = "00" + sora;
            else if (sora < 100)
                soraStr = "0" + sora;
            else
                soraStr = "" + sora;

//http://mojamah.net/appfiles/S_002 - 018.mp3
            fileName = "S_" + soraStr + " - " + pageStr + ".mp3";

        }else{
//S_001-007.mp3
            String soraStr = "";
            String AyahStr = "";

            if (AyayNumber < 10)
                AyahStr = "00" + AyayNumber;
            else if (AyayNumber < 100)
                AyahStr = "0" + AyayNumber;
            else
                AyahStr = "" + AyayNumber;


            if (SoraAyahNumber < 10)
                soraStr = "00" + SoraAyahNumber;
            else if (SoraAyahNumber < 100)
                soraStr = "0" + SoraAyahNumber;
            else
                soraStr = "" + SoraAyahNumber;
                if(AyayNumber == 0)
                     fileName =  "S_001-001.mp3";
                else
                     fileName = "S_" + soraStr + "-" + AyahStr + ".mp3";
        }

    }

    /*
        public void playBeep(int n) {
            try {
                if (m.isPlaying()) {
                    btnPlay.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                    m.stop();
                    m.release();
                    m = new MediaPlayer();
                }
               else {
                   // AssetFileDescriptor descriptor = getAssets().openFd("t1.mp3");
                   // m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                    // m.setDataSource("http://www.mp3quran.net/newMedia.php?id=1&file=http://server6.mp3quran.net/hamad/001.mp3");
                    m = MediaPlayer.create(this, Uri.parse("http://www.mp3quran.net/newMedia.php?id=1&file=http://server6.mp3quran.net/hamad/001.mp3"));
                    m.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            btnPlay.setImageResource(R.drawable.ic_pause_circle_outline_white_24dp);

                            mp.setVolume(1f, 1f);
                            mp.setLooping(false);//configuration
                            //  progress.dismiss();
                            mp.start();
                        }
                    });
                  //  descriptor.close();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    */

    public  String getSoraNamebyPage(int position) {


        for (int i = 0; i < appConfigurations.allSoar.size() - 1; i++) {
            if (position + 1 >= appConfigurations.allSoar.get(i).FromPage
                    && position + 1 < appConfigurations.allSoar.get(i + 1).FromPage)
                return appConfigurations.allSoar.get(i).Name;
        }
        if (position + 1 >= appConfigurations.allSoar.get(appConfigurations.allSoar.size() - 1).FromPage)
            return appConfigurations.allSoar.get(appConfigurations.allSoar.size() - 1).Name;

        return appConfigurations.allSoar.get(0).Name;
    }
    public  int getSoraIndexbyPage(int position) {


        for (int i = 0; i < appConfigurations.allSoar.size() - 1; i++) {
            if (position + 1 >= appConfigurations.allSoar.get(i).FromPage
                    && position + 1 < appConfigurations.allSoar.get(i + 1).FromPage)
                return i+1;
        }

        if (position + 1 >= appConfigurations.allSoar.get(appConfigurations.allSoar.size() - 1).FromPage)
            return appConfigurations.allSoar.size();

        return 0;
    }
    private void initPagesDrawables() {
        allPages.add(R.drawable.page604);
        allPages.add(R.drawable.page603);
        allPages.add(R.drawable.page602);
        allPages.add(R.drawable.page601);
        allPages.add(R.drawable.page600);
        allPages.add(R.drawable.page599);
        allPages.add(R.drawable.page598);
        allPages.add(R.drawable.page597);
        allPages.add(R.drawable.page596);
        allPages.add(R.drawable.page595);
        allPages.add(R.drawable.page594);
        allPages.add(R.drawable.page593);
        allPages.add(R.drawable.page592);
        allPages.add(R.drawable.page591);
        allPages.add(R.drawable.page590);
        allPages.add(R.drawable.page589);
        allPages.add(R.drawable.page588);
        allPages.add(R.drawable.page587);
        allPages.add(R.drawable.page586);
        allPages.add(R.drawable.page585);
        allPages.add(R.drawable.page584);
        allPages.add(R.drawable.page583);
        allPages.add(R.drawable.page582);
        allPages.add(R.drawable.page581);
        allPages.add(R.drawable.page580);
        allPages.add(R.drawable.page579);
        allPages.add(R.drawable.page578);
        allPages.add(R.drawable.page577);
        allPages.add(R.drawable.page576);
        allPages.add(R.drawable.page575);
        allPages.add(R.drawable.page574);
        allPages.add(R.drawable.page573);
        allPages.add(R.drawable.page572);
        allPages.add(R.drawable.page571);
        allPages.add(R.drawable.page570);
        allPages.add(R.drawable.page569);
        allPages.add(R.drawable.page568);
        allPages.add(R.drawable.page567);
        allPages.add(R.drawable.page566);
        allPages.add(R.drawable.page565);
        allPages.add(R.drawable.page564);
        allPages.add(R.drawable.page563);
        allPages.add(R.drawable.page562);
        allPages.add(R.drawable.page561);
        allPages.add(R.drawable.page560);
        allPages.add(R.drawable.page559);
        allPages.add(R.drawable.page558);
        allPages.add(R.drawable.page557);
        allPages.add(R.drawable.page556);
        allPages.add(R.drawable.page555);
        allPages.add(R.drawable.page554);
        allPages.add(R.drawable.page553);
        allPages.add(R.drawable.page552);
        allPages.add(R.drawable.page551);
        allPages.add(R.drawable.page550);
        allPages.add(R.drawable.page549);
        allPages.add(R.drawable.page548);
        allPages.add(R.drawable.page547);
        allPages.add(R.drawable.page546);
        allPages.add(R.drawable.page545);
        allPages.add(R.drawable.page544);
        allPages.add(R.drawable.page543);
        allPages.add(R.drawable.page542);
        allPages.add(R.drawable.page541);
        allPages.add(R.drawable.page540);
        allPages.add(R.drawable.page539);
        allPages.add(R.drawable.page538);
        allPages.add(R.drawable.page537);
        allPages.add(R.drawable.page536);
        allPages.add(R.drawable.page535);
        allPages.add(R.drawable.page534);
        allPages.add(R.drawable.page533);
        allPages.add(R.drawable.page532);
        allPages.add(R.drawable.page531);
        allPages.add(R.drawable.page530);
        allPages.add(R.drawable.page529);
        allPages.add(R.drawable.page528);
        allPages.add(R.drawable.page527);
        allPages.add(R.drawable.page526);
        allPages.add(R.drawable.page525);
        allPages.add(R.drawable.page524);
        allPages.add(R.drawable.page523);
        allPages.add(R.drawable.page522);
        allPages.add(R.drawable.page521);
        allPages.add(R.drawable.page520);
        allPages.add(R.drawable.page519);
        allPages.add(R.drawable.page518);
        allPages.add(R.drawable.page517);
        allPages.add(R.drawable.page516);
        allPages.add(R.drawable.page515);
        allPages.add(R.drawable.page514);
        allPages.add(R.drawable.page513);
        allPages.add(R.drawable.page512);
        allPages.add(R.drawable.page511);
        allPages.add(R.drawable.page510);
        allPages.add(R.drawable.page509);
        allPages.add(R.drawable.page508);
        allPages.add(R.drawable.page507);
        allPages.add(R.drawable.page506);
        allPages.add(R.drawable.page505);
        allPages.add(R.drawable.page504);
        allPages.add(R.drawable.page503);
        allPages.add(R.drawable.page502);
        allPages.add(R.drawable.page501);
        allPages.add(R.drawable.page500);
        allPages.add(R.drawable.page499);
        allPages.add(R.drawable.page498);
        allPages.add(R.drawable.page497);
        allPages.add(R.drawable.page496);
        allPages.add(R.drawable.page495);
        allPages.add(R.drawable.page494);
        allPages.add(R.drawable.page493);
        allPages.add(R.drawable.page492);
        allPages.add(R.drawable.page491);
        allPages.add(R.drawable.page490);
        allPages.add(R.drawable.page489);
        allPages.add(R.drawable.page488);
        allPages.add(R.drawable.page487);
        allPages.add(R.drawable.page486);
        allPages.add(R.drawable.page485);
        allPages.add(R.drawable.page484);
        allPages.add(R.drawable.page483);
        allPages.add(R.drawable.page482);
        allPages.add(R.drawable.page481);
        allPages.add(R.drawable.page480);
        allPages.add(R.drawable.page479);
        allPages.add(R.drawable.page478);
        allPages.add(R.drawable.page477);
        allPages.add(R.drawable.page476);
        allPages.add(R.drawable.page475);
        allPages.add(R.drawable.page474);
        allPages.add(R.drawable.page473);
        allPages.add(R.drawable.page472);
        allPages.add(R.drawable.page471);
        allPages.add(R.drawable.page470);
        allPages.add(R.drawable.page469);
        allPages.add(R.drawable.page468);
        allPages.add(R.drawable.page467);
        allPages.add(R.drawable.page466);
        allPages.add(R.drawable.page465);
        allPages.add(R.drawable.page464);
        allPages.add(R.drawable.page463);
        allPages.add(R.drawable.page462);
        allPages.add(R.drawable.page461);
        allPages.add(R.drawable.page460);
        allPages.add(R.drawable.page459);
        allPages.add(R.drawable.page458);
        allPages.add(R.drawable.page457);
        allPages.add(R.drawable.page456);
        allPages.add(R.drawable.page455);
        allPages.add(R.drawable.page454);
        allPages.add(R.drawable.page453);
        allPages.add(R.drawable.page452);
        allPages.add(R.drawable.page451);
        allPages.add(R.drawable.page450);
        allPages.add(R.drawable.page449);
        allPages.add(R.drawable.page448);
        allPages.add(R.drawable.page447);
        allPages.add(R.drawable.page446);
        allPages.add(R.drawable.page445);
        allPages.add(R.drawable.page444);
        allPages.add(R.drawable.page443);
        allPages.add(R.drawable.page442);
        allPages.add(R.drawable.page441);
        allPages.add(R.drawable.page440);
        allPages.add(R.drawable.page439);
        allPages.add(R.drawable.page438);
        allPages.add(R.drawable.page437);
        allPages.add(R.drawable.page436);
        allPages.add(R.drawable.page435);
        allPages.add(R.drawable.page434);
        allPages.add(R.drawable.page433);
        allPages.add(R.drawable.page432);
        allPages.add(R.drawable.page431);
        allPages.add(R.drawable.page430);
        allPages.add(R.drawable.page429);
        allPages.add(R.drawable.page428);
        allPages.add(R.drawable.page427);
        allPages.add(R.drawable.page426);
        allPages.add(R.drawable.page425);
        allPages.add(R.drawable.page424);
        allPages.add(R.drawable.page423);
        allPages.add(R.drawable.page422);
        allPages.add(R.drawable.page421);
        allPages.add(R.drawable.page420);
        allPages.add(R.drawable.page419);
        allPages.add(R.drawable.page418);
        allPages.add(R.drawable.page417);
        allPages.add(R.drawable.page416);
        allPages.add(R.drawable.page415);
        allPages.add(R.drawable.page414);
        allPages.add(R.drawable.page413);
        allPages.add(R.drawable.page412);
        allPages.add(R.drawable.page411);
        allPages.add(R.drawable.page410);
        allPages.add(R.drawable.page409);
        allPages.add(R.drawable.page408);
        allPages.add(R.drawable.page407);
        allPages.add(R.drawable.page406);
        allPages.add(R.drawable.page405);
        allPages.add(R.drawable.page404);
        allPages.add(R.drawable.page403);
        allPages.add(R.drawable.page402);
        allPages.add(R.drawable.page401);
        allPages.add(R.drawable.page400);
        allPages.add(R.drawable.page399);
        allPages.add(R.drawable.page398);
        allPages.add(R.drawable.page397);
        allPages.add(R.drawable.page396);
        allPages.add(R.drawable.page395);
        allPages.add(R.drawable.page394);
        allPages.add(R.drawable.page393);
        allPages.add(R.drawable.page392);
        allPages.add(R.drawable.page391);
        allPages.add(R.drawable.page390);
        allPages.add(R.drawable.page389);
        allPages.add(R.drawable.page388);
        allPages.add(R.drawable.page387);
        allPages.add(R.drawable.page386);
        allPages.add(R.drawable.page385);
        allPages.add(R.drawable.page384);
        allPages.add(R.drawable.page383);
        allPages.add(R.drawable.page382);
        allPages.add(R.drawable.page381);
        allPages.add(R.drawable.page380);
        allPages.add(R.drawable.page379);
        allPages.add(R.drawable.page378);
        allPages.add(R.drawable.page377);
        allPages.add(R.drawable.page376);
        allPages.add(R.drawable.page375);
        allPages.add(R.drawable.page374);
        allPages.add(R.drawable.page373);
        allPages.add(R.drawable.page372);
        allPages.add(R.drawable.page371);
        allPages.add(R.drawable.page370);
        allPages.add(R.drawable.page369);
        allPages.add(R.drawable.page368);
        allPages.add(R.drawable.page367);
        allPages.add(R.drawable.page366);
        allPages.add(R.drawable.page365);
        allPages.add(R.drawable.page364);
        allPages.add(R.drawable.page363);
        allPages.add(R.drawable.page362);
        allPages.add(R.drawable.page361);
        allPages.add(R.drawable.page360);
        allPages.add(R.drawable.page359);
        allPages.add(R.drawable.page358);
        allPages.add(R.drawable.page357);
        allPages.add(R.drawable.page356);
        allPages.add(R.drawable.page355);
        allPages.add(R.drawable.page354);
        allPages.add(R.drawable.page353);
        allPages.add(R.drawable.page352);
        allPages.add(R.drawable.page351);
        allPages.add(R.drawable.page350);
        allPages.add(R.drawable.page349);
        allPages.add(R.drawable.page348);
        allPages.add(R.drawable.page347);
        allPages.add(R.drawable.page346);
        allPages.add(R.drawable.page345);
        allPages.add(R.drawable.page344);
        allPages.add(R.drawable.page343);
        allPages.add(R.drawable.page342);
        allPages.add(R.drawable.page341);
        allPages.add(R.drawable.page340);
        allPages.add(R.drawable.page339);
        allPages.add(R.drawable.page338);
        allPages.add(R.drawable.page337);
        allPages.add(R.drawable.page336);
        allPages.add(R.drawable.page335);
        allPages.add(R.drawable.page334);
        allPages.add(R.drawable.page333);
        allPages.add(R.drawable.page332);
        allPages.add(R.drawable.page331);
        allPages.add(R.drawable.page330);
        allPages.add(R.drawable.page329);
        allPages.add(R.drawable.page328);
        allPages.add(R.drawable.page327);
        allPages.add(R.drawable.page326);
        allPages.add(R.drawable.page325);
        allPages.add(R.drawable.page324);
        allPages.add(R.drawable.page323);
        allPages.add(R.drawable.page322);
        allPages.add(R.drawable.page321);
        allPages.add(R.drawable.page320);
        allPages.add(R.drawable.page319);
        allPages.add(R.drawable.page318);
        allPages.add(R.drawable.page317);
        allPages.add(R.drawable.page316);
        allPages.add(R.drawable.page315);
        allPages.add(R.drawable.page314);
        allPages.add(R.drawable.page313);
        allPages.add(R.drawable.page312);
        allPages.add(R.drawable.page311);
        allPages.add(R.drawable.page310);
        allPages.add(R.drawable.page309);
        allPages.add(R.drawable.page308);
        allPages.add(R.drawable.page307);
        allPages.add(R.drawable.page306);
        allPages.add(R.drawable.page305);
        allPages.add(R.drawable.page304);
        allPages.add(R.drawable.page303);
        allPages.add(R.drawable.page302);
        allPages.add(R.drawable.page301);
        allPages.add(R.drawable.page300);
        allPages.add(R.drawable.page299);
        allPages.add(R.drawable.page298);
        allPages.add(R.drawable.page297);
        allPages.add(R.drawable.page296);
        allPages.add(R.drawable.page295);
        allPages.add(R.drawable.page294);
        allPages.add(R.drawable.page293);
        allPages.add(R.drawable.page292);
        allPages.add(R.drawable.page291);
        allPages.add(R.drawable.page290);
        allPages.add(R.drawable.page289);
        allPages.add(R.drawable.page288);
        allPages.add(R.drawable.page287);
        allPages.add(R.drawable.page286);
        allPages.add(R.drawable.page285);
        allPages.add(R.drawable.page284);
        allPages.add(R.drawable.page283);
        allPages.add(R.drawable.page282);
        allPages.add(R.drawable.page281);
        allPages.add(R.drawable.page280);
        allPages.add(R.drawable.page279);
        allPages.add(R.drawable.page278);
        allPages.add(R.drawable.page277);
        allPages.add(R.drawable.page276);
        allPages.add(R.drawable.page275);
        allPages.add(R.drawable.page274);
        allPages.add(R.drawable.page273);
        allPages.add(R.drawable.page272);
        allPages.add(R.drawable.page271);
        allPages.add(R.drawable.page270);
        allPages.add(R.drawable.page269);
        allPages.add(R.drawable.page268);
        allPages.add(R.drawable.page267);
        allPages.add(R.drawable.page266);
        allPages.add(R.drawable.page265);
        allPages.add(R.drawable.page264);
        allPages.add(R.drawable.page263);
        allPages.add(R.drawable.page262);
        allPages.add(R.drawable.page261);
        allPages.add(R.drawable.page260);
        allPages.add(R.drawable.page259);
        allPages.add(R.drawable.page258);
        allPages.add(R.drawable.page257);
        allPages.add(R.drawable.page256);
        allPages.add(R.drawable.page255);
        allPages.add(R.drawable.page254);
        allPages.add(R.drawable.page253);
        allPages.add(R.drawable.page252);
        allPages.add(R.drawable.page251);
        allPages.add(R.drawable.page250);
        allPages.add(R.drawable.page249);
        allPages.add(R.drawable.page248);
        allPages.add(R.drawable.page247);
        allPages.add(R.drawable.page246);
        allPages.add(R.drawable.page245);
        allPages.add(R.drawable.page244);
        allPages.add(R.drawable.page243);
        allPages.add(R.drawable.page242);
        allPages.add(R.drawable.page241);
        allPages.add(R.drawable.page240);
        allPages.add(R.drawable.page239);
        allPages.add(R.drawable.page238);
        allPages.add(R.drawable.page237);
        allPages.add(R.drawable.page236);
        allPages.add(R.drawable.page235);
        allPages.add(R.drawable.page234);
        allPages.add(R.drawable.page233);
        allPages.add(R.drawable.page232);
        allPages.add(R.drawable.page231);
        allPages.add(R.drawable.page230);
        allPages.add(R.drawable.page229);
        allPages.add(R.drawable.page228);
        allPages.add(R.drawable.page227);
        allPages.add(R.drawable.page226);
        allPages.add(R.drawable.page225);
        allPages.add(R.drawable.page224);
        allPages.add(R.drawable.page223);
        allPages.add(R.drawable.page222);
        allPages.add(R.drawable.page221);
        allPages.add(R.drawable.page220);
        allPages.add(R.drawable.page219);
        allPages.add(R.drawable.page218);
        allPages.add(R.drawable.page217);
        allPages.add(R.drawable.page216);
        allPages.add(R.drawable.page215);
        allPages.add(R.drawable.page214);
        allPages.add(R.drawable.page213);
        allPages.add(R.drawable.page212);
        allPages.add(R.drawable.page211);
        allPages.add(R.drawable.page210);
        allPages.add(R.drawable.page209);
        allPages.add(R.drawable.page208);
        allPages.add(R.drawable.page207);
        allPages.add(R.drawable.page206);
        allPages.add(R.drawable.page205);
        allPages.add(R.drawable.page204);
        allPages.add(R.drawable.page203);
        allPages.add(R.drawable.page202);
        allPages.add(R.drawable.page201);
        allPages.add(R.drawable.page200);
        allPages.add(R.drawable.page199);
        allPages.add(R.drawable.page198);
        allPages.add(R.drawable.page197);
        allPages.add(R.drawable.page196);
        allPages.add(R.drawable.page195);
        allPages.add(R.drawable.page194);
        allPages.add(R.drawable.page193);
        allPages.add(R.drawable.page192);
        allPages.add(R.drawable.page191);
        allPages.add(R.drawable.page190);
        allPages.add(R.drawable.page189);
        allPages.add(R.drawable.page188);
        allPages.add(R.drawable.page187);
        allPages.add(R.drawable.page186);
        allPages.add(R.drawable.page185);
        allPages.add(R.drawable.page184);
        allPages.add(R.drawable.page183);
        allPages.add(R.drawable.page182);
        allPages.add(R.drawable.page181);
        allPages.add(R.drawable.page180);
        allPages.add(R.drawable.page179);
        allPages.add(R.drawable.page178);
        allPages.add(R.drawable.page177);
        allPages.add(R.drawable.page176);
        allPages.add(R.drawable.page175);
        allPages.add(R.drawable.page174);
        allPages.add(R.drawable.page173);
        allPages.add(R.drawable.page172);
        allPages.add(R.drawable.page171);
        allPages.add(R.drawable.page170);
        allPages.add(R.drawable.page169);
        allPages.add(R.drawable.page168);
        allPages.add(R.drawable.page167);
        allPages.add(R.drawable.page166);
        allPages.add(R.drawable.page165);
        allPages.add(R.drawable.page164);
        allPages.add(R.drawable.page163);
        allPages.add(R.drawable.page162);
        allPages.add(R.drawable.page161);
        allPages.add(R.drawable.page160);
        allPages.add(R.drawable.page159);
        allPages.add(R.drawable.page158);
        allPages.add(R.drawable.page157);
        allPages.add(R.drawable.page156);
        allPages.add(R.drawable.page155);
        allPages.add(R.drawable.page154);
        allPages.add(R.drawable.page153);
        allPages.add(R.drawable.page152);
        allPages.add(R.drawable.page151);
        allPages.add(R.drawable.page150);
        allPages.add(R.drawable.page149);
        allPages.add(R.drawable.page148);
        allPages.add(R.drawable.page147);
        allPages.add(R.drawable.page146);
        allPages.add(R.drawable.page145);
        allPages.add(R.drawable.page144);
        allPages.add(R.drawable.page143);
        allPages.add(R.drawable.page142);
        allPages.add(R.drawable.page141);
        allPages.add(R.drawable.page140);
        allPages.add(R.drawable.page139);
        allPages.add(R.drawable.page138);
        allPages.add(R.drawable.page137);
        allPages.add(R.drawable.page136);
        allPages.add(R.drawable.page135);
        allPages.add(R.drawable.page134);
        allPages.add(R.drawable.page133);
        allPages.add(R.drawable.page132);
        allPages.add(R.drawable.page131);
        allPages.add(R.drawable.page130);
        allPages.add(R.drawable.page129);
        allPages.add(R.drawable.page128);
        allPages.add(R.drawable.page127);
        allPages.add(R.drawable.page126);
        allPages.add(R.drawable.page125);
        allPages.add(R.drawable.page124);
        allPages.add(R.drawable.page123);
        allPages.add(R.drawable.page122);
        allPages.add(R.drawable.page121);
        allPages.add(R.drawable.page120);
        allPages.add(R.drawable.page119);
        allPages.add(R.drawable.page118);
        allPages.add(R.drawable.page117);
        allPages.add(R.drawable.page116);
        allPages.add(R.drawable.page115);
        allPages.add(R.drawable.page114);
        allPages.add(R.drawable.page113);
        allPages.add(R.drawable.page112);
        allPages.add(R.drawable.page111);
        allPages.add(R.drawable.page110);
        allPages.add(R.drawable.page109);
        allPages.add(R.drawable.page108);
        allPages.add(R.drawable.page107);
        allPages.add(R.drawable.page106);
        allPages.add(R.drawable.page105);
        allPages.add(R.drawable.page104);
        allPages.add(R.drawable.page103);
        allPages.add(R.drawable.page102);
        allPages.add(R.drawable.page101);
        allPages.add(R.drawable.page100);
        allPages.add(R.drawable.page099);
        allPages.add(R.drawable.page098);
        allPages.add(R.drawable.page097);
        allPages.add(R.drawable.page096);
        allPages.add(R.drawable.page095);
        allPages.add(R.drawable.page094);
        allPages.add(R.drawable.page093);
        allPages.add(R.drawable.page092);
        allPages.add(R.drawable.page091);
        allPages.add(R.drawable.page090);
        allPages.add(R.drawable.page089);
        allPages.add(R.drawable.page088);
        allPages.add(R.drawable.page087);
        allPages.add(R.drawable.page086);
        allPages.add(R.drawable.page085);
        allPages.add(R.drawable.page084);
        allPages.add(R.drawable.page083);
        allPages.add(R.drawable.page082);
        allPages.add(R.drawable.page081);
        allPages.add(R.drawable.page080);
        allPages.add(R.drawable.page079);
        allPages.add(R.drawable.page078);
        allPages.add(R.drawable.page077);
        allPages.add(R.drawable.page076);
        allPages.add(R.drawable.page075);
        allPages.add(R.drawable.page074);
        allPages.add(R.drawable.page073);
        allPages.add(R.drawable.page072);
        allPages.add(R.drawable.page071);
        allPages.add(R.drawable.page070);
        allPages.add(R.drawable.page069);
        allPages.add(R.drawable.page068);
        allPages.add(R.drawable.page067);
        allPages.add(R.drawable.page066);
        allPages.add(R.drawable.page065);
        allPages.add(R.drawable.page064);
        allPages.add(R.drawable.page063);
        allPages.add(R.drawable.page062);
        allPages.add(R.drawable.page061);
        allPages.add(R.drawable.page060);
        allPages.add(R.drawable.page059);
        allPages.add(R.drawable.page058);
        allPages.add(R.drawable.page057);
        allPages.add(R.drawable.page056);
        allPages.add(R.drawable.page055);
        allPages.add(R.drawable.page054);
        allPages.add(R.drawable.page053);
        allPages.add(R.drawable.page052);
        allPages.add(R.drawable.page051);
        allPages.add(R.drawable.page050);
        allPages.add(R.drawable.page049);
        allPages.add(R.drawable.page048);
        allPages.add(R.drawable.page047);
        allPages.add(R.drawable.page046);
        allPages.add(R.drawable.page045);
        allPages.add(R.drawable.page044);
        allPages.add(R.drawable.page043);
        allPages.add(R.drawable.page042);
        allPages.add(R.drawable.page041);
        allPages.add(R.drawable.page040);
        allPages.add(R.drawable.page039);
        allPages.add(R.drawable.page038);
        allPages.add(R.drawable.page037);
        allPages.add(R.drawable.page036);
        allPages.add(R.drawable.page035);
        allPages.add(R.drawable.page034);
        allPages.add(R.drawable.page033);
        allPages.add(R.drawable.page032);
        allPages.add(R.drawable.page031);
        allPages.add(R.drawable.page030);
        allPages.add(R.drawable.page029);
        allPages.add(R.drawable.page028);
        allPages.add(R.drawable.page027);
        allPages.add(R.drawable.page026);
        allPages.add(R.drawable.page025);
        allPages.add(R.drawable.page024);
        allPages.add(R.drawable.page023);
        allPages.add(R.drawable.page022);
        allPages.add(R.drawable.page021);
        allPages.add(R.drawable.page020);
        allPages.add(R.drawable.page019);
        allPages.add(R.drawable.page018);
        allPages.add(R.drawable.page017);
        allPages.add(R.drawable.page016);
        allPages.add(R.drawable.page015);
        allPages.add(R.drawable.page014);
        allPages.add(R.drawable.page013);
        allPages.add(R.drawable.page012);
        allPages.add(R.drawable.page011);
        allPages.add(R.drawable.page010);
        allPages.add(R.drawable.page009);
        allPages.add(R.drawable.page008);
        allPages.add(R.drawable.page007);
        allPages.add(R.drawable.page006);
        allPages.add(R.drawable.page005);
        allPages.add(R.drawable.page004);
        allPages.add(R.drawable.page003);
        allPages.add(R.drawable.page002);
        allPages.add(R.drawable.page001);


    }


    Boolean isPermissionsAvailable = false;
    ProgressDialog mProgressDialog;
    Item item;
    private String fileName = "";
    MediaPlayer player;
    private boolean isPlaying = false;
    int length;

    private long startTime=0;
    private long endTime=0;

    private void playMp3File() {

        try {

            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(item.getMp3Url());
            player.prepare();
            player.start();
            btnPlay.setImageResource(R.drawable.ic_pause_circle_outline_white_24dp);
            isPlaying = true;
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    if(checkRepeat.isChecked()){//repeat //TODO chekc on check box
                        isPlaying = false;
                        btnPlay.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                        if (player != null && isPlaying == true)
                            player.pause();
                        player = null;
                        // if(AyayNumber != -1) {

                        AyahInformation nextAyah = null;
                        int nextPage = mViewPager.getCurrentItem();

                        if (AyayNumber != -1) {
                            //if (isLastAyah())
                              //  nextPage = mViewPager.getCurrentItem() - 1;
                            nextAyah = GetCurrentAyah(nextPage);
                        } else {
                            nextAyah = FirstAyah(nextPage);
                        }

                        if (nextPage != mViewPager.getCurrentItem())
                            mViewPager.setCurrentItem(nextPage);

                        //Setting Ayah Number
                        AyayNumber = nextAyah.getAyahNumber();
                        SoraAyahNumber = nextAyah.getSoraNumber();

                        //drawing ayah
                        int childCount = quran_layout.getChildCount();
                        for (int k = 0; k < childCount; k++) {
                            View vv = quran_layout.getChildAt(k);
                            if (vv instanceof MyView) {
                                quran_layout.removeView(vv);
                                k--;
                                childCount--;
                            }
                        }
                        int w, h;
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                        w = pref.getInt("w", 0);
                        h = pref.getInt("h", 0);

                        int y_start = (int) (((int) (h) * nextAyah.yStart) / 2024);
                        int y_end = (int) (((int) (h) * nextAyah.yEnd) / 2024);
                        int x_start = (int) (((int) (w) * nextAyah.xStart) / 1536);
                        int x_end = (int) (((int) (w) * nextAyah.xEnd) / 1536);

//check if multi lines
                        if ((float) (y_end - y_start) / h > 0.08) {
                            int numberofRects = (int) ((float) (y_end - y_start) / h / 0.065);
                            numberofRects++;

                            int rectHeight = (int) ((float) h * 0.065);
                            for (int i = 0; i < numberofRects; i++) {
                                if (i == 0) {
                                    MyView myView = new MyView(QuranActivity.this, (int) 0, y_start + heightRatio, x_start, (int) heightRatio + y_start + rectHeight);
                                    quran_layout.addView(myView);
                                } else if (i == numberofRects - 1) {
                                    MyView myView = new MyView(QuranActivity.this, (int) x_end, (i * rectHeight) + y_start + heightRatio, w, (int) heightRatio + y_start + rectHeight + (i * rectHeight));
                                    quran_layout.addView(myView);
                                } else {
                                    MyView myView = new MyView(QuranActivity.this, (int) 0, (i * rectHeight) + y_start + heightRatio, w, (int) heightRatio + y_start + rectHeight + (i * rectHeight));
                                    quran_layout.addView(myView);
                                }
                            }

                        } else {
                            MyView myView = new MyView(QuranActivity.this, (int) x_start, y_start + heightRatio, x_end, y_end + heightRatio);
                            quran_layout.addView(myView);
                        }

                        //them play so
                        setMp3FileName(603 - mViewPager.getCurrentItem());
                        checkPer();

                    }

                    else {
                        isPlaying = false;
                        btnPlay.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                        if (player != null && isPlaying == true)
                            player.pause();
                        player = null;
                        // if(AyayNumber != -1) {

                        AyahInformation nextAyah = null;
                        int nextPage = mViewPager.getCurrentItem();

                        if (AyayNumber != -1) {
                            if (isLastAyah())
                                nextPage = mViewPager.getCurrentItem() - 1;

                            nextAyah = GetNextAyah(nextPage);
                        } else {
                            nextAyah = FirstAyah(nextPage);
                        }

                        if (nextPage != mViewPager.getCurrentItem())
                            mViewPager.setCurrentItem(nextPage);

                        //Setting Ayah Number
                        AyayNumber = nextAyah.getAyahNumber();
                        SoraAyahNumber = nextAyah.getSoraNumber();

                        //drawing ayah
                        int childCount = quran_layout.getChildCount();
                        for (int k = 0; k < childCount; k++) {
                            View vv = quran_layout.getChildAt(k);
                            if (vv instanceof MyView) {
                                quran_layout.removeView(vv);
                                k--;
                                childCount--;
                            }
                        }
                        int w, h;
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                        w = pref.getInt("w", 0);
                        h = pref.getInt("h", 0);

                        int y_start = (int) (((int) (h) * nextAyah.yStart) / 2024);
                        int y_end = (int) (((int) (h) * nextAyah.yEnd) / 2024);
                        int x_start = (int) (((int) (w) * nextAyah.xStart) / 1536);
                        int x_end = (int) (((int) (w) * nextAyah.xEnd) / 1536);

//check if multi lines
                        if ((float) (y_end - y_start) / h > 0.08) {
                            int numberofRects = (int) ((float) (y_end - y_start) / h / 0.065);
                            numberofRects++;

                            int rectHeight = (int) ((float) h * 0.065);
                            for (int i = 0; i < numberofRects; i++) {
                                if (i == 0) {
                                    MyView myView = new MyView(QuranActivity.this, (int) 0, y_start + heightRatio, x_start, (int) heightRatio + y_start + rectHeight);
                                    quran_layout.addView(myView);
                                } else if (i == numberofRects - 1) {
                                    MyView myView = new MyView(QuranActivity.this, (int) x_end, (i * rectHeight) + y_start + heightRatio, w, (int) heightRatio + y_start + rectHeight + (i * rectHeight));
                                    quran_layout.addView(myView);
                                } else {
                                    MyView myView = new MyView(QuranActivity.this, (int) 0, (i * rectHeight) + y_start + heightRatio, w, (int) heightRatio + y_start + rectHeight + (i * rectHeight));
                                    quran_layout.addView(myView);
                                }
                            }

                        } else {
                            MyView myView = new MyView(QuranActivity.this, (int) x_start, y_start + heightRatio, x_end, y_end + heightRatio);
                            quran_layout.addView(myView);
                        }

                        //them play so
                        setMp3FileName(603 - mViewPager.getCurrentItem());
                        checkPer();
                        //}
                    }
                }
            });

        } catch (Exception e) {
            // TODO: handle exceptionint
            int x= 3;
        }
    }

    private AyahInformation FirstAyah(int nextPage) {
        if (allAyatInforamation == null)
            allAyatInforamation = new AllAyatInforamation();

        PageInformation pageInformation = allAyatInforamation.AllQuranPages.get(603-nextPage);
        return  pageInformation.getPageAyat().get(0);//return first ayah
    }


    private AyahInformation GetNextAyah(int nextPage) {
        if (allAyatInforamation == null)
            allAyatInforamation = new AllAyatInforamation();

        AyahInformation ayahInformation = null;
        PageInformation pageInformation = allAyatInforamation.AllQuranPages.get(603-nextPage);
        if(nextPage != mViewPager.getCurrentItem()) //new page
        {
          return  pageInformation.getPageAyat().get(0);//return first ayah
        }
        else{ // still on the same page

             ArrayList<AyahInformation> pageAyat =pageInformation.getPageAyat();
             for(int i=0 ; i< pageAyat.size() ; i++){
                if(pageAyat.get(i).ayahNumber == AyayNumber && pageAyat.get(i).soraNumber == SoraAyahNumber)
                {
                    return  pageAyat.get(i+1);
                }
            }

        }
return  null;
    }

    private AyahInformation GetCurrentAyah(int CurrentPage) {
        if (allAyatInforamation == null)
            allAyatInforamation = new AllAyatInforamation();

        AyahInformation ayahInformation = null;
        PageInformation pageInformation = allAyatInforamation.AllQuranPages.get(603-CurrentPage);
        if(CurrentPage != mViewPager.getCurrentItem()) //new page
        {
            return  pageInformation.getPageAyat().get(0);//return first ayah
        }
        else{ // still on the same page

            ArrayList<AyahInformation> pageAyat =pageInformation.getPageAyat();
            for(int i=0 ; i< pageAyat.size() ; i++){
                if(pageAyat.get(i).ayahNumber == AyayNumber && pageAyat.get(i).soraNumber == SoraAyahNumber)
                {
                    return  pageAyat.get(i);
                }
            }
        }
        return  null;
    }
    private boolean isLastAyah() {
        if (allAyatInforamation == null)
            allAyatInforamation = new AllAyatInforamation();

        PageInformation pageInformation = allAyatInforamation.AllQuranPages.get(603-mViewPager.getCurrentItem());
        if(pageInformation.getPageAyat().get(pageInformation.getPageAyat().size()-1).ayahNumber == AyayNumber
                && pageInformation.getPageAyat().get(pageInformation.getPageAyat().size()-1).soraNumber == SoraAyahNumber)
            return true;

        return false;
    }

    private void downloadFile() {
        if (checkFileExists()) {
            item = loadDownloadedItem();
            playMp3File();
        } else {
            mProgressDialog = new ProgressDialog(QuranActivity.this);
            mProgressDialog.setMessage("جاري التحميل");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setCancelable(true);

            final DownloadTask downloadTask = new DownloadTask(QuranActivity.this, fileName);
            String _Url = "";
            if(AyayNumber == -1) {
                if (selectedSound == 0)
                    _Url = "http://mojamah.net/appfiles/AL_Sodes/";
                if (selectedSound == 1)
                    _Url = "http://mojamah.net/appfiles/AL_Hozife/";
                if (selectedSound == 2)
                    _Url = "http://mojamah.net/appfiles/Abd_Elbaset/";
                if (selectedSound == 3)
                    _Url = "http://mojamah.net/appfiles/AL_menshwe/";
            }else{
                if (selectedSound == 0)
                    _Url = "http://mojamah.net/appfiles/AL_Sodes-/";
                if (selectedSound == 1)
                    _Url = "http://mojamah.net/appfiles/AL_Hozife-/";
                if (selectedSound == 2)
                    _Url = "http://mojamah.net/appfiles/Abd_Elbaset-/";
                if (selectedSound == 3)
                    _Url = "http://mojamah.net/appfiles/AL_menshwe-/";
            }
            downloadTask.execute(_Url + fileName);

            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    downloadTask.cancel(true);
                }
            });
        }
    }

    public void checkPer() {
        if ((ContextCompat.checkSelfPermission(QuranActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_PHONE_STATE"}, 1);
            }
        } else {
            isPermissionsAvailable = true;
            downloadFile();
        }
    }

    private boolean checkFileExists() {
        String _fileName ="";
        if(AyayNumber == -1)
        {
            if(selectedSound == 0)
                _fileName = DOWNLOADED_FILE_NAME_AL_Sodes_PAGE;
            if(selectedSound == 1)
                _fileName = DOWNLOADED_FILE_NAME_AL_Hozife_PAGE;
            if(selectedSound == 2)
                _fileName = DOWNLOADED_FILE_NAME_Abd_Elbaset_PAGE;
            if(selectedSound == 3)
                _fileName = DOWNLOADED_FILE_NAME_AL_menshwe_PAGE;
        }else{
            if(selectedSound == 0)
                _fileName = DOWNLOADED_FILE_NAME_AL_Sodes_AYAT;
            if(selectedSound == 1)
                _fileName = DOWNLOADED_FILE_NAME_AL_Hozife_AYAT;
            if(selectedSound == 2)
                _fileName = DOWNLOADED_FILE_NAME_Abd_Elbaset_AYAT;
            if(selectedSound == 3)
                _fileName = DOWNLOADED_FILE_NAME_AL_menshwe_AYAT;
        }


        File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + _fileName);
        File file = new File(root + File.separator + fileName);
        if (file.exists())
            return true;
        else
            return false;
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;
        private String fileName;

        public DownloadTask(Context context, String fileName) {
            this.context = context;
            this.fileName = fileName;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
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
                if(AyayNumber == -1) {
                    if (selectedSound == 0)
                        _fileName = DOWNLOADED_FILE_NAME_AL_Sodes_PAGE;
                    if (selectedSound == 1)
                        _fileName = DOWNLOADED_FILE_NAME_AL_Hozife_PAGE;
                    if (selectedSound == 2)
                        _fileName = DOWNLOADED_FILE_NAME_Abd_Elbaset_PAGE;
                    if (selectedSound == 3)
                        _fileName = DOWNLOADED_FILE_NAME_AL_menshwe_PAGE;
                }else{
                    if (selectedSound == 0)
                        _fileName = DOWNLOADED_FILE_NAME_AL_Sodes_AYAT;
                    if (selectedSound == 1)
                        _fileName = DOWNLOADED_FILE_NAME_AL_Hozife_AYAT;
                    if (selectedSound == 2)
                        _fileName = DOWNLOADED_FILE_NAME_Abd_Elbaset_AYAT;
                    if (selectedSound == 3)
                        _fileName = DOWNLOADED_FILE_NAME_AL_menshwe_AYAT;
                }
                File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + _fileName);
                root.mkdirs();

                output = new FileOutputStream(root + File.separator + fileName);
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
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
            mProgressDialog.setCancelable(false);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
                downloadFile();
            }
        }

    }

    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    private Item loadDownloadedItem() {
        String _fileName ="";

        if(AyayNumber == -1)
        {
            if(selectedSound == 0)
                _fileName = DOWNLOADED_FILE_NAME_AL_Sodes_PAGE;
            if(selectedSound == 1)
                _fileName = DOWNLOADED_FILE_NAME_AL_Hozife_PAGE;
            if(selectedSound == 2)
                _fileName = DOWNLOADED_FILE_NAME_Abd_Elbaset_PAGE;
            if(selectedSound == 3)
                _fileName = DOWNLOADED_FILE_NAME_AL_menshwe_PAGE;
        }else{
            if(selectedSound == 0)
                _fileName = DOWNLOADED_FILE_NAME_AL_Sodes_AYAT;
            if(selectedSound == 1)
                _fileName = DOWNLOADED_FILE_NAME_AL_Hozife_AYAT;
            if(selectedSound == 2)
                _fileName = DOWNLOADED_FILE_NAME_Abd_Elbaset_AYAT;
            if(selectedSound == 3)
                _fileName = DOWNLOADED_FILE_NAME_AL_menshwe_AYAT;
        }

        File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + _fileName);
        File file = new File(root + File.separator + fileName);
        MediaMetadataRetriever md = new MediaMetadataRetriever();
        md.setDataSource(file.getAbsolutePath());
        String title = file.getName();
        String duration = md.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        if(duration == null || duration.equals("null"))
        {
            return null;
        }else {
            duration = milliSecondsToTimer(Long.parseLong(duration));

            String url = file.getAbsolutePath();
            Item item = new Item(url, title, duration);
            this.item =  new Item(url, title, duration);
            return item;
        }
    }


    @Override
    protected void onPause() {
        if (isPlaying)
        {
           // player.pause();
            //player=null;
        }
        else
        {
            //player=null;
        }
        super.onPause();

    }


}
