package com.daralmathour.altaefhessan.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
        startActivity(new Intent(HomeActivity.this,SebhaActivity.class));

    }

    public void openQuran(View view) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int savepos = prefs.getInt("saveindex", -1);
        if(savepos == -1)
           startActivity(new Intent(HomeActivity.this,FehrestActivity.class));
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
        startActivity(new Intent(HomeActivity.this,EtgahElqeblaActivity.class));

    }

    public void openMoqtatafaat(View view) {
        startActivity(new Intent(HomeActivity.this,MoqtatafatActivity.class));

    }

    public void openTwaselMaana(View view) {
        startActivity(new Intent(HomeActivity.this,TawasselMaanaActivity.class));

    }
}
