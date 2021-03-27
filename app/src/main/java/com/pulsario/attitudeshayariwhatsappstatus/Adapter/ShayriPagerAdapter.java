package com.pulsario.attitudeshayariwhatsappstatus.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.pulsario.attitudeshayariwhatsappstatus.Item.ShayriItem;
import com.pulsario.attitudeshayariwhatsappstatus.R;
import com.pulsario.attitudeshayariwhatsappstatus.ShyariActivity;

import java.util.List;

import static com.pulsario.attitudeshayariwhatsappstatus.ShyariActivity.BLov;
import static com.pulsario.attitudeshayariwhatsappstatus.ShyariActivity.BMHen;
import static com.pulsario.attitudeshayariwhatsappstatus.ShyariActivity.BMHi;
import static com.pulsario.attitudeshayariwhatsappstatus.ShyariActivity.BMen;
import static com.pulsario.attitudeshayariwhatsappstatus.ShyariActivity.MyPref;

public class ShayriPagerAdapter extends PagerAdapter {
    private List<ShayriItem> shayriItems;
    private LayoutInflater inflater;
    private Context context;
    SharedPreferences sharedPreferences;
    int lan;

    public ShayriPagerAdapter(List<ShayriItem> shayriItems, Context context, int lan) {
        this.shayriItems = shayriItems;
        this.context = context;
        inflater = LayoutInflater.from(context);
        sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        this.lan = lan;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
    @Override
    public int getCount() {
        return shayriItems.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View shayriLayout = inflater.inflate(R.layout.card_shayri_item, container, false);
        assert shayriLayout != null;

        final TextView txt = shayriLayout.findViewById(R.id.shayri_text);
        final ToggleButton bookmark = shayriLayout.findViewById(R.id.bookmark);

        int MARKER = getSharedPref();

        if (MARKER == position && MARKER != 0)
            bookmark.setChecked(true);

        bookmark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    putValue(position);
                } else {
                    putValue(0);
                }
            }
        });

        txt.setText(shayriItems.get(position).getShayriText());

        txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, ShyariActivity.textSize);
        container.addView(shayriLayout, 0);
        return shayriLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {
        super.restoreState(state, loader);
    }
    public Parcelable saveState(){
        return null;
    }

    void putValue(int val){
        Log.e("SET ", "putValue");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (lan){
            case 0:
                editor.putInt(BMen, val);
                editor.apply();
                break;
            case 1:
                editor.putInt(BMHi, val);
                editor.apply();
                break;
            case 2:
                editor.putInt(BMHen, val);
                editor.apply();
                break;
            case 3:
                editor.putInt(BLov, val);
                editor.apply();
                break;
            default:
        }
    }
    int getSharedPref(){
        Log.e("SET ", "getSharedPref");
        switch (lan){
            case 0:
                return sharedPreferences.getInt(BMen,0);
            case 1:
                return sharedPreferences.getInt(BMHi,0);
            case 2:
                return sharedPreferences.getInt(BMHen,0);
            case 3:
                return sharedPreferences.getInt(BLov,0);
            default:
                return 0;
        }
    }
}
