package com.daralmathour.altaefhessan.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.daralmathour.altaefhessan.R;

public class AzkarMainActivity extends AppCompatActivity {


    RelativeLayout  azkarSabah,azkarMasa,azkarNoom,azkarSafar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azkar_main);

        azkarSabah = (RelativeLayout) findViewById(R.id.azkarSabah);
        azkarMasa = (RelativeLayout) findViewById(R.id.azkarMasa);
        azkarNoom = (RelativeLayout) findViewById(R.id.azkarNoom);
        azkarSafar = (RelativeLayout) findViewById(R.id.azkarSafar);

        azkarSabah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent  intent = new Intent(AzkarMainActivity.this,AzkarDetailsActivity.class);
                intent.putExtra("index",1);
                startActivity(intent);
            }
        });

        azkarMasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(AzkarMainActivity.this,AzkarDetailsActivity.class);
                intent.putExtra("index",2);
                startActivity(intent);
            }
        });

        azkarNoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(AzkarMainActivity.this,AzkarDetailsActivity.class);
                intent.putExtra("index",3);
                startActivity(intent);
            }
        });

        azkarSafar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(AzkarMainActivity.this,AzkarDetailsActivity.class);
                intent.putExtra("index",4);
                startActivity(intent);
            }
        });




    }
}
