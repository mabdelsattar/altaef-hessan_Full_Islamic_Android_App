package com.daralmathour.altaefhessan.Activities;

import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;


public class userPreference extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean b = requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //addPreferencesFromResource(R.xml.settings);
    }
}
