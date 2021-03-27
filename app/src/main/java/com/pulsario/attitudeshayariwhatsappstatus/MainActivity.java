package com.pulsario.attitudeshayariwhatsappstatus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    private NotificationManagerCompat notificationManager;

    public static final String CHANNEL_ID = "Pulsar1";
    public static final String CHANNEL_NAME = "Pulsar io";
    public static final String CHANNEL_DESC = "PULSAR NOTIFICATION";

    public static final String MyPref2 = "MyPref2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Attitude Status");
        Button englishBtn = findViewById(R.id.english_button);
        Button hindiBtn = findViewById(R.id.hindi_button);
        Button hinglishBtn = findViewById(R.id.hinglish_button);
        Button loveBtn = findViewById(R.id.button_love);
        Button dailyBtn = findViewById(R.id.button_daily_quote);
        CardView shareBtn = findViewById(R.id.share_button);

        englishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShyariActivity.class);
                intent.putExtra("TITLE", "English Shayri");
                intent.putExtra("BACK", 0);
                startActivity(intent);
            }
        });
        hindiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShyariActivity.class);
                intent.putExtra("TITLE", "Hindi Shayri");
                intent.putExtra("BACK", 1);
                startActivity(intent);
            }
        });
        hinglishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShyariActivity.class);
                intent.putExtra("TITLE", "Hinglish Shayri");
                intent.putExtra("BACK", 2);
                startActivity(intent);
            }
        });
        loveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShyariActivity.class);
                intent.putExtra("TITLE", "Love Shayri");
                intent.putExtra("BACK", 3);
                startActivity(intent);
            }
        });
        dailyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DailyActivity.class);
                startActivity(intent);
            }
        });
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                intent.putExtra(Intent.EXTRA_TEXT, "Checkout this app : Attitude Shayri Status https://play.google.com/store/apps/details?id=com.pulsario.attitudeshayariwhatsappstatus");
                startActivity(intent);
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    getString(R.string.default_notification_channel_id),
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESC);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
        }
    }
}
