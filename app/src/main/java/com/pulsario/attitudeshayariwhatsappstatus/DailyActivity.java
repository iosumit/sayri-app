package com.pulsario.attitudeshayariwhatsappstatus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import static com.pulsario.attitudeshayariwhatsappstatus.MassagingService.DESC;
import static com.pulsario.attitudeshayariwhatsappstatus.MassagingService.TITLE;

public class DailyActivity extends AppCompatActivity {
    public static final String MyPref2 = "MySecPref";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        sharedPreferences = getSharedPreferences(MyPref2, Context.MODE_PRIVATE);
        //sharedPreferences.getString(TITLE, null);
        String x = sharedPreferences.getString(DESC, "THIS IS CONTENT");
        TextView desc = findViewById(R.id.shayri_text);
        desc.setText(x);
    }
}