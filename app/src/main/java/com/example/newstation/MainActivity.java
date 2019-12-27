package com.example.newstation;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.newstation.database.AppDatabase;
import com.example.newstation.news.Function;
import com.example.newstation.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import berlin.volders.badger.BadgeDrawable;

public class MainActivity extends AppCompatActivity {
    AppDatabase database;
    private BadgeDrawable badge;
    private final static String API_KEY = "ff8c03c87d0048fb8ce9209c6239d52c";
    private static final String TAG = MainActivity.class.getSimpleName();
    public static TextView textView3;
    public static TextView textView2;

    public static TextView textView1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Function.isNetworkAvailable(getApplicationContext())) {
            database = AppDatabase.getDatabaseInstance(getApplicationContext());
            AppDatabase.destroyInstance();
        }

        //For API


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);


        final TabLayout tabs = findViewById(R.id.tabs);

        tabs.setupWithViewPager(viewPager);


        tabs.getTabAt(0).setCustomView(R.layout.notification_badge);
        textView3 = (TextView) tabs.getTabAt(0).getCustomView().findViewById(R.id.textBadge);
        textView3.setText("0");
        ImageView imageView = (ImageView) tabs.getTabAt(0).getCustomView().findViewById(R.id.iconBadge);
        imageView.setImageResource(R.drawable.ic_newspaper);

        tabs.getTabAt(1).setCustomView(R.layout.notification_badge);
        textView1 = (TextView) tabs.getTabAt(1).getCustomView().findViewById(R.id.textBadge);
        textView1.setText("0");
        ImageView imageView1 = (ImageView) tabs.getTabAt(1).getCustomView().findViewById(R.id.iconBadge);
        imageView1.setImageResource(R.drawable.ic_bars);

        tabs.getTabAt(2).setCustomView(R.layout.notification_badge);
        textView2 = (TextView) tabs.getTabAt(2).getCustomView().findViewById(R.id.textBadge);
        textView2.setText("0");
        ImageView imageView2 = (ImageView) tabs.getTabAt(2).getCustomView().findViewById(R.id.iconBadge);
        imageView2.setImageResource(R.drawable.ic_soccer_ball_variant);

//        tabs.getTabAt(3).setCustomView(R.layout.notification_badge);
//        final TextView textView3 = (TextView) tabs.getTabAt(3).getCustomView().findViewById(R.id.textBadge);
//        textView3.setText("13");
//        ImageView imageView3 = (ImageView) tabs.getTabAt(3).getCustomView().findViewById(R.id.iconBadge);
//        imageView3.setImageResource(R.drawable.ic_cloudy_day);


        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                final TextView textView4 = (TextView) tabs.getTabAt(position).getCustomView().findViewById(R.id.textBadge);
                textView4.setVisibility(View.GONE);
                textView4.setText("0");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                final TextView textView5 = (TextView) tabs.getTabAt(position).getCustomView().findViewById(R.id.textBadge);
                if (textView5.getText().equals("0")) {
                    textView5.setVisibility(View.GONE);
                } else
                    textView5.setVisibility(View.VISIBLE);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}