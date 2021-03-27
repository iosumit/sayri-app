package com.pulsario.attitudeshayariwhatsappstatus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.pulsario.attitudeshayariwhatsappstatus.Adapter.ShayriPagerAdapter;
import com.pulsario.attitudeshayariwhatsappstatus.Item.ShayriItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShyariActivity extends AppCompatActivity {
    ViewPager shayriPager;
    ArrayList<ShayriItem> shayriItems;
    ShayriPagerAdapter shayriPagerAdapter;
    private AdView mAdView;

    SharedPreferences sharedPreferences;

    public static final String MyPref = "MyPref";
    public static final String BMHen = "Hinglish_BookMark";
    public static final String BMHi = "Hindi_BookMark";
    public static final String BMen = "english_BookMark";
    public static final String BLov = "love_BookMark";

    public static int textSize = 30;

    int CURRENT = 0;
    int MAX = 0;
    int MARK = 0;

    List<String> txts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shyari);
        setTitle(getIntent().getStringExtra("TITLE"));
        // Initialize

        shayriPager = findViewById(R.id.shayri_pager);
        ImageView next = findViewById(R.id.button_next);
        ImageView prev = findViewById(R.id.button_prev);
        ImageView copy = findViewById(R.id.button_copy);
        ImageView share = findViewById(R.id.button_send);


        shayriItems = new ArrayList<>();

        final int lan = getIntent().getIntExtra("BACK",0);

        sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);

        textSize = 30;

        LinearLayout ll = findViewById(R.id.background_pic);

        /*Set Background image*/
        if (getIntent().getIntExtra("BACK",0) == 0){
            txts = Arrays.asList(getResources().getStringArray(R.array.english));
            ll.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.attitudeboy, null));
            MARK = sharedPreferences.getInt(BMen, 0);

        } else if (getIntent().getIntExtra("BACK",0) == 1){
            txts = Arrays.asList(getResources().getStringArray(R.array.hindi));
            ll.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.attitudegirl, null));
            MARK = sharedPreferences.getInt(BMHi, 0);
        }
        else if (getIntent().getIntExtra("BACK",0) == 3){
            txts = Arrays.asList(getResources().getStringArray(R.array.love));
            ll.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.attitudegirl, null));
            MARK = sharedPreferences.getInt(BLov, 0);
        }else {
            txts = Arrays.asList(getResources().getStringArray(R.array.hinglish));
            ll.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.icon2, null));
            MARK = sharedPreferences.getInt(BMHen, 0);
        }

        MAX = txts.size();                                                                          // MAX Size

        //Adding List Array to Items

        for (String txt : txts) {
            shayriItems.add(new ShayriItem(txt,"SS"));
        }
        Log.e("ERror ", MAX+" ");

        shayriPagerAdapter = new ShayriPagerAdapter(shayriItems, this, lan);
        shayriPager.setAdapter(shayriPagerAdapter);                                                 // Set Adapter

        shayriPager.setCurrentItem(MARK);


        shayriPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.e("INDEX", position+"  " + CURRENT +" "+ MAX);
                CURRENT = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("INDEX", "  " + CURRENT +" "+ MAX);
                if (CURRENT == MAX-1)
                    Toast.makeText(ShyariActivity.this, "End", Toast.LENGTH_SHORT).show();
                if (CURRENT < MAX-1) {
                    CURRENT++;
                    shayriPager.setCurrentItem(CURRENT);
                    //textSize = textSize+2;
                }

            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("INDEX", "  " + CURRENT +" "+ MAX);
                if (CURRENT <= 0)
                    Toast.makeText(ShyariActivity.this, "Starting", Toast.LENGTH_SHORT).show();
                if (CURRENT > 0) {
                    CURRENT--;
                    //textSize = textSize-2;
                    shayriPager.setCurrentItem(CURRENT);
                }
            }
        });
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = txts.get(CURRENT);
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Share", txt);
                clipboardManager.setPrimaryClip(clip);
                Toast.makeText(ShyariActivity.this, "Copied", Toast.LENGTH_SHORT).show();

            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = txts.get(CURRENT);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                intent.putExtra(Intent.EXTRA_TEXT, txt);
                startActivity(intent);
            }
        });


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_setting:
                //Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                final Dialog d = new Dialog(ShyariActivity.this);
                d.setContentView(R.layout.dialog_setting);
                final TextView demoText = d.findViewById(R.id.demo_text);
                final TextView demoTextSize = d.findViewById(R.id.text_size);
                SeekBar seekBar = d.findViewById(R.id.button_seekbar);
                demoText.setTextSize(textSize);
                demoTextSize.setText(textSize+"%");
                seekBar.setProgress(textSize);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        textSize = progress;
                        demoText.setTextSize(textSize);
                        demoTextSize.setText(progress+"%");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                d.show();
                return true;

            case R.id.menu_rate_us:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.pulsario.attitudeshayariwhatsappstatus"));
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
