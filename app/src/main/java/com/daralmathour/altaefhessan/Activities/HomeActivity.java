package com.daralmathour.altaefhessan.Activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.daralmathour.altaefhessan.R;

public class HomeActivity extends AppCompatActivity {


    private TextView sebhaTitle;
    private TextView quraanKarremTitle;
    private TextView mawaqeet_elsalah_title;
    private TextView etgahElqeblaTitle;
    private TextView moqtatafatTitle;
    private TextView tawasselMaanaTitle;
    private TextView pageTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sebhaTitle = (TextView)findViewById( R.id.sebha_title );
        quraanKarremTitle = (TextView)findViewById( R.id.quraan_karrem_title );
        mawaqeet_elsalah_title = (TextView)findViewById( R.id.mawaqeet_elsalah_title );
        etgahElqeblaTitle = (TextView)findViewById( R.id.etgah_elqebla_title );
        moqtatafatTitle = (TextView)findViewById( R.id.moqtatafat_title );
        tawasselMaanaTitle = (TextView)findViewById( R.id.tawassel_maana_title );
        pageTitle = (TextView)findViewById( R.id.pageTitle );
        Typeface face1 = Typeface.createFromAsset(getAssets(),
                "UTHMAN-TAHATN1-BOLD_0.OTF");
        Typeface face2 = Typeface.createFromAsset(getAssets(),
                "UTHMAN-TAHATN1-BOLD_0.OTF");
        pageTitle.setTypeface(face1);
        sebhaTitle.setTypeface(face2);
        quraanKarremTitle.setTypeface(face2);
        mawaqeet_elsalah_title.setTypeface(face2);
        etgahElqeblaTitle.setTypeface(face2);
        moqtatafatTitle.setTypeface(face2);
        tawasselMaanaTitle.setTypeface(face2);

    }

    public void openSepha(View view) {
        startActivity(new Intent(HomeActivity.this,AzkarMainActivity.class));

    }

    public void openQuran(View view) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int savepos = prefs.getInt("saveindex", -1);
        if(savepos == -1) {
            Intent intent = new Intent(HomeActivity.this, QuranActivity.class);
            intent.putExtra("index", 0);
            intent.putExtra("position", 0);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(HomeActivity.this, QuranActivity.class);
            intent.putExtra("fromhome", true);
            startActivity(intent);

        }


    }

    public void openMawaqeetElsalah(View view) {
        startActivity(new Intent(HomeActivity.this,MawaqeetElsalahActivity.class));

    }

    public void openElqepla(View view) {
        startActivity(new Intent(HomeActivity.this,MainOActivity.class));

    }

    public void openMoqtatafaat(View view) {
        startActivity(new Intent(HomeActivity.this,MoqtatafatActivity.class));
    }

    public void openTwaselMaana(View view) {
       // startActivity(new Intent(HomeActivity.this,TawasselMaanaActivity.class));
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }
    }
}
